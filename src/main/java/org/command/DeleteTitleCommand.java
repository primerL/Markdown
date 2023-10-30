package org.command;

import org.component.MarkdownTitle;

public class DeleteTitleCommand implements Command{
    private MarkdownTitle root;
    private int line;
    private String text;


    /***
     * @param line 删除的行数
     * @param text 插入的内容
     */
    public DeleteTitleCommand(int line, String text, MarkdownTitle root){
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
        System.out.println("Undoing delete title...");
        //撤销插入标题，即删除标题
    }
}
