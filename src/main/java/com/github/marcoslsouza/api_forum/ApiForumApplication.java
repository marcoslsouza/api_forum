package com.github.marcoslsouza.api_forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport // Habilitar a paginacao
public class ApiForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiForumApplication.class, args);
	}

}
