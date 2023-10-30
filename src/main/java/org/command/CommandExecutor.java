package org.command;

import org.component.MarkdownTitle;

import java.util.Stack;

public class CommandExecutor {

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public void undo() {

    }

    public void redo() {

    }
}
