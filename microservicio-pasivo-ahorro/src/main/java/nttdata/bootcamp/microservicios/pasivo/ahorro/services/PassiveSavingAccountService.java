package nttdata.bootcamp.microservicios.pasivo.ahorro.services;

import nttdata.bootcamp.microservicios.pasivo.ahorro.documents.PassiveSavingAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PassiveSavingAccountService {
	public Mono<PassiveSavingAccount> findById(String id);

	public Flux<PassiveSavingAccount> findAlls();

	public Mono<PassiveSavingAccount> saves(PassiveSavingAccount document);

	public Mono<Void> delete(PassiveSavingAccount document);
}
