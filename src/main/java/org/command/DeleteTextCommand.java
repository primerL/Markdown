package org.command;

import org.component.MarkdownTitle;

public class DeleteTextCommand implements Command{
    private MarkdownTitle root;
    private int line;
    private String text;


    /***
     * @param line 插入的行数
     * @param text 插入的内容
     */
    public DeleteTextCommand(int line, String text, MarkdownTitle root){
        //判断是插入标题还是文本
        this.line = line;
        this.text = text;
        this.root = root;
    }

    @Override
    public void execute(){
    }

    @Override
    public void undo(){
    }
}
