package net.vitic.ddd.readmodel.customerorder;

import net.vitic.ddd.readmodel.customerorder.domain.event.CustomerAdded;

public class ReadModelCustomerOrderFixtures {

    public static final String CUSTOMER_FIRST_NAME = "FirstName";
    public static final String CUSTOMER_LAST_NAME = "LastName";
    public static final String CUSTOMER_ID = "181230-1A2B3C4D";

    public static CustomerAdded customerAddedEvent(){
        return new CustomerAdded(CUSTOMER_ID,
                            CUSTOMER_FIRST_NAME,
                            CUSTOMER_LAST_NAME);
    }
}
