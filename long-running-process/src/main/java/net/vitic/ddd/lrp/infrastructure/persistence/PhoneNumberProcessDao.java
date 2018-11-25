package net.vitic.ddd.lrp.infrastructure.persistence;

import net.vitic.ddd.lrp.domain.model.PhoneNumberProcess;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberProcessDao extends CrudRepository<PhoneNumberProcess, String>  {
}
