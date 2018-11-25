package net.vitic.ddd.lrp.application.service;

import lombok.extern.slf4j.Slf4j;
import net.vitic.ddd.lrp.application.command.StartProcessCommand;
import net.vitic.ddd.lrp.domain.model.PhoneNumberProcess;
import net.vitic.ddd.lrp.domain.model.repository.PhoneNumberProcessRepo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class PhoneNumberProcessService {

    private final PhoneNumberProcessRepo repo;


    public PhoneNumberProcessService(PhoneNumberProcessRepo phoneNumberProcessRepo) {
        this.repo = phoneNumberProcessRepo;
    }

    @Transactional
    public void startProcess(StartProcessCommand command){

        PhoneNumberProcess process = new PhoneNumberProcess();

        log.info("STARTING: " + process.id());

        repo.save(process.startProcess(command.getPhoneNumbers()));
    }
}
