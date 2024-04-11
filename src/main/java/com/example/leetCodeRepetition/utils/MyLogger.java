package com.example.leetCodeRepetition.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyLogger {
    private static final Logger logger = LoggerFactory.getLogger(MyLogger.class);

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }
}
