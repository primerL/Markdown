package org.command;

import org.component.Component;
import org.component.MarkdownTitle;

public class InsertTitleCommand implements Command{

    private MarkdownTitle root;
    private int line;
    private String text;


    /***
     * @param line 插入的行数
     * @param text 插入的内容
     */
    public InsertTitleCommand(int line, String text, MarkdownTitle root){
        //判断是插入标题还是文本
        this.line = line;
        this.text = text;
        this.root = root;
    }

    @Override
    public void execute(){
        System.out.println("Inserting title...");
        //插入标题
        root.insertTitle(line,text,root);
    }

    @Override
    public void undo(){
        System.out.println("Undoing insert title...");
        //撤销插入标题，即删除标题
    }

}
