package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.CreateCompanyDto;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;

import java.util.List;

public interface CompanyCarService {
    List<ListCompanyDto> listAll();

    FindCompanyDto findById(Long id);

    CreateCompanyDto create(CreateCompanyDto createCompanyDto);

    UpdateCompanyDto update(UpdateCompanyDto updateCompanyDto);
}
