package org.command;

import org.component.MarkdownTitle;

public class LoadCommand implements Command {
    private String path;
    private MarkdownTitle root;

    public LoadCommand() {
    }

    public LoadCommand(String path, MarkdownTitle root) {
        this.path = path;
        this.root = root;
    }

    @Override
    public void execute() {
        // TODO - implement SaveCommand.execute
        root.load(path);
    }

    @Override
    public void undo() {
        // TODO - implement SaveCommand.undo
        throw new UnsupportedOperationException();
    }
}
