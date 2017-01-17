package com.example.javiosyc.todoapp.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javiosyc on 2017/1/17.
 */

public enum ToDoItemLevel {
    HIGH("high", "HIGH"), NORMAL("normal", "NORMAL"), LOWER("lower", "LOWER");

    private String text;
    private String content;

    private ToDoItemLevel(String content, String text) {
        this.content = content;
        this.text = text;
    }

    private static final Map<String, ToDoItemLevel> map = new HashMap<>();

    static {
        for (ToDoItemLevel level : ToDoItemLevel.values())
            map.put(level.getContent(), level);
    }

    public static ToDoItemLevel getEnumByContext(String context) {
        ToDoItemLevel status = map.get(context);
        return status;
    }

    public static String[] getAllStatus() {
        String[] items = new String[map.size()];
        items[0] = ToDoItemLevel.LOWER.getText();
        items[1] = ToDoItemLevel.NORMAL.getText();
        items[2] = ToDoItemLevel.HIGH.getText();
        return items;
    }

    public String getText() {
        return text;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
