package net.vitic.ddd.lrp.domain.model.repository;

import net.vitic.ddd.lrp.domain.model.PhoneNumberProcess;
import net.vitic.ddd.lrp.infrastructure.persistence.PhoneNumberProcessDao;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PhoneNumberProcessRepo {

    private final PhoneNumberProcessDao dao;

    public PhoneNumberProcessRepo(PhoneNumberProcessDao dao) {this.dao = dao;}

    public Optional<PhoneNumberProcess> processOfId(String id){
        return dao.findById(id);
    }

    public void save(PhoneNumberProcess phoneNumberProcess) {
        dao.save(phoneNumberProcess);
    }
}
