package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.CreateCompanyDto;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;

import java.util.List;

public interface CompanyCarService {
    List<ListCompanyDto> listarMarcaCarro();

    FindCompanyDto buscarPorId(Long id);

    CreateCompanyDto cadastroMarcaCarro(CreateCompanyDto createCompanyDto);
}
