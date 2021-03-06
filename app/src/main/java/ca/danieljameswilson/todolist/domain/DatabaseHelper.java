package ca.danieljameswilson.todolist.domain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Daniel on 2016-11-20.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todo2.db";
    public static final int DATABASE_VERSION = 1;
    public static final String ITEM_TABLE = "item2";
    private static DatabaseHelper instance = null;

    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            Log.d("DATABASE INFO", "DATABASE IS CREATED");
            instance = new DatabaseHelper(context);
        }
        return instance;
    }
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + ITEM_TABLE +" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT NOT NULL,"+
                "timestamp TEXT NOT NULL,"+
                "completed INTEGER NOT NULL DEFAULT 0)";

        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
