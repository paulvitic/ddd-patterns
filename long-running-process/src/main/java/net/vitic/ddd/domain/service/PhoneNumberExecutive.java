package net.vitic.ddd.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.domain.events.MatchedPhoneNumbersCounted;
import net.vitic.ddd.domain.events.AllPhoneNumbersCounted;
import net.vitic.ddd.domain.events.DomainEvent;
import net.vitic.ddd.domain.model.repository.PhoneNumberProcessRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PhoneNumberExecutive implements PhoneNumberProcessor<DomainEvent> {

    private final PhoneNumberProcessRepo repo;

    public PhoneNumberExecutive(PhoneNumberProcessRepo phoneNumberProcessRepo) {
        this.repo = phoneNumberProcessRepo;
    }

    @Override
    @Transactional
    public Optional<DomainEvent> process(DomainEvent event) {

        this.repo.processOfId(event.processId())
                 .ifPresent(process -> {

                     if (event.type().equals(AllPhoneNumbersCounted.class.getName())) {
                        process.setTotalPhoneNumbers(((AllPhoneNumbersCounted)event).totalPhoneNumbers());
                        log.info("AllPhoneNumbersCounted...");

                     } else if (event.type().equals(MatchedPhoneNumbersCounted.class.getName())) {
                        process.setMatchedPhoneNumbers(((MatchedPhoneNumbersCounted)event).matchedPhoneNumbers());
                        log.info("MatchedPhoneNumbersCounted...");
                    }

                    if (process.isCompleted()) {
                        log.info(
                            "COMPLETED: "
                            + process.id()
                            + ": "
                            + process.matchedPhoneNumbers()
                            + " of "
                            + process.totalPhoneNumbers()
                            + " phone numbers found.");
                    }
        });

        return Optional.empty();
    }

    @Override
    public List<String> handles() {
        return Arrays.asList(
            AllPhoneNumbersCounted.class.getName(),
            MatchedPhoneNumbersCounted.class.getName());
    }
}