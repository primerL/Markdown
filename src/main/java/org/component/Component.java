package org.component;

import java.util.List;

public abstract class Component {

    private List<Component> components;
    private int lineIndex;

    public abstract void print();

    public abstract int getLineIndex();

    public abstract List<Component> getComponents();

    public abstract int getSize();

    public abstract void setLineIndex(int lineIndex);

    public abstract void insertTitle(int line, String text, Component root);

    public abstract void insertText(int line, String text, Component root);

    public abstract String getContent();

    public abstract Component getChild(int i);

    public abstract void updateIndex(int i);

    public abstract int getLevel();

    protected abstract void printTree(int i, List<Integer> level);

    protected abstract void delete(String content);
}
