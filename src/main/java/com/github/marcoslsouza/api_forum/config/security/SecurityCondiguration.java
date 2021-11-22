package com.github.marcoslsouza.api_forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// Ativa o Spring Security
@EnableWebSecurity

// E uma classe de configuracao e com beans e etc.
@Configuration
public class SecurityCondiguration extends WebSecurityConfigurerAdapter {

	
}
