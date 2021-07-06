package edu.cnm.deepdive.diceware.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorldController {

  @GetMapping("greet")
  public String greet(
      @RequestParam(name = "t", defaultValue = "world") String target) {
    return String.format("Hello, %s!", target);
  }

  @GetMapping("compliment")
  public String beNice(@RequestParam(name = "w", defaultValue = "5") int week) {
    return String.format(
        "Good job in making it to week %d! You now know much more Java than 99+%% of the population.",
        week);
  }

  @GetMapping
  public String explain() {
    return "This is a simple Spring MVC-based web service.";
  }

}
