package com.example.javiosyc.todoapp.model;

import com.example.javiosyc.todoapp.model.enums.ToDoItemLevel;
import com.example.javiosyc.todoapp.model.enums.ToDoItemStatus;

import java.io.Serializable;

/**
 * Created by javiosyc on 2017/1/17.
 */

public class ToDoItem  implements Serializable{
    private String name;
    private String dueDate;
    private String note;
    private ToDoItemLevel level;
    private ToDoItemStatus status;
    public static final String  COLUMN_SPLIT = ":";
    public ToDoItem() {
    }


    public ToDoItem(String name ) {
        this.name = name;
    }
    public ToDoItem(String name, String dueDate, String note, ToDoItemLevel level, ToDoItemStatus status) {
        this.name = name;
        this.dueDate = dueDate;
        this.note = note;
        this.level = level;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ToDoItemLevel getLevel() {
        return level;
    }

    public void setLevel(ToDoItemLevel level) {
        this.level = level;
    }

    public ToDoItemStatus getStatus() {
        return status;
    }

    public void setStatus(ToDoItemStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getName());
       /* builder.append( COLUMN_SPLIT);
        builder.append(this.getDueDate());
        builder.append( COLUMN_SPLIT);
        builder.append(this.getLevel());
        builder.append( COLUMN_SPLIT);
        builder.append(this.getNote());
        builder.append( COLUMN_SPLIT);
        builder.append( this.getStatus());*/
        return builder.toString();
    }
}
