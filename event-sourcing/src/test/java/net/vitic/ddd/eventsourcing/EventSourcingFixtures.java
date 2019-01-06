package net.vitic.ddd.eventsourcing;


import net.vitic.ddd.eventsourcing.domain.event.CustomerCreated;
import net.vitic.ddd.eventsourcing.domain.event.CustomerFirstNameUpdated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventSourcingFixtures {

    public static final String CUSTOMER_FIRST_NAME_0 = "FirstName";
    public static final String CUSTOMER_FIRST_NAME_1 = "UpdatedFirstName";
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

    public static CustomerCreated customerCreatedEvent(){
        return new CustomerCreated(CUSTOMER_ID,
                            CUSTOMER_FIRST_NAME_0,
                            CUSTOMER_LAST_NAME,
                            CUSTOMER_BIRTH_DATE);
    }

    public static CustomerFirstNameUpdated customerFirstNameUpdatedEvent(){
        return new CustomerFirstNameUpdated(CUSTOMER_ID,
                                            CUSTOMER_FIRST_NAME_1);
    }
}
