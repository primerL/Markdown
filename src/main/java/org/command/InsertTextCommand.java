package org.command;

import org.component.MarkdownTitle;

public class InsertTextCommand implements Command {

    private String text;
    private int lineIndex;

    private MarkdownTitle root;

    public InsertTextCommand(int lineIndex, String text,MarkdownTitle root){
        this.lineIndex = lineIndex;
        this.text = text;
        this.root = root;
    }

    public String getText(){
        return text;
    }

    public int getLineIndex(){
        return lineIndex;
    }

    @Override
    public void execute() {
        System.out.println("Inserting text...");
        root.insertText(lineIndex,text,root);
    }

    @Override
    public void undo() {
        System.out.println("Undoing insert text...");
    }
}
