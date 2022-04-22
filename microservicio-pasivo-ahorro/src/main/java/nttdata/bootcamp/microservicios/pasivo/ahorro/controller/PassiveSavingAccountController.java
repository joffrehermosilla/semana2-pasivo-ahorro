package nttdata.bootcamp.microservicios.pasivo.ahorro.controller;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nttdata.bootcamp.microservicios.pasivo.ahorro.documents.PassiveSavingAccount;
import nttdata.bootcamp.microservicios.pasivo.ahorro.services.PassiveSavingAccountService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PassiveSavingAccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PassiveSavingAccountController.class);

	@Autowired
	private PassiveSavingAccountService service;

	@GetMapping("/all")
	public Flux<PassiveSavingAccount> searchAll() {
		Flux<PassiveSavingAccount> passivesaving = service.findAlls();
		LOGGER.info("PASSIVE SAVING ACCOUNT registered: " + passivesaving);
		return passivesaving;
	}

	@GetMapping("/id/{id}")
	public Mono<PassiveSavingAccount> searchById(@PathVariable String id) {
		LOGGER.info("Passive Saving Account id: " + service.findById(id) + " code : " + id);
		return service.findById(id);
	}

	@PostMapping("/create-passive-saving-account")
	public Mono<PassiveSavingAccount> passiveSavingAccount(
			@Valid @RequestBody PassiveSavingAccount passiveSavingAccount) {
		LOGGER.info("PASSIVE SAVING ACCOUNT create: " + service.saves(passiveSavingAccount));
		return service.saves(passiveSavingAccount);
	}

}
