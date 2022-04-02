package br.com.domiciano.project.crud.appserver;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"br.com.domiciano.project.crud"})
@ComponentScan(basePackages = {"br.com.domiciano.project.crud"})
@EnableJpaRepositories(basePackages = {"br.com.domiciano.project.crud"})
@SpringBootApplication
public class CrudCarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudCarsApplication.class, args);
    }

}
