package net.vitic.ddd.lrp.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllPhoneNumbersCounted extends PhoneNumberProcessEvent {

    private int totalPhoneNumbers;

    public AllPhoneNumbersCounted(String aProcessId, int aTotalPhoneNumbersCount) {
        super(aProcessId, false);

        this.totalPhoneNumbers = aTotalPhoneNumbersCount;
    }

    public int totalPhoneNumbers() {
        return this.totalPhoneNumbers;
    }
}
