package nttdata.bootcamp.microservicios.pasivo.ahorro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nttdata.bootcamp.microservicios.pasivo.ahorro.documents.PassiveSavingAccount;
import nttdata.bootcamp.microservicios.pasivo.ahorro.repository.PassiveSavingAccountRepository;
import nttdata.bootcamp.microservicios.pasivo.ahorro.services.PassiveSavingAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PassiveSavingAccountServiceImpl implements PassiveSavingAccountService {

	@Autowired
	PassiveSavingAccountRepository repository;

	@Override
	public Mono<PassiveSavingAccount> findById(String id) {

		return repository.findById(id);
	}

	@Override
	public Flux<PassiveSavingAccount> findAlls() {

		return repository.findAll();
	}

	@Override
	public Mono<PassiveSavingAccount> saves(PassiveSavingAccount document) {

		return repository.save(document);
	}

	@Override
	public Mono<Void> delete(PassiveSavingAccount document) {

		return repository.delete(document);
	}

}
