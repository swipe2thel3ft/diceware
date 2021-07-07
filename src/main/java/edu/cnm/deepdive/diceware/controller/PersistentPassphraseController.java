package edu.cnm.deepdive.diceware.controller;

import edu.cnm.deepdive.diceware.model.dao.PassphraseRepository;
import edu.cnm.deepdive.diceware.model.entity.Passphrase;
import edu.cnm.deepdive.diceware.model.entity.Word;
import edu.cnm.deepdive.diceware.service.PassphraseGenerator;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passphrases")
public class PersistentPassphraseController {

  private final PassphraseGenerator generator;
  private final PassphraseRepository repository;

  @Autowired
  public PersistentPassphraseController(
      PassphraseGenerator generator, PassphraseRepository repository) {
    this.generator = generator;
    this.repository = repository;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase post(@RequestBody Passphrase passphrase) {
    // TODO Support specification of full passphrase (not just length) from client.
    passphrase.getWords().clear(); // FIXME Respect the words provided by the client.
    // Assume passphrase.getLength() has a meaningful value.
    String[] words = generator.generate(passphrase.getLength());
    int order = 0;
    for (String w : words) {
      Word word = new Word();
      word.setText(w);
      word.setPassphrase(passphrase);
      word.setOrder(order++);
      passphrase.getWords().add(word);
    }
    return repository.save(passphrase);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Passphrase get(@PathVariable UUID id) {
    return repository
        .findById(id)
        .orElseThrow();
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    repository
        .findById(id)
        .map((passphrase) -> {
          repository.delete(passphrase);
          return null;
        });
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Passphrase> get() {
    return repository.getAllByOrderByCreatedDesc();
  }

}
