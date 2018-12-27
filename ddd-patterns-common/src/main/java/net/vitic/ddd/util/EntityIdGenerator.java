package net.vitic.ddd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EntityIdGenerator {

    public static String generate(String prefix){
        return prefix +
                "-" +
                new SimpleDateFormat("yyMMdd").format(new Date()) +
                "-" +
                UUID.randomUUID().toString().toUpperCase().substring(0, 8);
    }
}
