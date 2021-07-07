package edu.cnm.deepdive.diceware.controller;

import edu.cnm.deepdive.diceware.service.PassphraseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/passphrases/transient")
public class TransientPassphraseController {

  private final PassphraseGenerator generator;

  @Autowired
  public TransientPassphraseController(PassphraseGenerator generator) {
    this.generator = generator;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String[] generate(@RequestParam(defaultValue = "5") int length) {
    return generator.generate(length);
  }

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public String generate(@RequestParam(defaultValue = "5") int length, Model model) {
    model.addAttribute("words", generator.generate(length));
    return "passphrase";
  }

}

