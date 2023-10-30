package org.singleleton;

//TODO：实现日志记录器，记录每一次执行的命令，使用单例模式
public class Logger {
    private static Logger logger = new Logger();

    private Logger() {}

    public static Logger getInstance() {
        return logger;
    }

}
