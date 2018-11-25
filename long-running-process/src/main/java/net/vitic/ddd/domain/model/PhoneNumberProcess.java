package net.vitic.ddd.domain.model;

import net.vitic.ddd.domain.events.AllPhoneNumbersListed;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Entity
public class PhoneNumberProcess extends AbstractAggregateRoot {

    @Id
    private String id;
    private int matchedPhoneNumbers;
    private int totalPhoneNumbers;

    public PhoneNumberProcess() {
        super();

        this.id = UUID.randomUUID().toString().toUpperCase();
        this.matchedPhoneNumbers = -1;
        this.totalPhoneNumbers = -1;
    }

    public boolean isCompleted() {
        return this.matchedPhoneNumbers() >= 0 && this.totalPhoneNumbers() >= 0;
    }

    public String id() {
        return this.id;
    }

    public int matchedPhoneNumbers() {
        return this.matchedPhoneNumbers;
    }

    public void setMatchedPhoneNumbers(int aMatchedPhoneNumbersCount) {
        this.matchedPhoneNumbers = aMatchedPhoneNumbersCount;
    }

    public int totalPhoneNumbers() {
        return this.totalPhoneNumbers;
    }

    public void setTotalPhoneNumbers(int aTotalPhoneNumberCount) {
        this.totalPhoneNumbers = aTotalPhoneNumberCount;
    }

    public boolean hasTimedOut(){
        // TODO implement
        return false;
    }

    public PhoneNumberProcess startProcess(List<String> phoneNumbers) {
        String allPhoneNumbers = "";

        for (String phoneNumber : phoneNumbers) {
            if (!allPhoneNumbers.isEmpty()) {
                allPhoneNumbers = allPhoneNumbers + "\n";
            }

            allPhoneNumbers = allPhoneNumbers + phoneNumber;
        }

        registerEvent(new AllPhoneNumbersListed(
            id,
            allPhoneNumbers));

        return this;
    }
}