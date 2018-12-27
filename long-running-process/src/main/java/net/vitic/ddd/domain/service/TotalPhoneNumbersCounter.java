package net.vitic.ddd.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.event.AllPhoneNumbersCounted;
import net.vitic.ddd.domain.event.AllPhoneNumbersListed;
import net.vitic.ddd.domain.event.DomainEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TotalPhoneNumbersCounter implements PhoneNumberProcessor<AllPhoneNumbersListed> {

    @Override
    public Optional<DomainEvent> process(AllPhoneNumbersListed event) {

        log.info("AllPhoneNumbersListed (to total)...");

        String allPhoneNumbers = event.allPhoneNumbers();

        String[] allPhoneNumbersToCount = allPhoneNumbers.split("\n");

        DomainEvent newEvent = new AllPhoneNumbersCounted(
            event.aggregateId(),
            allPhoneNumbersToCount.length);

        return Optional.of(newEvent);
    }

    @Override
    public List<String> handles() {
        return Collections.singletonList(AllPhoneNumbersListed.class.getName());
    }
}
