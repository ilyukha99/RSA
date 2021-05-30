package utils;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyLogger {
    private static Logger logger = null;

    private MyLogger() {}

    public static Logger getLogger(String name) {
        if (logger == null) {
            logger = Logger.getLogger(name);
            Handler handlerObj = new ConsoleHandler();
            handlerObj.setLevel(Level.ALL);
            logger.addHandler(handlerObj);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
        }

        return logger;
    }
}
