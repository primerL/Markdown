package org.component;

import java.util.List;

public class MarkdownText extends Component{

    private String content;
    private int lineIndex;

    private List<Component> components;

    private int level = 0;

    public List<Component> getComponents() {
        return components;
    }


    public MarkdownText(String content) {
        this.content = content;
    }

    @Override
    public void printTree(int i, List<Integer> level) {
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void updateIndex(int i) {
        this.lineIndex += 1;
    }

    public Component getChild(int i){
        return null;
    }

    @Override
    public String getContent() {
        return content;
    }


    @Override
    public void insertTitle(int line, String text, Component root) {
        //报错，叶子节点不能插入标题
        System.out.println("叶子节点不能插入标题");
    }

    @Override
    public void insertText(int line, String text, Component root) {
        //报错，叶子节点不能插入文本
        System.out.println("叶子节点不能插入文本");
    }

    @Override
    public void print() {
        if(this.content != null)
            System.out.println(this.content);
    }

    @Override
    public int getLineIndex() {
        return this.lineIndex;
    }

    @Override
    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public int getSize(){
        if(content == null || content.isEmpty()){
            return 0;
        }else {
            return 1;
        }
    }
}
