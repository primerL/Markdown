package org.command;

import org.component.Component;
import org.component.MarkdownTitle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestCommand {


    @Test
    public void testInsertTitle() {
        //TODO: 测试插入命令
        MarkdownTitle root = new MarkdownTitle();
        Command insertTitleCommand = new InsertTitleCommand(1, "# title1", root);
        insertTitleCommand.execute();
        assert (root.getSize() == 1);
        assert (Objects.equals(root.getChild(0).getContent(), "# title1"));
        assert (root.getChild(0).getLevel() == 1);
        System.out.println(root.getChild(0).getSize());
        root.print();

        Command insertTitleCommand2 = new InsertTitleCommand(2, "## title2", root);
        insertTitleCommand2.execute();
        assert (root.getSize() == 2);
        Component child1 = root.getChild(0);
        Component child2 = child1.getChild(0);
        assert (Objects.equals(child2.getContent(), "## title2"));
        assert (child2.getLevel() == 2);
        System.out.println(root.getChild(0).getSize());
        root.print();


        Command insertTitleCommand3 = new InsertTitleCommand(3, "### title2.1", root);
        insertTitleCommand3.execute();
        root.print();
        assert (root.getSize() == 3);
        Component child3 = child2.getChild(0);
        assert (Objects.equals(child3.getContent(), "### title2.1"));
        System.out.println(root.getChild(0).getSize());


        Command insertTitleCommand4 = new InsertTitleCommand(4, "### title2.2", root);
        insertTitleCommand4.execute();
        root.print();
        assert (root.getSize() == 4);
        Component child4 = child2.getChild(0);



        Command insertTitleCommand5 = new InsertTextCommand(4, "* 文本", root);
        insertTitleCommand5.execute();


        Command insertTextCommand1 = new InsertTextCommand(6, "* 文本2", root);
        insertTextCommand1.execute();
        root.print();

    }

    @Test
    public void testLoad() {
        //TODO 测试load指令
        MarkdownTitle root = new MarkdownTitle();
        Command loadCommand = new LoadCommand("test.md", root);
        loadCommand.execute();

        Command insertTitleCommand4 = new InsertTitleCommand(5, "#### 备考", root);
        insertTitleCommand4.execute();

        Command insertTitleCommand5 = new InsertTitleCommand(6, "## 其他", root);
        insertTitleCommand5.execute();
        List<Integer> level = new ArrayList<>();
        root.printTree(0, level);

    }
}
