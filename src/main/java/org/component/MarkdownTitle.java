package org.component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.utils.Constants;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MarkdownTitle extends Component{

        private List<Component> components;
        private String content;
        private Component root;

        private String path;

        private int lineIndex;

        private int level;

        public MarkdownTitle() {
            this.components = new ArrayList<>();
            this.lineIndex = 0;
        }

        public Component getChild(int i){
            if(i < components.size())
                return components.get(i);
            else
                return null;
        }

        public boolean isValidFilePath(String filePath){
            try{

                Path path = Paths.get(filePath);
                if(!Files.exists(path)){
                   Files.createFile(path);
                }

                return !Files.isDirectory(path);

            } catch (IOException e) {
               System.out.println("文件路径不合法");
                return false;
            }
        }

        public void load(String path){
            this.path = path;

            String filePath = Constants.RESOURCE_PATH + path;
            System.out.println("Loading file from "+filePath);
            if(!isValidFilePath(filePath)){
               System.out.println("文件路径不合法");
               return;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                int lineIndex = 1;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    if(line.startsWith("#")){
                        //如果是标题
                        insertTitle(lineIndex, line, this);
                    }else{
                        //如果是文本
                        insertText(lineIndex, line, this);
                    }
                    lineIndex++;
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        }

        @Override
        public String getContent() {
            return content;
        }

        public int getLevel(){
            return level;
        }

        public int calculateLevel(String content){
            int level = 0;
            for(int i=0;i<content.length();i++){
                if(content.charAt(i) == '#'){
                    level++;
                }else{
                    break;
                }
            }
            return level;
        }

        public List<Component> getComponents() {
            return components;
        }

        public MarkdownTitle(String content, int lineIndex) {
            this.content = content;
            this.lineIndex = lineIndex;
            this.components = new ArrayList<>();
            //根据content有多少个#来判断level
            this.level = calculateLevel(content);
        }

        //获取Title覆盖的行数
        public int getCoveredLine(int i){
            int size = components.get(i).getSize();
            int line = components.get(i).getLineIndex();
            return size+line-1;
        }

        public void updateIndex(int i){
            //TODO: 从第i个后开始更新
            for (int j=i+1;j<components.size();j++){
                components.get(j).setLineIndex(components.get(j).getLineIndex()+1);
                //TODO: 更新所有字节点的lineIndex
                components.get(j).updateIndex(0);
            }
        }

        /**
         * printTree
         * @param
         * tab: 用于打印空格
         * level: 存储每一层级的竖线
         * */
        public void printTree(int tab, List<Integer> level){
                for(Component component : components) {
                    //打印tab个空格，|-，content 在同一行
                    // 让同一层级有竖线
                    if(components.size()>1 && component != components.get(components.size()-1)){
                        if(level.size()>=tab+1)
                            level.set(tab, 1);
                        else
                            level.add(tab, 1);
                    }
                    else if(components.size()==1 || component == components.get(components.size()-1)){
                        if(level.size()>=tab+1)
                            level.set(tab, 0);
                        else
                            level.add(tab, 0);
                    }
                    for(int i=0;i<tab;i++){
                        if(level.size()>=i && level.get(i) == 1)
                            System.out.print("|");

                        System.out.print("\t");
                    }

                    if(component == components.get(0) && component != components.get(components.size()-1)){
                        System.out.print("├──");
                    }else
                        System.out.print("└──");

                    System.out.println(component.getContent());
                    component.printTree(tab+1, level);
                }
        }

        //加入标题
//        @Override
//        public void insertTitle(int line, String text, Component root){
//             this.root = root;
//             if((content == null || content.isEmpty()) && this!=root) {
//                 //如果是空的，且不是根节点，就加入
//                 this.content = text;
//                 this.lineIndex = line;
//             }
//             else{
//                 //根据line遍历判断加入的位置
//                 if(components.isEmpty()) {
//                     //如果是空的，就加入
//                     components.add(new MarkdownTitle(text, line));
//                 }else{
//                     //如果不是空的，就遍历
//                     for(int i=0;i<components.size();i++){
//                         if(line <= components.get(i).getLineIndex()){
//                             //如果line小于等于当前的line，就加入
//                             components.add(i, new MarkdownTitle(text, line));
//                             //TODO: 更新所有字节点的lineIndex
//                             updateIndex(i);
//                             break;
//                         }
//                         int CoverLine = getCoveredLine(i);
//                         if(line>components.get(i).getLineIndex() && i<= CoverLine){
//                             //如果line大于当前的line，就加入
//                             components.get(i).insertTitle(line,text,root);
//                             break;
//                         }
//                     }
//                 }
//             }
//        }

        public void save(){
            //TODO: 保存到文件
            String filePath = Constants.RESOURCE_PATH + path;
            System.out.println("Saving file to "+filePath);
            //遍历所有的节点，保存到文件
        }

        //加入标题重构
        @Override
        public void insertTitle(int line, String text, Component root){
            this.root = root;
            MarkdownTitle title = new MarkdownTitle(text, line);
            if(components.isEmpty()) {
                components.add(new MarkdownTitle(text, line));
            }else{
                //如果不是空的，就遍历
                for(int i=0;i<components.size();i++) {
                    Component sister = components.get(i);
                    //寻找在哪一个子树
                    if(line>=1 && line<=sister.getSize()+1){
                        //比较level，判断再哪一级
                        if(title.level == sister.getLevel() || sister.getLevel()==0){
                            //如果level相同，比较lineIndex
                            if(line == 1){
                                components.add(i, title);
                                if(sister.getLevel()==0){
                                    //找到其他的叶子节点，把它们加入到title中
                                    for(int j=i+1;j<components.size();j++){
                                        Component child = components.get(j);
                                        if(child.getLevel()==0){
                                            title.components.add(child);
                                            components.remove(child);
                                            j--;
                                        }else{
                                            break;
                                        }
                                    }
                                }
                                break;
                            }else if(line == sister.getSize()+1){
                                components.add(i+1, title);
                                if(sister.getLevel() == 0){
                                    for(int j=i+2;j<components.size();j++){
                                        Component child = components.get(j);
                                        if(child.getLevel()==0){
                                            title.components.add(child);
                                            components.remove(child);
                                            j--;
                                        }else{
                                            break;
                                        }
                                    }
                                }
                                break;
                            }else{
                                components.add(i+1, title);
                                //做调整，把child的孩子节点大于等于line的过继给新加入的节点
                                updateChild(line, title, sister);
                                break;
                            }
                        }
                        else if(title.level > sister.getLevel()){
                            //如果level于当前的level，进入下一层子树
                            line = line -1;
                            sister.insertTitle(line, text, root);
                            break;
                        }
                        else{
                            //如果level大于当前的level,只能是叶子节点情况,并且line相等
                            assert(line == sister.getSize());
                            components.remove(sister);
                            title.components.add(sister);
                            components.add(i, title);
                        }
                    }
                    else{
                        line = line-sister.getSize();
                    }
                }
            }

        }


        //用于调整子树
        public void updateChild(int line, MarkdownTitle title, Component sister){
            assert(line >1);
            line = line -1;
            for(int i=0;i<sister.getSize()-1;i++){
                Component child = sister.getChild(i);
                if(child == null)
                    break;
                if(line >1 && line <= child.getSize()){
                    updateChild(line, title, child);
                    line = 0;
                }else if(line == 0 || line == 1){
                    title.components.add(child);
                    sister.getComponents().remove(child);
                    i--;
                }else{
                    line = line - child.getSize();
                }

            }
        }

        public String dealContent(String content){
            //TODO:
            return content;
        }

        public void delete(String content){
            //TODO: 删除所有文本等于content的节点，标题要去除#比较
            for(int i=0;i<components.size();i++){
                Component component = components.get(i);
                String dealContent = dealContent(component.getContent());
                if(){
                    components.remove(component);
                    i--;
                }else{
                    component.delete(content);
                }
            }
        }

        @Override
        //向标题中加入文本
        public void insertText(int line, String content, Component root){
            this.root = root;
            MarkdownText text = new MarkdownText(content);
            if(components.isEmpty()) {
                components.add(new MarkdownText(content));
            }else{
                for(int i=0; i<components.size(); i++){
                    Component sister = components.get(i);
                    if(line >=1 && line <= sister.getSize()+1){
                        if(line == 1){
                            components.add(i, text);
                            break;
                        }else if(text.getLevel() == sister.getLevel()){
                               int index = line==1?i:i+1;
                               components.add(index, text);
                               break;
                        }else{
                            line = line -1;
                            sister.insertText(line, content, root);
                            break;
                        }
                    }
                    else{
                        line = line-sister.getSize();
                    }
                }
            }
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public void setLineIndex(int lineIndex) {
            this.lineIndex = lineIndex;
        }

        @Override
        public int getLineIndex() {
            return this.lineIndex;
        }

        @Override
        public void print() {
            if(content!=null)
                System.out.println(content);
            if(components!=null && !components.isEmpty())
                for (Component component : components) {
                    component.print();
                }
        }

        @Override
        public int getSize(){
            int size = 0;
            if ((content == null || content.isEmpty()) && (components==null||components.isEmpty())) {
                size = 0;
            } else {
                size = this==root?0:1;
                if(components!=null && !components.isEmpty())
                    for (Component component : components) {
                        size += component.getSize();
                    }
            }
            return size;
        }

}
