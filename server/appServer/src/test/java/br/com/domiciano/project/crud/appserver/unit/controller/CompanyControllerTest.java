package br.com.domiciano.project.crud.appserver.unit.controller;

import br.com.domiciano.project.crud.appserver.controller.CompanyController;
import br.com.domiciano.project.crud.appserver.unit.annotation.CustomInitTest;
import br.com.domiciano.project.crud.base.exceptions.handle.ExceptionHandle;
import br.com.domiciano.project.crud.car.service.CompanyCarService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

@WebMvcTest
@CustomInitTest
class CompanyControllerTest {

    private final CompanyController companyController;
    private final ExceptionHandle exceptionHandle;

    @MockBean
    private CompanyCarService companyCarService;

    @Autowired
    CompanyControllerTest(CompanyController companyController, ExceptionHandle exceptionHandle) {
        this.companyController = companyController;
        this.exceptionHandle = exceptionHandle;
    }

    @BeforeEach
    public void setup() {
        standaloneSetup(this.companyController, this.exceptionHandle);
    }


}
