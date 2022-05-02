package nttdata.bootcamp.microservicios.pasivo.ahorro.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("passive_saving", service.findAlls());
		return ResponseEntity.ok(response);

	}

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
		Mono.just(passiveSavingAccount).doOnNext(t -> {

			t.setCreateAt(new Date());

		}).onErrorReturn(passiveSavingAccount).onErrorResume(e -> Mono.just(passiveSavingAccount))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));

		Mono<PassiveSavingAccount> newBusinessAsset = service.saves(passiveSavingAccount);

		return newBusinessAsset;
	}

	@PutMapping("/update-passive-saving/{id}")
	public ResponseEntity<Mono<?>> updatePassiveSaving(@PathVariable String id,
			@Valid @RequestBody PassiveSavingAccount passiveSaving) {
		Mono.just(passiveSaving).doOnNext(t -> {
			passiveSaving.setId(id);
			t.setCreateAt(new Date());
		}).onErrorReturn(passiveSaving).onErrorResume(e -> Mono.just(passiveSaving))
				.onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> LOGGER.info(x.toString()));
		Mono<PassiveSavingAccount> newPassiveSaving = service.saves(passiveSaving);
		if (newPassiveSaving != null) {
			return new ResponseEntity<>(newPassiveSaving, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(Mono.just(new PassiveSavingAccount()), HttpStatus.I_AM_A_TEAPOT);
	}

	@DeleteMapping("/delete-passive-saving/{id}")
	public ResponseEntity<Mono<Void>> deletePassiveSaving(@PathVariable String id) {
		PassiveSavingAccount passiveSaving = new PassiveSavingAccount();
		passiveSaving.setId(id);
		Mono<PassiveSavingAccount> newPassiveSaving = service.findById(id);
		newPassiveSaving.subscribe();
		Mono<Void> test = service.delete(passiveSaving);
		test.subscribe();
		return ResponseEntity.noContent().build();
	}

}
