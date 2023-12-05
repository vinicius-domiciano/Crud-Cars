//package br.com.domiciano.project.crud.appserver.unit.controller;
//
//import br.com.domiciano.project.crud.appserver.controller.CarController;
//import br.com.domiciano.project.crud.appserver.unit.annotation.CustomInitTest;
//import br.com.domiciano.project.crud.base.dto.ErrorExceptionDto;
//import br.com.domiciano.project.crud.base.exceptions.BadRequestException;
//import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
//import br.com.domiciano.project.crud.base.exceptions.handle.ExceptionHandle;
//import br.com.domiciano.project.crud.car.dto.*;
//import br.com.domiciano.project.crud.car.service.CarService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Set;
//
//import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT;
//import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
//import static io.restassured.http.ContentType.JSON;
//import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.http.HttpStatus.*;
//
//@CustomInitTest
//class CarControllerTest {
//
//    private final CarController carController;
//    private final ExceptionHandle exceptionHandle;
//
//    @MockBean
//    private CarService carService;
//
//    @Autowired
//    public CarControllerTest(CarController carController, ExceptionHandle exceptionHandle) {
//        this.carController = carController;
//        this.exceptionHandle = exceptionHandle;
//    }
//
//    @BeforeEach
//    public void setup() {
//        standaloneSetup(this.carController, this.exceptionHandle);
//    }
//
//    @Test
//    void haveToReturnSuccess_inListCars() throws JsonProcessingException {
//        ListCarDto listCarDto1 = new ListCarDto(1L, "CarName1", new BigDecimal("78000.00"), new CompanyCarDto(1L, "MyTestCompany"), Calendar.getInstance());
//        ListCarDto listCarDto2 = new ListCarDto(2L, "CarName2", new BigDecimal("88000.00"), new CompanyCarDto(1L, "MyTestCompany"), Calendar.getInstance());
//        when(this.carService.listCars())
//                .thenReturn(List.of(listCarDto1, listCarDto2));
//
//        String bodyResult = new ObjectMapper()
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
//                .writeValueAsString(List.of(listCarDto1, listCarDto2));
//
//        given().when()
//                .get("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(OK.value())
//                .contentType(JSON)
//                .body(is(equalTo(bodyResult)));
//
//    }
//
//    @Test
//    void haveToReturnSuccess_inFindCardById() throws JsonProcessingException {
//        FindCarDto findCarDto = new FindCarDto(1L, Calendar.getInstance(), Calendar.getInstance(), new BigDecimal("150000"), "CarName", new CompanyCarDto(1L, "MyTestCompany"), 2022);
//
//        when(this.carService.findCarById(1L))
//                .thenReturn(findCarDto);
//
//        String bodyResult = new ObjectMapper()
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
//                .writeValueAsString(findCarDto);
//
//        given().when()
//                .get("api/cars/{id}", 1L)
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(OK.value())
//                .contentType(JSON)
//                .body(is(equalTo(bodyResult)));
//    }
//
//    @Test
//    void haveToReturnError404WhenSetInvalidId_inFindCardById() {
//        var now = Calendar.getInstance();
//
//        doThrow(new NotFoundException(CAR_NOT_FOUND_ID_FORMAT, 1L))
//                .when(this.carService)
//                .findCarById(1L);
//
//        var errorDto = given().when()
//                .get("api/cars/{id}", 1L)
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(NOT_FOUND.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(1, errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().contains(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L)));
//        assertEquals("/api/cars/1", errorDto.getPath());
//        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnSuccess_inSave() throws JsonProcessingException {
//        var saveCarDto = new CreateCarDto(null, "MyCarTest", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("250000.00"), null, null);
//        var carDtoSaved = new CreateCarDto(1L, "MyCarTest", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("250000.00"), Calendar.getInstance(), Calendar.getInstance());
//
//        when(this.carService.save(saveCarDto))
//                .thenReturn(carDtoSaved);
//
//        var bodyResult = new ObjectMapper()
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
//                .writeValueAsString(carDtoSaved);
//
//        given().contentType(JSON)
//                .accept(JSON)
//                .body(saveCarDto)
//                .when()
//                .post("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(CREATED.value())
//                .contentType(JSON)
//                .body(is(equalTo(bodyResult)));
//
//    }
//
//    @Test
//    void haveToReturnError400WhenSetInvalidFieldInBody_inSave() {
//        var saveCarDto = new CreateCarDto();
//        var now = Calendar.getInstance();
//
//        var errors = Set.of(
//                "Necessario informar o ano",
//                "Necessario informar o nome",
//                "Necessario informar o preco",
//                "Necessario informar a marca"
//        );
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(saveCarDto)
//                .when()
//                .post("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(BAD_REQUEST.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnError400WhenNotSetCompanyInBody_inSave() {
//        var saveCarDto = new CreateCarDto(null, "Tests", new CompanyCarDto(), 2018, new BigDecimal("190000.0"), null, null);
//        var now = Calendar.getInstance();
//
//        doThrow(new BadRequestException("Is need to set company id."))
//                .when(this.carService)
//                .save(saveCarDto);
//
//        var errors = Set.of("Is need to set company id.");
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(saveCarDto)
//                .when()
//                .post("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(BAD_REQUEST.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnError400WhenSetInvalidFieldsNumberInBody_inSave() {
//        var saveCarDto = new CreateCarDto(null, "Tests", new CompanyCarDto(1L, "MyTestCompany"), 1800, new BigDecimal("0.0"), null, null);
//        var now = Calendar.getInstance();
//
//        var errors = Set.of(
//                "Necessario informar o ano maior que 1900",
//                "Necessario informar o preco maior que 0"
//        );
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(saveCarDto)
//                .when()
//                .post("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(BAD_REQUEST.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnError404WhenSetInvalidCompanyIdInBody_inSave() {
//        var saveCarDto = new CreateCarDto(null, "Tests", new CompanyCarDto(1L, "MyTestCompany"), 2018, new BigDecimal("190000.0"), null, null);
//        var now = Calendar.getInstance();
//
//        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L))
//                .when(this.carService)
//                .save(saveCarDto);
//
//        var errors = Set.of(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L));
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(saveCarDto)
//                .when()
//                .post("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(NOT_FOUND.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnSuccess_inUpdate() throws JsonProcessingException {
//        var updateCarDto = new UpdateCarDto(1L, "MyCarTest", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("250000.00"),  null);
//        var carDtoUpdated = new UpdateCarDto(1L, "MyCarTest", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("250000.00"), Calendar.getInstance());
//
//        when(this.carService.update(updateCarDto))
//                .thenReturn(carDtoUpdated);
//
//        var bodyResult = new ObjectMapper()
//                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
//                .writeValueAsString(carDtoUpdated);
//
//        given().contentType(JSON)
//                .accept(JSON)
//                .body(updateCarDto)
//                .when()
//                .put("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(OK.value())
//                .contentType(JSON)
//                .body(is(equalTo(bodyResult)));
//
//    }
//
//    @Test
//    void haveToReturnError400WhenSetInvalidFieldInBody_inUpdate() {
//        var updateCarDto = new UpdateCarDto();
//        var now = Calendar.getInstance();
//
//        var errors = Set.of(
//                "Necessario informar o id",
//                "Necessario informar o ano",
//                "Necessario informar o nome",
//                "Necessario informar o preco",
//                "Necessario informar a marca"
//        );
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(updateCarDto)
//                .when()
//                .put("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(BAD_REQUEST.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturn404WhenUpdateInvalidId_inUpdate() {
//        var updateCarDto = new UpdateCarDto(1L, "MyCarTest", new CompanyCarDto(1L, "MyTestCompany"), 2022, new BigDecimal("250000.00"),  null);
//        var now = Calendar.getInstance();
//
//        doThrow(new NotFoundException(CAR_NOT_FOUND_ID_FORMAT, 1L))
//                .when(this.carService)
//                .update(updateCarDto);
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(updateCarDto)
//                .when()
//                .put("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(NOT_FOUND.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(1, errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().contains(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L)));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnError400WhenSetInvalidFieldsNumberInBody_inUpdate() {
//        var updateCarDto = new UpdateCarDto(0L, "Tests", new CompanyCarDto(1L, "MyTestCompany"), 1800, new BigDecimal("0.0"), null);
//        var now = Calendar.getInstance();
//
//        var errors = Set.of(
//                "Id deve ser maior que 0",
//                "Necessario informar o ano maior que 1900",
//                "Necessario informar o preco maior que 0"
//        );
//
//        var errorDto = given().contentType(JSON)
//                .accept(JSON)
//                .body(updateCarDto)
//                .when()
//                .put("api/cars")
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(BAD_REQUEST.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
//        assertEquals("/api/cars", errorDto.getPath());
//        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//
//    @Test
//    void haveToReturnSuccess_inDelete() {
//
//        given().accept(JSON)
//                .when()
//                .delete("api/cars/{id}", 1L)
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(NO_CONTENT.value());
//
//    }
//
//    @Test
//    void haveToReturnError404WhenSendInvalidId_inDelete() {
//        var now = Calendar.getInstance();
//
//        doThrow(new NotFoundException(CAR_NOT_FOUND_ID_FORMAT, 1L))
//                .when(this.carService)
//                .delete(1L);
//
//        var errorDto = given().accept(JSON)
//                .when()
//                .delete("api/cars/{id}", 1L)
//                .then()
//                .log()
//                .ifValidationFails()
//                .statusCode(NOT_FOUND.value())
//                .contentType(JSON)
//                .extract()
//                .as(ErrorExceptionDto.class);
//
//        assertEquals(1, errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().contains(String.format(CAR_NOT_FOUND_ID_FORMAT, 1L)));
//        assertEquals("/api/cars/1", errorDto.getPath());
//        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
//        assertTrue(now.before(errorDto.getTimestamp()));
//    }
//}
