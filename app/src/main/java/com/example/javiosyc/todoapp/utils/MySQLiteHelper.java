package com.example.javiosyc.todoapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.javiosyc.todoapp.dao.ToDoDAO;

/**
 * Created by javiosyc on 2017/1/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";
    public static final int VERSION = 1;
    private static SQLiteDatabase database;

    public MySQLiteHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MySQLiteHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ToDoDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoDAO.TABLE_NAME);
        onCreate(db);
    }
}
