package com.github.marcoslsouza.api_forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Habilita o modulo de segurança da aplicação
@EnableWebSecurity
// Classe de configuracao (tera alguns beans)
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	
}
