package br.com.domiciano.project.crud.car.service.impl;

import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.exceptions.NotFoundCompanyException;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import br.com.domiciano.project.crud.car.service.CompanyCarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CompanyCarServiceImpl implements CompanyCarService {

    private final CompanyRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public CompanyCarServiceImpl(CompanyRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ListCompanyDto> listAll() {
        return mapper.map(this.repository.findAll(), new TypeToken<List<ListCompanyDto>>() {
        }.getType());
    }

    @Override
    public FindCompanyDto findById(Long id) {
        Company company = this.repository.findById(id).orElseThrow(() -> new NotFoundCompanyException(id));
        return mapper.map(company, FindCompanyDto.class);
    }

    @Override
    public UpdateCompanyDto update(UpdateCompanyDto updateCompanyDto) {
        Company company = this.repository.findById(updateCompanyDto.getId())
                .orElseThrow(() -> new NotFoundCompanyException(updateCompanyDto.getId()));

        return mapper.map(this.repository.save(company), UpdateCompanyDto.class);
    }

}
