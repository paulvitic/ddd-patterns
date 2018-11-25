package net.vitic.ddd.lrp.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhoneNumbersMatched extends PhoneNumberProcessEvent {

    private String matchedPhoneNumbers;

    public PhoneNumbersMatched(String aProcessId, String aMatchedPhoneNumbers) {
        super(aProcessId, false);

        this.matchedPhoneNumbers = aMatchedPhoneNumbers;
    }

    public String matchedPhoneNumbers() {
        return this.matchedPhoneNumbers;
    }
}
