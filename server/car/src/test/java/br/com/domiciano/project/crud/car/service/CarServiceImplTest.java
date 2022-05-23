package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.base.exceptions.BadRequestException;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.annotation.CustomInitTest;
import br.com.domiciano.project.crud.car.dto.*;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT;
import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@CustomInitTest
class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private CompanyCarService companyCarService;

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
                new Car(1L, Calendar.getInstance(), Calendar.getInstance(), "FirstCar", 2022, new BigDecimal("20000.00"), new Company(1L, "MyTestCompany")),
                new Car(2L, Calendar.getInstance(), Calendar.getInstance(), "SecondCar", 2022, new BigDecimal("20000.00"), new Company(1L, "MyTestCompany")),
                new Car(3L, Calendar.getInstance(), Calendar.getInstance(), "ThirdCar", 2022, new BigDecimal("20000.00"), new Company(1L, "MyTestCompany"))
        );

        when(this.carRepository.findAll())
                .thenReturn(cars);

        var resultReturn = List.of(
                new ListCarDto(1L, "FirstCar", new BigDecimal("20000.00"), new CompanyCarDto(1L, "MyTestCompany"), Calendar.getInstance()),
                new ListCarDto(1L, "SecondCar", new BigDecimal("20000.00"), new CompanyCarDto(1L, "MyTestCompany"), Calendar.getInstance()),
                new ListCarDto(1L, "ThirdCar", new BigDecimal("20000.00"), new CompanyCarDto(1L, "MyTestCompany"), Calendar.getInstance())
        );

        var carsDto = this.carService.listCars();

        assertEquals(carsDto.size(), resultReturn.size());
        assertTrue(this.getIds(carsDto).containsAll(this.getIds(resultReturn)));
    }

    @Test
    void haveToReturnEmptyList_inListCars() {
        when(this.carRepository.findAll())
                .thenReturn(new ArrayList<>());

        var cars = this.carService.listCars();

        assertTrue(cars.isEmpty());
    }

    private List<Long> getIds(List<ListCarDto> carsDto) {
        return carsDto.stream()
                .map(ListCarDto::getId)
                .collect(toList());
    }

    @Test
    void haveToReturnFindCarDto_inFindById() {
        var now = Calendar.getInstance();

        when(this.carRepository.findById(1L))
                .thenReturn(Optional.of(new Car(1L, now, now, "HelloWorld", 2022, new BigDecimal("25.00"), new Company(1L, "MyTestCompany"))));

        var response = new CreateCarDto(1L, "HelloWorld", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), now, now);
        var carDto = this.carService.findCarById(1L);

        assertEquals(response.getId(), carDto.getId());
        assertEquals(response.getName(), carDto.getName());
        assertEquals(response.getCompany(), carDto.getCompany());
        assertEquals(response.getYear(), carDto.getYear());
        assertEquals(response.getPrice(), carDto.getPrice());
        assertEquals(response.getDateCreated(), carDto.getDateCreated());
        assertEquals(response.getDateUpdated(), carDto.getDateUpdated());
    }

    @Test
    void haveToReturnThrowNotFoundCar_inFindCarById() {
        when(this.carRepository.findById(1L))
            .thenReturn(Optional.empty());

        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            this.carService.findCarById(1L);
        } catch(NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(404, statusCode);
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L), message);
        assertTrue(isError);
    }

    @Test
    void haveToSaveAndReturnDtoObject_inSaveCar() {
        var now = Calendar.getInstance();

        var request = new CreateCarDto(null, "HelloWorld", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), null, null);
        var response = new CreateCarDto(1L, "HelloWorld", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), now, now);

        var requestRepository = new Car("HelloWorld", 2022, new BigDecimal("25.00"), new Company(1L,"MyTestCompany"));
        var responseRepository = new Car(1L, now, now, "HelloWorld", 2022, new BigDecimal("25.00"), new Company(1L,"MyTestCompany"));

        when(this.carRepository.save(requestRepository))
                .thenReturn(responseRepository);

        var result = this.carService.save(request);

        assertEquals(response.getId(), result.getId());
        assertEquals(response.getName(), result.getName());
        assertEquals(response.getCompany(), result.getCompany());
        assertEquals(response.getYear(), result.getYear());
        assertEquals(response.getPrice(), result.getPrice());
        assertEquals(response.getDateCreated(), result.getDateCreated());
        assertEquals(response.getDateUpdated(), result.getDateUpdated());
    }

    @Test
    void haveToReturnErrorBadRequestWhenNotToSetCompanyAndNotSetCompanyId_inSaveCar() {
        var request = new CreateCarDto(null, "HelloWorld", null, 2022, new BigDecimal("25.00"), null, null);
        BadRequestException exception = assertThrows(
                "Expected carService.save(request) to throw",
                BadRequestException.class,
                () -> this.carService.save(request)
        );

        assertEquals(400, exception.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception.getMessage());

        var request2 = new CreateCarDto(null, "HelloWorld", new CompanyCarDto(), 2022, new BigDecimal("25.00"), null, null);
        BadRequestException exception2 = assertThrows(
                "Expected carService.save(request) to throw",
                BadRequestException.class,
                () -> this.carService.save(request2)
        );

        assertEquals(400, exception2.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception2.getMessage());
    }

    @Test
    void haveToReturnErrorNotFoundCompany_inSaveCar() {
        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L))
                .when(this.companyCarService)
                .findById(1L);

        var request = new CreateCarDto(null, "HelloWorld", new CompanyCarDto(1L, "ABCDE"), 2022, new BigDecimal("25.00"), null, null);
        NotFoundException exception = assertThrows(
                "Expected carService.save(request) to throw",
                NotFoundException.class,
                () -> this.carService.save(request)
        );

        assertEquals(404, exception.getHttpStatus().value());
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    void haveToUpdateAndReturnDtoObject_inUpdateCar() {
        var car = new Car(1L, Calendar.getInstance(), Calendar.getInstance(), "HelloWorld", 2022, new BigDecimal("25.00"), new Company(1L, "MyTestCompany"));

        when(this.carRepository.findById(1L))
                .thenReturn(Optional.of(car));

        var request = new UpdateCarDto(1L, "MyName", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), null);
        var carUpdate = new Car(1L, null, null, "MyName", 2022, new BigDecimal("25.00"), new Company(1L, "MyTestCompany"));

        when(this.carRepository.save(carUpdate))
                .thenReturn(carUpdate);

        var response = this.carService.update(request);

        assertEquals(response.getId(), request.getId());
        assertEquals(response.getName(), request.getName());
        assertNotEquals(response.getName(), car.getName());
        assertEquals(response.getCompany(), request.getCompany());
        assertEquals(response.getYear(), request.getYear());
        assertEquals(response.getPrice(), request.getPrice());
        assertNotEquals(response.getDateUpdated(), car.getDateUpdated());
    }

    @Test
    void haveToReturnErrorNotFoundCar_inUpdateCar() {
        when(this.carRepository.findById(1L))
                .thenReturn(Optional.empty());

        when(this.companyCarService.findById(1L))
                .thenReturn(new FindCompanyDto());

        var request = new UpdateCarDto(1L, "MyName", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), null);
        NotFoundException exception = assertThrows(
                "Expected carService.update(request) to throw",
                NotFoundException.class,
                () -> this.carService.update(request)
        );

        assertEquals(404, exception.getHttpStatus().value());
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    void haveToReturnErrorBadRequestWhenNotToSetCompanyAndNotSetCompanyId_inUpdateCar() {
        var request = new UpdateCarDto(1L, "MyName",null, 2022, new BigDecimal("25.00"), null);
        BadRequestException exception = assertThrows(
                "Expected carService.update(request) to throw",
                BadRequestException.class,
                () -> this.carService.update(request)
        );

        assertEquals(400, exception.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception.getMessage());

        var request2 = new UpdateCarDto(1L, "MyName",new CompanyCarDto(), 2022, new BigDecimal("25.00"), null);
        BadRequestException exception2 = assertThrows(
                "Expected carService.update(request) to throw",
                BadRequestException.class,
                () -> this.carService.update(request2)
        );

        assertEquals(400, exception2.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception2.getMessage());
    }

    @Test
    void haveToReturnErrorNotFoundCompany_inUpdateCar() {
        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L))
                .when(this.companyCarService)
                .findById(1L);

        var request = new UpdateCarDto(1L, "MyName", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("25.00"), null);
        NotFoundException exception = assertThrows(
                "Expected carService.update(request) to throw",
                NotFoundException.class,
                () -> this.carService.update(request)
        );

        assertEquals(404, exception.getHttpStatus().value());
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    void haveToDeleteCar_inDeleteCar() {
        var car = new Car(1L, Calendar.getInstance(), Calendar.getInstance(), "HelloWorld", 2022, new BigDecimal("25.00"), new Company(1L, "MyCompany"));

        when(this.carRepository.findById(1L))
                .thenReturn(Optional.of(car));

        assertDoesNotThrow(() -> this.carService.delete(1L));
    }

    @Test
    void haveToReturnNotFound_inDeleteCar() {
        when(this.carRepository.findById(2L))
                .thenReturn(Optional.empty());

        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            this.carService.delete(2L);
        } catch(NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(404, statusCode);
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 2L), message);
        assertTrue(isError);
    }

}
