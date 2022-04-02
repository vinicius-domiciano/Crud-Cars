package br.com.domiciano.project.crud.appserver.unit;

import br.com.domiciano.project.crud.appserver.controller.CarController;
import br.com.domiciano.project.crud.appserver.unit.annotation.CustomInitTest;
import br.com.domiciano.project.crud.base.dto.ErrorExceptionDto;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.base.exceptions.handle.ExceptionHandle;
import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;
import br.com.domiciano.project.crud.car.dto.SaveCarDto;
import br.com.domiciano.project.crud.car.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest
@CustomInitTest
public class CarControllerTest {

    @Autowired
    private CarController carController;

    @Autowired
    private ExceptionHandle exceptionHandle;

    @MockBean
    private CarService carService;

    @BeforeEach
    public void setup() {
        standaloneSetup(this.carController, this.exceptionHandle);
    }

    @Test
    public void haveToReturnSuccess_inListCars() throws JsonProcessingException {
        ListCarDto listCarDto1 = new ListCarDto(1L, "CarName1", 78000.00, "MyTestCompany", Calendar.getInstance());
        ListCarDto listCarDto2 = new ListCarDto(2L, "CarName2", 88000.00, "MyTestCompany", Calendar.getInstance());
        when(this.carService.listCars())
                .thenReturn(List.of(listCarDto1, listCarDto2));

        String bodyResult = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writeValueAsString(List.of(listCarDto1, listCarDto2));

        given().when()
                .get("api/cars")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));

    }

    @Test
    public void haveToReturnSuccess_inFindCardById() throws JsonProcessingException {
        FindCarDto findCarDto = new FindCarDto(1L, Calendar.getInstance(), Calendar.getInstance(), new BigDecimal("150000"), "CarName", "MyTestCompany", 2022);

        when(this.carService.findCarById(1L))
                .thenReturn(findCarDto);

        String bodyResult = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writeValueAsString(findCarDto);

        given().when()
                .get("api/cars/{id}", 1L)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));
    }

    @Test
    public void haveToReturnError404WhenSetInvalidId_inFindCardById() throws JsonProcessingException {

        Calendar now = Calendar.getInstance();

        doThrow(new NotFoundException(String.format("Car not found for id[%s]", 1L)))
                .when(this.carService)
                .findCarById(1L);

        var errorDto = given().when()
                .get("api/cars/{id}", 1L)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(NOT_FOUND.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        assertEquals(1, errorDto.getMessages().size());
        assertTrue(errorDto.getMessages().contains("Car not found for id[1]"));
        assertEquals("/api/cars/1", errorDto.getPath());
        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }

    @Test
    public void haveToReturnSuccess_inSave() throws JsonProcessingException {
        var saveCarDto = new SaveCarDto(null, "MyCarTest", "MyCompany", 2022, new BigDecimal("250000.00"), null, null);
        var carDtoSaved = new SaveCarDto(1L, "MyCarTest", "MyCompany", 2022, new BigDecimal("250000.00"), Calendar.getInstance(), Calendar.getInstance());

        when(this.carService.save(saveCarDto))
                .thenReturn(carDtoSaved);

        String bodyResult = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writeValueAsString(carDtoSaved);

        given().contentType(JSON)
                .accept(JSON)
                .body(saveCarDto)
                .when()
                .post("api/cars")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));

    }

}
