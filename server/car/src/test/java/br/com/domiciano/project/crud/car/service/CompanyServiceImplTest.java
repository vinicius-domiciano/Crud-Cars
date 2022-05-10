package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.annotation.CustomInitTest;
import br.com.domiciano.project.crud.car.dto.CreateCompanyDto;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import br.com.domiciano.project.crud.car.service.impl.CompanyCarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
@CustomInitTest
class CompanyServiceImplTest {

    @MockBean
    private CompanyRepository companyRepository;

    private final CompanyCarServiceImpl companyCarService;

    @Autowired
    public CompanyServiceImplTest(CompanyCarServiceImpl companyCarService) {
        this.companyCarService = companyCarService;
    }

    @BeforeEach
    public void setup() {
        standaloneSetup(this.companyCarService);
    }

    @Test
    void haveToReturnListFromDto_inListAll() {
        var companies = List.of(
                new Company(1L, Calendar.getInstance(), Calendar.getInstance(), "MyCompany", "Company test"),
                new Company(2L, Calendar.getInstance(), Calendar.getInstance(), "MyCompany2", "Company test 2")
        );

        when(this.companyRepository.findAll())
                .thenReturn(companies);

        var companiesDto = List.of(
            new ListCompanyDto(1L, "MyCompany"),
            new ListCompanyDto(2L, "MyCompany2")
        );

        var response = this.companyCarService.listAll();

        assertEquals(companiesDto.size(), response.size());
        assertArrayEquals(companiesDto.toArray(new ListCompanyDto[]{}), response.toArray(new ListCompanyDto[]{}));
        assertTrue(getIds(companiesDto).containsAll(getIds(response)));
    }

    public static List<Long> getIds(List<ListCompanyDto> companiesDto) {
        return companiesDto.stream()
                .map(ListCompanyDto::getId)
                .collect(Collectors.toList());
    }

    @Test
    void haveToReturnEmptyList_inListAll() {
        when(this.companyRepository.findAll())
                .thenReturn(new ArrayList<>());

        var response = this.companyCarService.listAll();

        assertTrue(response.isEmpty());
    }

    @Test
    void haveToReturnFindCardDto_inFindById() {
        var now = Calendar.getInstance();
        var company = new Company(1L, now, now, "MyCompany", "Company test");

        when(this.companyRepository.findById(1L))
                .thenReturn(Optional.of(company));

        var companyDto = new FindCompanyDto(1L, now, now, "Company test", "MyCompany");
        var response = this.companyCarService.findById(1L);

        assertEquals(companyDto.getId(), response.getId());
        assertEquals(companyDto.getName(), response.getName());
        assertEquals(companyDto.getDescription(), response.getDescription());
        assertEquals(companyDto.getDateCreated(), response.getDateCreated());
        assertEquals(0, companyDto.getDateCreated().compareTo(response.getDateCreated()));
        assertEquals(companyDto.getDateUpdated(), response.getDateUpdated());
        assertEquals(0, companyDto.getDateUpdated().compareTo(response.getDateUpdated()));
    }

    @Test
    void haveToReturnThrowWhenSetInvalidId_inFindById() {
        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            this.companyCarService.findById(1L);
        } catch(NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(NOT_FOUND.value(), statusCode);
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), message);
        assertTrue(isError);
    }

    @Test
    void haveToReturnCreatedCarDto_inCreate() {
        var now = Calendar.getInstance();
        var company = new Company(null, null, null, "CompanyName", "HelloWorld");
        var companySaved = new Company(1L, now, now, "CompanyName", "HelloWorld");

        when(this.companyRepository.save(company))
                .thenReturn(companySaved);

        var request = new CreateCompanyDto(null, "HelloWorld", "CompanyName", null, null);
        var companyDto = new CreateCompanyDto(1L, "HelloWorld", "CompanyName", now, now);
        var response = this.companyCarService.create(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getDescription());
        assertNotNull(response.getDateCreated());
        assertNotNull(response.getDateUpdated());

        assertEquals(companyDto.getId(), response.getId());
        assertEquals(companyDto.getName(), response.getName());
        assertEquals(companyDto.getDescription(), response.getDescription());
        assertEquals(companyDto.getDateCreated(), response.getDateCreated());
        assertEquals(companyDto.getDateUpdated(), response.getDateUpdated());

        assertEquals(0, companyDto.getDateCreated().compareTo(response.getDateCreated()));
        assertEquals(0, companyDto.getDateUpdated().compareTo(response.getDateUpdated()));
    }

    @Test
    void haveToReturnUpdatedCarDto_inUpdate() {
        var now = Calendar.getInstance();
        var company = new Company(1L, Calendar.getInstance(), Calendar.getInstance(), "Hello", "HelloWorld");

        when(this.companyRepository.findById(1L))
                .thenReturn(Optional.of(company));

        company = new Company(1L, null, null, "CompanyName", "HelloWorld");
        var companyUpdated = new Company(1L, now, now, "CompanyName", "HelloWorld");
        var request = new UpdateCompanyDto(1L, "CompanyName", "HelloWorld", null);

        when(this.companyRepository.save(company))
                .thenReturn(companyUpdated);

        var companyDto = new UpdateCompanyDto(1L, "CompanyName", "HelloWorld", now);
        var response = this.companyCarService.update(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getName());
        assertNotNull(response.getDescription());
        assertNotNull(response.getDateUpdated());

        assertEquals(companyDto.getId(), response.getId());
        assertEquals(companyDto.getName(), response.getName());
        assertEquals(companyDto.getDescription(), response.getDescription());
        assertEquals(companyDto.getDateUpdated(), response.getDateUpdated());

        assertEquals(0, companyDto.getDateUpdated().compareTo(response.getDateUpdated()));
    }

    @Test
    void haveToReturnThrowWhenSetInvalidId_inUpdate() {
        var isError = Boolean.FALSE;
        var message = "";
        var statusCode = 0;

        try {
            var companyDto = new UpdateCompanyDto(2L, "MyCompany", "HelloWorld", null);
            this.companyCarService.update(companyDto);
        } catch(NotFoundException e) {
            isError = Boolean.TRUE;
            message = e.getMessage();
            statusCode = e.getHttpStatus().value();
        }

        assertEquals(NOT_FOUND.value(), statusCode);
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 2L), message);
        assertTrue(isError);
    }

}
