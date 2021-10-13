package com.github.marcoslsouza.api_forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@RequestMapping("/")
	@ResponseBody // Retorna a string direto para o navegador, pois nao e uma pagina para exibir
	public String hello() {
		return "Hello World!";
	}
}
