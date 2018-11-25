package net.vitic.ddd.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MatchedPhoneNumbersCounted extends PhoneNumberProcessEvent {

    private int matchedPhoneNumbers;

    public MatchedPhoneNumbersCounted(String aProcessId, int aMatchedPhoneNumbersCount) {
        super(aProcessId, true);

        this.matchedPhoneNumbers = aMatchedPhoneNumbersCount;
    }

    public int matchedPhoneNumbers() {
        return this.matchedPhoneNumbers;
    }
}
