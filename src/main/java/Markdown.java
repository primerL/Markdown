import java.util.Scanner;

import org.command.*;
import org.component.MarkdownTitle;

import javax.swing.*;

public class Markdown {

    public Markdown() {}

    private MarkdownTitle root = new MarkdownTitle();

    public void start() {
        System.out.println("Welcome to use Markdown Editor!");
        CommandExecutor executor = new CommandExecutor();
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            command = scanner.nextLine().trim();
            System.out.println("command: " + command);
            //TODO： 如果没有加载文件，报错
            Command parsedCommand = parseCommand(command);
            if (parsedCommand != null) {
                executor.executeCommand(parsedCommand);
            }

        } while (true);

    }

    private boolean isTitle(String text){
        String pattern = "^#{1,6}\\s.+";
        return text.matches(pattern);
    }

    private boolean isLoadCommand(String command) {
        if(command.startsWith("load "))
        {
            //检查文件是否以.md结尾
            String text = command.substring(7);
            String[] split = text.split(" ");
            System.out.println("split: " + split[0]);
            return true;

        }else
            return false;
    }

    private Command parseCommand(String command) {
        Command parseCommand = null;
        try {
            //TODO: 判断指令，拆分出函数, 不要写在一起
            if (command.startsWith("insert ")) {
                String text = command.substring(7);
                String[] split = text.split(" ");
                System.out.println("split: " + split[0]);
                int lineIndex;
                //判断是否有行号,匹配数字或者负号加数字
                if (split[0].matches("\\d+") || split[0].matches("-\\d+")) {
                    lineIndex = Integer.parseInt(split[0]);
                    text = text.substring(split[0].length() + 1);
                    if(lineIndex<0 || lineIndex>root.getSize()+1)
                        throw new Exception();
                    System.out.println("lineIndex: " + lineIndex + " text: " + text);
                } else {
                    System.out.println("test");
                    lineIndex = root.getSize() + 1;
                    System.out.println("no line lineIndex: " + lineIndex + " text: " + text);
                }


                //判断是否是标题, 格式为#加空格+内容
                if(isTitle(text)){
                    System.out.println("title:" + text);
                    parseCommand = new InsertTitleCommand(lineIndex, text, root);
                }else{
                    System.out.println("text:" + text);
                    parseCommand = new InsertTextCommand(lineIndex, text, root);
                }
            }
            else if(command.startsWith("load ")) {
                //检查文件是否以.md结尾
                String text = command.substring(5);
                String[] path = text.split(" ");
                if(path[0].endsWith(".md"))
                {
                    parseCommand = new LoadCommand(path[0], root);
                }
                else
                {
                    System.out.println("文件格式错误");
                }
            }
            else if(command.equals("list")){
                parseCommand = new ListCommand(root);
            }else if(command.equals("list-tree")){
                parseCommand = new ListTreeCommand(root);
            }else if(command.equals("save")){
                //TODO: 保存文件
                parseCommand = new SaveCommand(root);
            }else{
                System.out.println("命令格式错误");
            }
        } catch (Exception e) {
            System.out.println("命令格式错误");
        }
        return parseCommand;
    }
}


