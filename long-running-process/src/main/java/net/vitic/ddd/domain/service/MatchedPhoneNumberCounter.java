package net.vitic.ddd.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.events.MatchedPhoneNumbersCounted;
import net.vitic.ddd.domain.events.DomainEvent;
import net.vitic.ddd.domain.events.PhoneNumbersMatched;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class MatchedPhoneNumberCounter implements PhoneNumberProcessor<PhoneNumbersMatched> {

    @Override
    public Optional<DomainEvent> process(PhoneNumbersMatched event) {

        log.info("PhoneNumbersMatched (to count)...");

        String allMatchedPhoneNumbers = event.matchedPhoneNumbers();

        String[] allPhoneNumbersToCount = allMatchedPhoneNumbers.split("\n");

        DomainEvent newEvent = new MatchedPhoneNumbersCounted(
            event.getProcessId(),
            allPhoneNumbersToCount.length);

        return Optional.of(newEvent);
    }

    @Override
    public List<String> handles() {
        return Collections.singletonList(PhoneNumbersMatched.class.getName());
    }
}