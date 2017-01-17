package com.example.javiosyc.todoapp.utils;

import com.example.javiosyc.todoapp.model.ToDoItem;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by javiosyc on 2017/1/17.
 */

public class DBUtils {
    private static final String FILE_NAME = "todo.txt";

    public void writeItems (  File filesDir ,  ArrayList<ToDoItem> items) {
        File todoFile = new File(filesDir, FILE_NAME);
        try {
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ToDoItem> readItems( File filesDir) {
        ArrayList<ToDoItem> items;
        File todoFile = new File(filesDir, FILE_NAME);
        try{
            items = parseRecords (FileUtils.readLines(todoFile));
        } catch ( IOException e) {
            items = new ArrayList<ToDoItem>();
        }
        return items;
    }

    private ArrayList<ToDoItem> parseRecords(List<String> records) {

        ArrayList<ToDoItem> items = new ArrayList<>();

        for(String record: records) {
            ToDoItem item = new ToDoItem(record);
            items.add(item);
            /*
            String[] columns = record.split(ToDoItem.COLUMN_SPLIT);

            if(columns.length == 5) {
                ToDoItem item = new ToDoItem( columns[0],columns[1],columns[2], ToDoItemLevel.getEnumByContext(columns[3]), ToDoItemStatus.getEnumByContext(columns[4]));
                items.add(item);
            }*/
        }

        return items;
    }
}
