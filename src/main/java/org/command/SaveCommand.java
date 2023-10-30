package org.command;

import org.component.MarkdownTitle;

public class SaveCommand implements Command {

    private MarkdownTitle root;

    public SaveCommand(MarkdownTitle root) {
        this.root = root;
    }

    @Override
    public void execute() {
        // TODO - implement SaveCommand.execute
        root.save();
    }

    @Override
    public void undo() {
        // TODO - implement SaveCommand.undo
        throw new UnsupportedOperationException();
    }

}
