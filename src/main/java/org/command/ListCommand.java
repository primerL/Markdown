package org.command;

import org.component.MarkdownTitle;

public class ListCommand implements Command{

    private MarkdownTitle root;


    public ListCommand(MarkdownTitle root){
        this.root = root;
    }

    @Override
    public void execute(){
        root.print();
    }

    @Override
    public void undo(){
    }
}
