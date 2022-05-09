package br.com.domiciano.project.crud.car.service.impl;

import br.com.domiciano.project.crud.base.helpers.ExceptionsIndices;
import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.CreateCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.service.CompanyCarService;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyCarServiceImpl implements CompanyCarService {

    private final CompanyRepository repository;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public CompanyCarServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ListCompanyDto> listAll() {
        return MAPPER.map(
                this.repository.findAll(),
                new TypeToken<List<ListCompanyDto>>() {}.getType()
        );
    }

    @Override
    public FindCompanyDto findById(Long id) {
        return MAPPER.map(
                this.repository.findById(id)
                    .orElseThrow(() -> new NotFoundException(ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT, id)),
                FindCompanyDto.class
        );
    }

    @Override
    public CreateCompanyDto create(CreateCompanyDto createCompanyDto) {
        return MAPPER.map(
                this.repository.save(MAPPER.map(createCompanyDto, Company.class)),
                CreateCompanyDto.class
        );
    }

}
