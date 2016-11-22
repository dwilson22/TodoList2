package ca.danieljameswilson.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import ca.danieljameswilson.todolist.domain.DatabaseHelper;

public class ToDoListManager {


    private static final String TODO_ITEMS_KEY = "itemslist";
    private DatabaseHelper dbHealper;

    public ToDoListManager(Context context) {
        dbHealper = DatabaseHelper.getInstance(context);
    }

    public List<ToDoItem> getList() {
        SQLiteDatabase db = dbHealper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.ITEM_TABLE, null);
        List<ToDoItem> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ToDoItem item = new ToDoItem(
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getInt(cursor.getColumnIndex("completed")) != 0);
                list.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return list;
    }

    public void add(ToDoItem item) {


        ContentValues newItem = new ContentValues();
        newItem.put("description", item.getDescription());
        newItem.put("completed", item.isComplete());

        SQLiteDatabase db = dbHealper.getWritableDatabase();
        db.insert(DatabaseHelper.ITEM_TABLE, null, newItem);
    }

    public void removeItem(ToDoItem item) {
        //  list.remove(item);
        //  Log.d("deleting", item.getDescription().toString());
        //  saveList();
    }
}
