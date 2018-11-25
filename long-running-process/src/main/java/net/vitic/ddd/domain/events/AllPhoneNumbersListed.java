package net.vitic.ddd.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllPhoneNumbersListed extends PhoneNumberProcessEvent {

    private String allPhoneNumbers;

    public AllPhoneNumbersListed(String aProcessId, String aPhoneNumbersArray) {
        super(aProcessId, false);

        this.allPhoneNumbers = aPhoneNumbersArray;
    }

    public String allPhoneNumbers() {
        return this.allPhoneNumbers;
    }
}
