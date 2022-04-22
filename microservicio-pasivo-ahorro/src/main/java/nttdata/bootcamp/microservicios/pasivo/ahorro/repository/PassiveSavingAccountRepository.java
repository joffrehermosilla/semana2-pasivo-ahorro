package nttdata.bootcamp.microservicios.pasivo.ahorro.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import nttdata.bootcamp.microservicios.pasivo.ahorro.documents.PassiveSavingAccount;

@Repository
public interface PassiveSavingAccountRepository extends ReactiveMongoRepository<PassiveSavingAccount, String> {

}
