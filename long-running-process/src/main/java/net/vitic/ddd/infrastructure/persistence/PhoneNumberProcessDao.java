package net.vitic.ddd.infrastructure.persistence;

import net.vitic.ddd.domain.model.PhoneNumberProcess;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberProcessDao extends CrudRepository<PhoneNumberProcess, String>  {
}
