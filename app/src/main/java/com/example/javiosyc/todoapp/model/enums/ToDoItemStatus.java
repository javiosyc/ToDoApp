package com.example.javiosyc.todoapp.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javiosyc on 2017/1/17.
 */

public enum ToDoItemStatus implements ListProperty {
    DONE("done", "DONE"), TO_DO("toDo", "TO-DO"), PAST_DUE("pastDue", "PAST-DUE");

    private String content;
    private String text;

    private ToDoItemStatus(String content, String text) {
        this.content = content;
        this.text = text;
    }

    private static final Map<String, ToDoItemStatus> map = new HashMap<>();

    static {
        for (ToDoItemStatus status : ToDoItemStatus.values())
            map.put(status.getContent(), status);
    }

    public static ToDoItemStatus getEnumByContext(String context) {
        ToDoItemStatus level = map.get(context);
        return level;
    }

    public String getContent() {
        return content;
    }

    public String getText() {
        return text;
    }

    public static ToDoItemStatus[] getAllStatus() {
        ToDoItemStatus[] items = new ToDoItemStatus[map.size()];
        items[0] = ToDoItemStatus.TO_DO;
        items[1] = ToDoItemStatus.DONE;
        items[2] = ToDoItemStatus.PAST_DUE;
        return items;
    }

    @Override
    public String toString() {
        return this.content;
    }


    public static int getDefaultLevelIndex() {
        return 0;
    }
}
