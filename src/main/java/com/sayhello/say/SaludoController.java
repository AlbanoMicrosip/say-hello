package com.sayhello.say;

import org.springframework.web.bind.annotation.RequestMapping;

public interface SaludoController {
  @RequestMapping("/saludo")
  String saludo();
}
