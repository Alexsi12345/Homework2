package org.example;

public class Tree {
    private final int id;
    private final int parentId;

    public Tree(int id, int parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    // Другие геттеры и методы, если нужно
}