package com.github.mealsquad.utility;

import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger();

    public static String readProperties(String key) {
        String value = ConfigFactory.load().getString(key);
        logger.info(String.format("Reading in configuration property: [%s] = %s", key, value));
        return value;
    }
}
