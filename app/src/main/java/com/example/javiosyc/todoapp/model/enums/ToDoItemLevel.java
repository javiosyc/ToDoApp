package com.example.javiosyc.todoapp.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by javiosyc on 2017/1/17.
 */

public enum ToDoItemLevel {
    HIGH("high", "HIGH","#D31930"), NORMAL("normal", "NORMAL","#698980"), LOWER("lower", "LOWER","#c5a08d");

    private String text;
    private String content;
    private String color;

    private ToDoItemLevel(String content, String text ,String color) {
        this.content = content;
        this.text = text;
        this.color = color;
    }

    private static final Map<String, ToDoItemLevel> map = new HashMap<>();

    static {
        for (ToDoItemLevel level : ToDoItemLevel.values())
            map.put(level.getContent(), level);
    }

    public String getText() {
        return text;
    }

    public static ToDoItemLevel getEnumByContext(String context) {
        ToDoItemLevel status = map.get(context);
        return status;
    }

    public static ToDoItemLevel[] getAllStatus() {
        ToDoItemLevel[] items = new ToDoItemLevel[map.size()];
        items[0] = ToDoItemLevel.LOWER;
        items[1] = ToDoItemLevel.NORMAL;
        items[2] = ToDoItemLevel.HIGH;
        return items;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return this.content;
    }

    public String getColor() {
        return color;
    }

    public static int  getDefaultLevelIndex() {
        return 1;
    }
}
