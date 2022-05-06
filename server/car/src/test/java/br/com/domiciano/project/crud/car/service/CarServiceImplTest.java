package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.annotation.CustomInitTest;
import br.com.domiciano.project.crud.car.dto.CreateCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@CustomInitTest
class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;

    private final CarServiceImpl carService;

    @Autowired
    public CarServiceImplTest(CarServiceImpl carService) {
        this.carService = carService;
    }

    @BeforeEach
    public void setup() {
        standaloneSetup(this.carService);
    }

    @Test
    void haveToListCarsAndReturnDto_inListCars() {
        var cars = List.of(
                new Car(1L, Calendar.getInstance(), Calendar.getInstance(), "FirstCar", 2022, new BigDecimal("20000.00"), "MyTestCompany"),
                new Car(2L, Calendar.getInstance(), Calendar.getInstance(), "SecondCar", 2022, new BigDecimal("20000.00"), "MyTestCompany"),
                new Car(3L, Calendar.getInstance(), Calendar.getInstance(), "ThirdCar", 2022, new BigDecimal("20000.00"), "MyTestCompany")
        );

        when(this.carRepository.findAll())
                .thenReturn(cars);

        var resultReturn = List.of(
                new ListCarDto(1L, "FirstCar", new BigDecimal("20000.00"), "MyTestCompany", Calendar.getInstance()),
                new ListCarDto(1L, "SecondCar", new BigDecimal("20000.00"), "MyTestCompany", Calendar.getInstance()),
                new ListCarDto(1L, "ThirdCar", new BigDecimal("20000.00"), "MyTestCompany", Calendar.getInstance())
        );

        var carsDto = this.carService.listCars();

        assertEquals(carsDto.size(), resultReturn.size());
        assertTrue(this.getIds(carsDto).containsAll(this.getIds(resultReturn)));
    }

    private List<Long> getIds(List<ListCarDto> carsDto) {
        return carsDto.stream()
                .map(ListCarDto::getId)
                .collect(toList());
    }

    @Test
    void haveToSaveAndReturnDtoObject_inSaveCar() {
        var now = Calendar.getInstance();

        var request = new CreateCarDto(null, "HelloWorld", "MyCompany", 2022, new BigDecimal("25.00"), null, null);
        var response = new CreateCarDto(1L, "HelloWorld", "MyCompany", 2022, new BigDecimal("25.00"), now, now);

        var requestRepository = new Car("HelloWorld", 2022, new BigDecimal("25.00"), "MyCompany");
        var responseRepository = new Car(1L, now, now, "HelloWorld", 2022, new BigDecimal("25.00"), "MyCompany");

        when(this.carRepository.save(requestRepository))
                .thenReturn(responseRepository);

        var result = this.carService.save(request);

        assertEquals(response.getId(), result.getId());
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getCompany(), result.getCompany());
        assertEquals(response.getYear(), result.getYear());
        assertTrue(response.getPrice().equals(result.getPrice()));
        assertEquals(response.getDateCreated(), result.getDateCreated());
        assertEquals(response.getDateUpdated(), result.getDateUpdated());
    }

}
