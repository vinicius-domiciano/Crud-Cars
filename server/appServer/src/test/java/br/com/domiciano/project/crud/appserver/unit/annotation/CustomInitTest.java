package br.com.domiciano.project.crud.appserver.unit.annotation;

import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@MockBeans({
        @MockBean(CarRepository.class),
        @MockBean(CompanyRepository.class)
})
public @interface CustomInitTest {
}
