package edu.cnm.deepdive.diceware.controller;

import edu.cnm.deepdive.diceware.model.dao.PassphraseRepository;
import edu.cnm.deepdive.diceware.model.entity.Passphrase;
import edu.cnm.deepdive.diceware.model.entity.Word;
import edu.cnm.deepdive.diceware.service.PassphraseGenerator;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/passphrases")
public class PassphraseController {

  private final PassphraseGenerator generator;
  private final PassphraseRepository repository;

  @Autowired
  public PassphraseController(PassphraseGenerator generator, PassphraseRepository repository) {
    this.generator = generator;
    this.repository = repository;
  }

  @GetMapping(value = "/transient", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String[] generate(@RequestParam(defaultValue = "5") int length) {
    return generator.generate(length);
  }

  @GetMapping(value = "/transient", produces = MediaType.TEXT_HTML_VALUE)
  public String generateHtml(@RequestParam(defaultValue = "5") int length, Model model) {
    model.addAttribute("words", generator.generate(length));
    return "passphrase";
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Passphrase post(@RequestBody Passphrase passphrase) {
    // TODO Support specification of full passphrase not just length from client.
    passphrase.getWords().clear(); // FIXME respect the words provided by the client.
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
  @ResponseBody
  public Passphrase get(@PathVariable UUID id) {
    Passphrase passphrase = repository
        .findById(id)
        .orElseThrow();
    return passphrase;
  }

}
