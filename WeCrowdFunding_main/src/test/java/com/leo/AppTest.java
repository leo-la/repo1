package com.leo;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
            logger.trace("trace message");
            logger.debug("debug message");
            logger.info("info message");
            logger.warn("warn message");
            logger.error("error message");
            logger.fatal("fatal message");
            System.out.println("Hello World!");
    }
}
