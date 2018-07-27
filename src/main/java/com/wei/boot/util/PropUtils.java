package com.wei.boot.util;

import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public abstract class PropUtils {

    private static Logger logger = LoggerFactory.getLogger(PropUtils.class); 
    private static Properties prop = null;

    static {
        prop = new Properties();
        Properties commonProp = loadPropertyFromFile("configs/common.properties");
        Properties envProp = loadPropertyFromFile("configs/config.properties");
        prop.putAll(commonProp);
        prop.putAll(envProp);
    }

    private static Properties loadPropertyFromFile(String filePath) {
        Properties properties = new Properties();
        InputStreamReader in = getPath(filePath);
        if (in == null) {
            logger.debug("config.properties does not exists");
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            logger.error("load config.properties error:{}", e);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                logger.error("unable to close input stream for file config.properties:{}", e);
            }
        }

        return properties;
    }

    public static String getProperty(String key) {
        String value = "";
        if (StringUtils.isEmpty(key)) {
            logger.debug("ConfigProperties:key is null");
        } else {
            value = prop.getProperty(key);
            if (value != null) {
                value = value.trim();
            } else {
                value = "";
            }
        }
        return value;
    }

    public static int getInt(String key) {
        String value = "";
        if (StringUtils.isEmpty(key)) {
            logger.debug("ConfigProperties:key is null");
        } else {
            value = prop.getProperty(key);
            if (value != null) {
                value = value.trim();
            } else {
                value = "0";
            }
        }
        return Integer.parseInt(value);
    }

    public static long getLong(String key) {
        String value = "";
        if (StringUtils.isEmpty(key)) {
            logger.debug("ConfigProperties:key is null");
        } else {
            value = prop.getProperty(key);
            if (value != null) {
                value = value.trim();
            } else {
                value = "0";
            }
        }
        return Long.parseLong(value);
    }

    private static InputStreamReader getPath(String path) {
        File fDir = new File(PropUtils.class.getResource("/").getPath());
        String p = fDir.getAbsolutePath();
        path = p.replace("\\", "/") + "/" + path;
        logger.debug("config.properties path:{}", path);
        try {
            InputStreamReader is = new FileReader(path);
            return is;
        } catch (FileNotFoundException e) {
            logger.error("unable to find file:{} , error:{}", path, e);
        }

        return null;
    }

}
