package net.vitic.ddd.infrastructure.persistence;

import net.vitic.ddd.model.PhoneNumberProcess;
import org.springframework.data.repository.CrudRepository;

public interface PhoneNumberProcessDao extends CrudRepository<PhoneNumberProcess, String>  {
}
