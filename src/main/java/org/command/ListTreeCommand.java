package org.command;
import org.component.MarkdownTitle;

import java.util.ArrayList;
import java.util.List;

public class ListTreeCommand implements Command{

    private MarkdownTitle root;

    public ListTreeCommand(MarkdownTitle root){
        this.root = root;
    }

    @Override
    public void execute() {
        List<Integer> level = new ArrayList<>();
        root.printTree(0, level);
    }

    @Override
    public void undo() {
    }
}
