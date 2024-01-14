package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.base.exceptions.BadRequestException;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.annotation.CustomInitTest;
import br.com.domiciano.project.crud.car.dto.SaveCarDto;
import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import br.com.domiciano.project.crud.car.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT;
import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@CustomInitTest
@DisplayName("Car service tests")
class CarServiceImplTest {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private CompanyRepository companyRepository;

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
    @DisplayName("List all: Should Return a list of cars")
    void shouldReturnAListOfCarsAndReturnDto_inListCars() {
        final Company company = mock(Company.class);
        final Calendar date = Calendar.getInstance();
        var cars = List.of(
                new Car(1L, date, date, "FirstCar", 2022, new BigDecimal("20000.00"), company),
                new Car(2L, date, date, "SecondCar", 2022, new BigDecimal("20000.00"), company),
                new Car(3L, date, date, "ThirdCar", 2022, new BigDecimal("20000.00"), company)
        );

        when(this.carRepository.findAll()).thenReturn(cars);

        var carsDto = this.carService.listCars();

        assertEquals(carsDto.size(), cars.size());

        Long[] id = getIdArray(cars);
        Long[] ids = getIdArray(carsDto);
        assertArrayEquals(id, ids);

        for (Car car : cars) {
            var dto = carsDto.stream()
                    .filter(d -> d.getId().equals(car.getId()))
                    .findFirst()
                    .get();

            assertEquals(car.getName(), dto.getName());
            assertEquals(car.getDateCreated(), dto.getDateCreated());
        }
    }

    @Test
    @DisplayName("List all: Should return a empty list")
    void haveToReturnEmptyList_inListCars() {
        when(this.carRepository.findAll()).thenReturn(new ArrayList<>());
        var cars = this.carService.listCars();

        assertTrue(cars.isEmpty());
    }

    public <T> Long[] getIdArray(List<T> list) {
        return list.parallelStream().map(t -> {
            try {
                return t.getClass().getDeclaredField("id").get(Long.class);
            } catch (Exception e) {
                return 0L;
            }
        }).toArray(Long[]::new);
    }

    @Test
    @DisplayName("Find by id: Should return a car")
    void shouldReturnFindCarDto_inFindById() {
        var now = Calendar.getInstance();
        final BigDecimal price = new BigDecimal("199000.00");
        final Company company = mock(Company.class);

        when(company.getId()).thenReturn(1L);
        when(company.getName()).thenReturn("Company Test");

        final Long id = 100L;

        when(this.carRepository.findById(1L)).thenReturn(Optional.of(new Car(
                id,
                now,
                now,
                "HelloWorld",
                2022,
                price,
                company,
                price,
                "001",
                "December, 2022",
                "D",
                "Disel"
        )));

        var carDto = this.carService.findCarById(1L);

        assertEquals(id, carDto.getId());
        assertEquals(now, carDto.getDateCreated());
        assertEquals(now, carDto.getDateUpdated());
        assertEquals("HelloWorld", carDto.getName());
        assertEquals((Integer) 2022, carDto.getYear());
        assertEquals(price, carDto.getSalePrice());
        assertEquals(price, carDto.getFipePrice());
        assertEquals("001", carDto.getFipeCode());
        assertEquals("December, 2022", carDto.getReferenceMonth());
        assertEquals("Disel", carDto.getFuel());
        assertEquals((Long) 1L, carDto.getCompany().getId());
        assertEquals("Company Test", carDto.getCompany().getName());

    }

    @Test
    @DisplayName("Find by id: Should return error when id was not found")
    void shouldReturnThrowNotFoundCar_inFindCarById() {
        when(this.carRepository.findById(1L))
                .thenReturn(Optional.empty());

        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            this.carService.findCarById(1L);
        } catch (NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(404, statusCode);
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L), message);
        assertTrue(isError);
    }

    @Test
    @DisplayName("Save: Should save a car")
    void shouldSaveAndReturnDtoObject_inSaveCar() {
        var now = Calendar.getInstance();
        when(companyRepository.findById(any())).thenReturn(Optional.of(mock(Company.class)));

        var request = new SaveCarDto("NewCar", new BigDecimal("200000.00"), new BigDecimal("300000.00"), 2L, 2022, "001", "December, 2022", "D", "Disel");

        AtomicReference<Car> carSave = new AtomicReference<>();
        when(this.carRepository.save(any())).thenAnswer(invocationOnMock -> {
            final Car car = invocationOnMock.getArgument(0);
            car.setId(1L);
            car.setDateCreated(now);
            car.setDateUpdated(now);

            carSave.set(car);

            return car;
        });

        var response = this.carService.save(request);

        assertEquals(carSave.get().getId(), response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getSalePrice(), response.getSalePrice());
        assertEquals(request.getFipePrice(), response.getFipePrice());

        assertEquals(request.getFuel(), response.getFuel());
        assertEquals(request.getYear(), response.getYear());
        assertEquals(carSave.get().getDateCreated(), response.getDateCreated());
        assertEquals(carSave.get().getDateUpdated(), response.getDateUpdated());
    }

    @Test
    @DisplayName("Save: when set company id null or less than 1 should return error")
    void shouldReturnErrorBadRequestWhenNotToSetCompanyAndNotSetCompanyId_inSaveCar() {
        var request = new SaveCarDto();
        BadRequestException exception = assertThrows(
                "Expected carService.save(request) to throw",
                BadRequestException.class,
                () -> this.carService.save(request)
        );

        assertEquals(400, exception.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception.getMessage());

        var request2 = new SaveCarDto();
        request2.setCompanyId(0L);

        BadRequestException exception2 = assertThrows(
                "Expected carService.save(request) to throw",
                BadRequestException.class,
                () -> this.carService.save(request2)
        );

        assertEquals(400, exception2.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception2.getMessage());
    }

    @Test
    @DisplayName("Save: Should return error when company wasn't fount")
    void shouldReturnErrorNotFoundCompany_inSaveCar() {
        var request = new SaveCarDto();
        request.setCompanyId(1L);

        BadRequestException exception = assertThrows(
                "Expected carService.save(request) to throw",
                BadRequestException.class,
                () -> this.carService.save(request)
        );

        assertEquals(400, exception.getHttpStatus().value());
        assertEquals("Company wasn't fount to id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Update: Should update a car")
    void shouldUpdateAndReturnDtoObject_inUpdateCar() {
        final Company company = new Company(1L, false, "Company");
        AtomicReference<Car> carAtomicReference = new AtomicReference<>(new Car(1L, Calendar.getInstance(), Calendar.getInstance(), "HelloWorld", 2022, new BigDecimal("25.00"), company));
        when(this.carRepository.findById(1L)).thenReturn(Optional.of(carAtomicReference.get()));

        var request = new UpdateCarDto(1L, false, "MyName", 2022, new BigDecimal("25.00"), 1L, new BigDecimal("24.00"), "001", "November, 2023", "D", "Disel");

        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));

        when(this.carRepository.save(any())).thenAnswer(invocationOnMock -> {
            carAtomicReference.set(invocationOnMock.getArgument(0));
            return carAtomicReference.get();
        });

        var response = this.carService.update(request);
        assertEquals(request, response);

        var car = carAtomicReference.get();
        assertEquals(request.getId(), car.getId());
        assertEquals("MyName", car.getName());
        assertEquals((Integer) 2022, car.getYear());
        assertEquals(new BigDecimal("25.00"), car.getSalePrice());
        assertEquals((Long) 1L, car.getCompany().getId());
        assertEquals(new BigDecimal("24.00"), car.getFipePrice());
        assertEquals("001", car.getFipeCode());
        assertEquals("November, 2023", car.getReferenceMonth());
        assertEquals("D", car.getFuelAcronym());
        assertEquals("Disel", car.getFuel());
    }

    @Test
    @DisplayName("Update: Should return error when car wasn't fount")
    void shouldReturnErrorNotFoundCar_inUpdateCar() {
        when(this.carRepository.findById(1L)).thenReturn(Optional.empty());

        var request = new UpdateCarDto();
        request.setCompanyId(1L);
        request.setId(1L);

        NotFoundException exception = assertThrows(
                "Expected carService.update(request) to throw",
                NotFoundException.class,
                () -> this.carService.update(request)
        );

        assertEquals(404, exception.getHttpStatus().value());
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    @DisplayName("Update: when set company id null or less than 1 should return error.")
    void shouldReturnErrorBadRequestWhenNotToSetCompanyAndNotSetCompanyId_inUpdateCar() {
        var request = new UpdateCarDto();
        BadRequestException exception = assertThrows(
                "Expected carService.update(request) to throw",
                BadRequestException.class,
                () -> this.carService.update(request)
        );

        assertEquals(400, exception.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception.getMessage());

        var request2 = new UpdateCarDto();
        request.setCompanyId(0L);

        BadRequestException exception2 = assertThrows(
                "Expected carService.update(request) to throw",
                BadRequestException.class,
                () -> this.carService.update(request2)
        );

        assertEquals(400, exception2.getHttpStatus().value());
        assertEquals("Is need to set company id.", exception2.getMessage());
    }

    @Test
    @DisplayName("Update: should return error um company wasn't found")
    void shouldReturnErrorNotFoundCompany_inUpdateCar() {
        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L)).when(this.companyRepository).findById(1L);

        final Car car = mock(Car.class);
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        var request = new UpdateCarDto();
        request.setId(1L);
        request.setCompanyId(1L);

        NotFoundException exception = assertThrows(
                "Expected carService.update(request) to throw",
                NotFoundException.class,
                () -> this.carService.update(request)
        );

        assertEquals(404, exception.getHttpStatus().value());
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    @DisplayName("Delete: should delete a car")
    void shouldDeleteCar_inDeleteCar() {
        when(this.carRepository.findById(1L)).thenReturn(Optional.of(mock(Car.class)));
        assertDoesNotThrow(() -> this.carService.delete(1L));
    }

    @Test
    @DisplayName("Delete: should return error when car id wasn't fount")
    void shouldReturnNotFound_inDeleteCar() {
        when(this.carRepository.findById(2L)).thenReturn(Optional.empty());

        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            this.carService.delete(2L);
        } catch (NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(404, statusCode);
        assertEquals(String.format(CAR_NOT_FOUND_ID_FORMAT, 2L), message);
        assertTrue(isError);
    }

}
