package com.example.javiosyc.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.javiosyc.todoapp.EditItemActivity;
import com.example.javiosyc.todoapp.model.ToDoItem;
import com.example.javiosyc.todoapp.model.enums.ToDoItemLevel;
import com.example.javiosyc.todoapp.model.enums.ToDoItemStatus;
import com.example.javiosyc.todoapp.utils.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by javiosyc on 2017/1/17.
 */

public class ToDoDAO {
    private static Logger _log = Logger.getLogger(EditItemActivity.class.getName());
    public static final String TABLE_NAME = "todo";

    public static final String KEY_ID = "_id";

    // 其它表格欄位名稱
    public static final String NAME_COLUMN = "name";
    public static final String DATETIME_COLUMN = "datetime";

    public static final String NOTES_COLUMN = "notes";
    public static final String LEVEL_COLUMN = "level";
    public static final String STATUS_COLUMN = "status";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT NOT NULL," +
                    DATETIME_COLUMN + " TEXT NOT NULL, " +
                    NOTES_COLUMN + " TEXT NOT NULL," +
                    LEVEL_COLUMN + " TEXT NOT NULL, " +
                    STATUS_COLUMN + " TEXT NOT NULL )";

    private SQLiteDatabase db;


    public ToDoDAO(Context context) {
        db = MySQLiteHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public ToDoItem insert(ToDoItem item) {
        ContentValues cv = new ContentValues();

        _log.info("note" + item.getNote());

        cv.put(NAME_COLUMN, item.getName());
        cv.put(DATETIME_COLUMN, item.getDueDate());
        cv.put(NOTES_COLUMN, item.getNote());
        cv.put(LEVEL_COLUMN, item.getLevel().getContent());
        cv.put(STATUS_COLUMN, item.getStatus().getContent());

        long id = db.insert(TABLE_NAME, null, cv);
        item.setId(id);

        return item;
    }


    public boolean update(ToDoItem item) {
        _log.info("update note" + item.getNote());

        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, item.getName());
        cv.put(DATETIME_COLUMN, item.getDueDate());
        cv.put(NOTES_COLUMN, item.getNote());
        cv.put(LEVEL_COLUMN, item.getLevel().getContent());
        cv.put(STATUS_COLUMN, item.getStatus().getContent());

        String where = KEY_ID + "=" + item.getId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }


    public List<ToDoItem> getAll() {
        List<ToDoItem> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public ToDoItem getRecord(Cursor cursor) {

        ToDoItem result = new ToDoItem();
        result.setId(cursor.getLong(0));
        result.setName(cursor.getString(1));
        result.setDueDate(cursor.getString(2));
        result.setNote(cursor.getString(3));
        result.setLevel(ToDoItemLevel.getEnumByContext(cursor.getString(4)));
        result.setStatus(ToDoItemStatus.getEnumByContext(cursor.getString(4)));

        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
