package br.com.domiciano.project.crud.appserver.unit.controller;

import br.com.domiciano.project.crud.appserver.controller.CompanyController;
import br.com.domiciano.project.crud.appserver.unit.annotation.CustomInitTest;
import br.com.domiciano.project.crud.base.dto.ErrorExceptionDto;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.base.exceptions.handle.ExceptionHandle;
import br.com.domiciano.project.crud.car.dto.CreateCompanyDto;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;
import br.com.domiciano.project.crud.car.service.CompanyCarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT;
import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@WebMvcTest
@CustomInitTest
class CompanyControllerTest {

    private final CompanyController companyController;
    private final ExceptionHandle exceptionHandle;

    @MockBean
    private CompanyCarService companyCarService;

    @Autowired
    public CompanyControllerTest(CompanyController companyController, ExceptionHandle exceptionHandle) {
        this.companyController = companyController;
        this.exceptionHandle = exceptionHandle;
    }

    @BeforeEach
    public void setup() {
        standaloneSetup(this.companyController, this.exceptionHandle);
    }

    @Test
    void haveToReturnSuccess_inListAllCompany() throws JsonProcessingException {
        var companies = List.of(
                new ListCompanyDto(1L, "MyCompany1"),
                new ListCompanyDto(2L, "MyCompany2")
        );

        when(this.companyCarService.listAll())
                .thenReturn(companies);

        String bodyResult = new ObjectMapper()
                .writeValueAsString(companies);

        given().accept(JSON)
                .when()
                .get("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));
    }

    @Test
    void haveToReturnSuccess_inFindCompanyById() throws JsonProcessingException {
        var company = new FindCompanyDto(1L, Calendar.getInstance(), Calendar.getInstance(), "companyDescription", "FindCompany");

        when(this.companyCarService.findById(1L))
                .thenReturn(company);

        String bodyResult = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writeValueAsString(company);

        given().accept(JSON)
                .when()
                .get("api/companies/{id}", 1L)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));
    }

    @Test
    void haveToReturnError404WhenSetInvalidId_inFindCompanyById() {
        var now = Calendar.getInstance();

        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L))
                .when(this.companyCarService)
                .findById(1L);

        var errorDto = given().accept(JSON)
                .when()
                .get("api/companies/{id}", 1L)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(NOT_FOUND.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        assertEquals(1, errorDto.getMessages().size());
        assertTrue(errorDto.getMessages().contains(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L)));
        assertEquals("/api/companies/1", errorDto.getPath());
        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }

    @Test
    void haveToReturnSuccess_inSaveCompany() throws JsonProcessingException {
        var requestBody = new CreateCompanyDto(null, "Description", "myCompany", null, null);
        var responseBody = new CreateCompanyDto(1L, "Description", "myCompany", Calendar.getInstance(), Calendar.getInstance());

        when(this.companyCarService.create(requestBody))
                .thenReturn(responseBody);

        String bodyResult = new ObjectMapper()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writeValueAsString(responseBody);

        given().accept(JSON)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(CREATED.value())
                .contentType(JSON)
                .body(is(equalTo(bodyResult)));
    }

    @Test
    void haveToReturnError400WhenSetInvalidFields_inSaveCompany() {
        var requestBody = new CreateCompanyDto();
        var now = Calendar.getInstance();

        var errorDto = given().accept(JSON)
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        var errorMessages = Set.of(
                "Necessario informar o nomeMarca"
        );

        assertEquals(errorMessages.size(), errorDto.getMessages().size());
        assertTrue(errorDto.getMessages().containsAll(errorMessages));
        assertEquals("/api/companies", errorDto.getPath());
        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }

    @Test
    void haveToReturnSuccess_inUpdate() throws JsonProcessingException {
        var updateCompanyRequest = new UpdateCompanyDto(1L, "MyUpdateCompany", "Update company");

        when(this.companyCarService.update(updateCompanyRequest))
                .thenReturn(updateCompanyRequest);

        var response = new ObjectMapper()
            .writeValueAsString(updateCompanyRequest);

        given().contentType(JSON)
                .accept(JSON)
                .body(updateCompanyRequest)
                .put("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(OK.value())
                .contentType(JSON)
                .body(is(equalTo(response)));
    }

    @Test
    void haveToReturnError400WhenSetInvalidFieldInBody_inUpdate() {
        var updateCompanyRequest = new UpdateCompanyDto(null, null,null);
        var now = Calendar.getInstance();

        var errors = Set.of(
                "Necessario informar o id",
                "Necessario informar o nome",
                "Necessario informar a descri????o"
        );

        var errorDto =  given().contentType(JSON)
                .accept(JSON)
                .body(updateCompanyRequest)
                .when()
                .put("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        assertEquals(errors.size(), errorDto.getMessages().size());
//        assertTrue(errorDto.getMessages().containsAll(errors));
        assertEquals("/api/companies", errorDto.getPath());
        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }

    @Test
    void haveToReturn400WhenSetInvalidId_inUpdate() {
        var updateCompanyRequest = new UpdateCompanyDto(0L, "MyCompany", "description");

        var errors = Set.of("Id deve ser maior que 0");
        var now = Calendar.getInstance();
        var errorDto =  given().contentType(JSON)
                .accept(JSON)
                .body(updateCompanyRequest)
                .when()
                .put("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(BAD_REQUEST.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        assertEquals(errors.size(), errorDto.getMessages().size());
        assertTrue(errorDto.getMessages().containsAll(errors));
        assertEquals("/api/companies", errorDto.getPath());
        assertEquals(BAD_REQUEST.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }

    @Test
    void haveToReturn404WhenUpdateInvalidId_inUpdate() {
        var updateCompanyRequest = new UpdateCompanyDto(1L, "MyUpdateCompany", "Update company");
        var now = Calendar.getInstance();

        doThrow(new NotFoundException(COMPANY_NOT_FOUND_ID_FORMAT, 1L))
                .when(this.companyCarService)
                .update(updateCompanyRequest);

        var errorDto = given().contentType(JSON)
                .accept(JSON)
                .body(updateCompanyRequest)
                .when()
                .put("api/companies")
                .then()
                .log()
                .ifValidationFails()
                .statusCode(NOT_FOUND.value())
                .contentType(JSON)
                .extract()
                .as(ErrorExceptionDto.class);

        assertEquals(1, errorDto.getMessages().size());
        assertTrue(errorDto.getMessages().contains(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L)));
        assertEquals("/api/companies", errorDto.getPath());
        assertEquals(NOT_FOUND.toString(), errorDto.getStatusCode());
        assertTrue(now.before(errorDto.getTimestamp()));
    }
}
