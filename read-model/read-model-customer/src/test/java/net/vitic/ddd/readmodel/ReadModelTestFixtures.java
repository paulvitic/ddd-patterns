package net.vitic.ddd.readmodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadModelTestFixtures {

    public static final String CUSTOMER_FIRST_NAME = "FirstName";
    public static final String CUSTOMER_LAST_NAME = "LastName";
    public static final String CUSTOMER_ID = "181230-1A2B3C4D";
    public static Date CUSTOMER_BIRTH_DATE;

    static {
        try {
            CUSTOMER_BIRTH_DATE = new SimpleDateFormat("mm-dd-yyyy").parse("01-01-2000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
