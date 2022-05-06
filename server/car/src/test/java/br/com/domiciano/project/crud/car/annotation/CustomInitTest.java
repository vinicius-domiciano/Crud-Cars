package br.com.domiciano.project.crud.car.annotation;

import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.repository.MarcaCarroRepository;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RunWith(SpringRunner.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@MockBeans({
        @MockBean(CarRepository.class),
        @MockBean(MarcaCarroRepository.class)
})
@WebAppConfiguration
public @interface CustomInitTest {
}
