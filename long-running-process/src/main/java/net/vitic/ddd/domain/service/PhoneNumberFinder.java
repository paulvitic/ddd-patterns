package net.vitic.ddd.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.AllPhoneNumbersListed;
import net.vitic.ddd.domain.event.DomainEvent;
import net.vitic.ddd.domain.event.PhoneNumbersMatched;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PhoneNumberFinder implements PhoneNumberProcessor<AllPhoneNumbersListed> {

    @Override
    public Optional<DomainEvent> process(AllPhoneNumbersListed event) {
        log.info("AllPhoneNumbersListed (to match)...");

        String allPhoneNumbers = event.allPhoneNumbers();

        String[] allPhoneNumbersToSearch = allPhoneNumbers.split("\n");

        String foundPhoneNumbers = "";

        for (String phoneNumber : allPhoneNumbersToSearch) {
            if (phoneNumber.contains("303-")) {
                if (!foundPhoneNumbers.isEmpty()) {
                    foundPhoneNumbers = foundPhoneNumbers + "\n";
                }
                foundPhoneNumbers = foundPhoneNumbers + phoneNumber;
            }
        }

        DomainEvent newEvent = new PhoneNumbersMatched(
            event.getProcessId(),
            foundPhoneNumbers);

        return Optional.of(newEvent);
    }

    @Override
    public List<String> handles() {
        return Collections.singletonList(AllPhoneNumbersListed.class.getName());
    }
}