package com.leo.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具
 */
public class LogUtils {
    /**
     * 获取日志
     * @return
     */
    public static Logger getLogger(){
        return LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    }
}
