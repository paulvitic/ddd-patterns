package net.vitic.ddd.model.repository;

import net.vitic.ddd.model.PhoneNumberProcess;
import net.vitic.ddd.infrastructure.persistence.PhoneNumberProcessDao;
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
