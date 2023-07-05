package com.sayhello.say;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayController {
  @GetMapping("/")
  public String getString(){
    return "Hola Mundo";
  }
}
