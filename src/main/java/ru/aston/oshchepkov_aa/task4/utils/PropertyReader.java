package ru.aston.oshchepkov_aa.task4.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class PropertyReader {
    private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);
    private static final String RESOURCE_NAME = "application";
    private static final String EMPTY_RESULT = "";
    private static PropertyReader reader;

    private final ResourceBundle bundle;
    private PropertyReader() {
        bundle = ResourceBundle.getBundle(RESOURCE_NAME);
    }
    public static PropertyReader getReader() {
        if (null == reader) {
            reader = new PropertyReader();
        }
        return reader;
    }

    public String getString(String key){
        try{
            var rawString = bundle.getString(key);

            if (rawString.isBlank()){
                return rawString;
            }

            if (isEnvVariable(rawString)){
                rawString = getEnvVariable(rawString);
            }

            return rawString;
        }catch (RuntimeException e){
            log.error("Failed to obtain {} property!", key);
            return EMPTY_RESULT;
        }

    }

    private boolean isEnvVariable(String key){
        return '$' == key.charAt(0);
    }

    private String getEnvVariable(String rawString) {
        var leftBrace = rawString.indexOf('{') + 1;
        var rightBrace = rawString.lastIndexOf('}');
        var envName = rawString.substring(leftBrace, rightBrace);

        var brIndex = envName.indexOf(":");
        if (brIndex != -1){
            var defVal = envName.substring(brIndex + 1);
            envName = envName.substring(0, brIndex);

            var result = System.getenv(envName);
            if (null == result){
                return defVal;
            }

            return result;
        }
        return System.getenv(envName);
    }
}
