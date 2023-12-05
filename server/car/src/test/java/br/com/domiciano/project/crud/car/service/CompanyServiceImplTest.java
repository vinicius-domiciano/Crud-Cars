package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.annotation.CustomInitTest;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import br.com.domiciano.project.crud.car.service.impl.CompanyCarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static br.com.domiciano.project.crud.base.helpers.ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@CustomInitTest
@DisplayName("Company services")
class CompanyServiceImplTest {

    @MockBean
    private CompanyRepository companyRepository;

    private final CompanyCarServiceImpl companyCarService;

    @Autowired
    CompanyServiceImplTest(CompanyCarServiceImpl companyCarService) {
        this.companyCarService = companyCarService;
    }

    @BeforeEach
    public void setup() {
        standaloneSetup(this.companyCarService);
    }

    @Test
    @DisplayName("List All: should return companies")
    void shouldReturnListFromDto_inListAll() {
        var companies = List.of(
                new Company(1L, Calendar.getInstance(), Calendar.getInstance(), "MyCompany", true),
                new Company(2L, Calendar.getInstance(), Calendar.getInstance(), "MyCompany2", true)
        );

        when(this.companyRepository.findAll()).thenReturn(companies);

        var response = this.companyCarService.listAll();
        verify(companyRepository, times(1)).findAll();

        assertEquals(companies.size(), response.size());

        for (ListCompanyDto dto : response) {
            var company = companies.stream().filter(c -> c.getId().equals(dto.getId()))
                    .findFirst()
                    .orElse(null);

            assertNotNull(company);
            assertEquals(dto.getName(), company.getName());
        }
    }

    @Test
    @DisplayName("List All: should return empty company")
    void shouldReturnEmptyList_inListAll() {
        when(this.companyRepository.findAll())
                .thenReturn(new ArrayList<>());

        var response = this.companyCarService.listAll();
        verify(companyRepository, times(1)).findAll();

        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Find by: should return company")
    void shouldReturnFindCardDto_inFindById() {
        var now = Calendar.getInstance();
        var company = new Company(1L, now, now, "MyCompany", true);

        when(this.companyRepository.findById(any())).thenReturn(Optional.of(company));

        var companyDto = new FindCompanyDto(1L, now, now, "MyCompany", true);
        var response = this.companyCarService.findById(1L);
        verify(companyRepository, times(1)).findById(any());
        verify(companyRepository, never()).findAll();

        assertEquals(companyDto.getId(), response.getId());
        assertEquals(companyDto.getName(), response.getName());
        assertEquals(companyDto.getDateCreated(), response.getDateCreated());
        assertEquals(0, companyDto.getDateCreated().compareTo(response.getDateCreated()));
        assertEquals(companyDto.getDateUpdated(), response.getDateUpdated());
        assertEquals(0, companyDto.getDateUpdated().compareTo(response.getDateUpdated()));
    }

    @Test
    @DisplayName("Find by: should return error when not found company")
    void shouldReturnThrowWhenSetInvalidId_inFindById() {
        when(this.companyRepository.findById(any()))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                "Should return not found exception", NotFoundException.class, () -> this.companyCarService.findById(1L));

        assertEquals(NOT_FOUND, exception.getHttpStatus());
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

    @Test
    @DisplayName("Update company: should return updated company")
    void shouldReturnUpdatedCarDto_inUpdate() {
        var now = Calendar.getInstance();
        var company = new Company(1L, Calendar.getInstance(), Calendar.getInstance(), "Hello", false);

        when(this.companyRepository.findById(any())).thenReturn(Optional.of(company));

        var companyUpdated = new Company(1L, now, now, "CompanyName", true);
        var request = new UpdateCompanyDto(1L, false);

        when(this.companyRepository.save(any())).thenReturn(companyUpdated);

        var companyDto = new UpdateCompanyDto(1L, true);
        var response = this.companyCarService.update(request);
        verify(this.companyRepository, times(1)).findById(any());
        verify(this.companyRepository, times(1)).save(any());
        verifyNoMoreInteractions(this.companyRepository);

        assertNotNull(response);
        assertNotNull(response.getId());

        assertEquals(companyDto.getId(), response.getId());
        assertEquals(companyDto.isAllowed(), response.isAllowed());
    }

    @Test
    @DisplayName("Update company: should return error when send invalid id")
    void shouldReturnThrowWhenSetInvalidId_inUpdate() {

        when(companyRepository.findById(any())).thenReturn(Optional.empty());

        var company = new UpdateCompanyDto(1L, false);
        NotFoundException exception = assertThrows(
                "Should return error",
                NotFoundException.class,
                () -> this.companyCarService.update(company)
        );

        verify(this.companyRepository, times(1)).findById(any());
        verify(this.companyRepository, never()).save(any());
        verifyNoMoreInteractions(this.companyRepository);

        assertEquals(NOT_FOUND, exception.getHttpStatus());
        assertEquals(String.format(COMPANY_NOT_FOUND_ID_FORMAT, 1L), exception.getMessage());
    }

}
