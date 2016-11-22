package ca.danieljameswilson.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                        cursor.getInt(cursor.getColumnIndex("completed")) != 0,
                        cursor.getLong(cursor.getColumnIndex("_id")),
                        cursor.getString(cursor.getColumnIndex("timestamp")));
                list.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return list;
    }

    public void add(ToDoItem item) {
        Date date = new Date();
        String timeStamp = DateFormat.getDateInstance().format(date);

        ContentValues newItem = new ContentValues();
        newItem.put("description", item.getDescription());
        newItem.put("completed", item.isComplete());
        newItem.put("timestamp", timeStamp);

        SQLiteDatabase db = dbHealper.getWritableDatabase();
        db.insert(DatabaseHelper.ITEM_TABLE, null, newItem);
    }

    public void removeItem(ToDoItem item) {
        SQLiteDatabase db = dbHealper.getWritableDatabase();
        String[] args = new String[]{String.valueOf(item.getId())};
        db.delete(DatabaseHelper.ITEM_TABLE, "_id=?",args);
    }

    public void updateItem(ToDoItem item){
        ContentValues newItem = new ContentValues();
        newItem.put("description",item.getDescription());
        newItem.put("completed",item.isComplete());

        SQLiteDatabase db = dbHealper.getWritableDatabase();
        String[] args = new String[]{String.valueOf(item.getId())};
        db.update(DatabaseHelper.ITEM_TABLE, newItem, "_id=?", args);

    }
}
