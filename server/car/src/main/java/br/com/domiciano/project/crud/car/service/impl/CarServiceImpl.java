package br.com.domiciano.project.crud.car.service.impl;

import br.com.domiciano.project.crud.base.exceptions.BadRequestException;
import br.com.domiciano.project.crud.car.dto.*;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.entity.Company;
import br.com.domiciano.project.crud.car.exceptions.NeedToSetCompanyIdException;
import br.com.domiciano.project.crud.car.exceptions.NotFoundCarException;
import br.com.domiciano.project.crud.car.repository.CarRepository;
import br.com.domiciano.project.crud.car.repository.CompanyRepository;
import br.com.domiciano.project.crud.car.service.CarService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CarServiceImpl implements CarService {

    private final CompanyRepository companyRepository;
    private final CarRepository carRepository;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CompanyRepository companyRepository, CarRepository carRepository, ModelMapper mapper) {
        this.companyRepository = companyRepository;
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public SavedCarDto save(SaveCarDto dto) {
        if (dto.getCompanyId() == null || dto.getCompanyId() < 1) {
            throw new NeedToSetCompanyIdException();
        }

        final Car car = Car.builder()
                .company(findCompanyById(dto.getCompanyId()))
                .name(dto.getName())
                .year(dto.getYear())
                .salePrice(dto.getSalePrice())
                .fipePrice(dto.getFipePrice())
                .fipeCode(dto.getFipeCode())
                .referenceMonth(dto.getReferenceMonth())
                .fuelAcronym(dto.getFuelAcronym())
                .fuel(dto.getFuel())
                .build();

        return mapper.map(carRepository.save(car), SavedCarDto.class);
    }

    @Override
    public List<ListCarDto> listCars() {
        return mapper.map(carRepository.findAll(), new TypeToken<List<ListCarDto>>() {
        }.getType());
    }

    @Override
    public FindCarDto findCarById(Long id) {
        return mapper.map(findById(id), FindCarDto.class);
    }

    @Override
    public UpdateCarDto update(UpdateCarDto updateCarDto) {
        if (updateCarDto.getCompanyId() == null || updateCarDto.getCompanyId() < 1) {
            throw new NeedToSetCompanyIdException();
        }

        final Car car = findById(updateCarDto.getId());
        car.setName(updateCarDto.getName());
        car.setYear(updateCarDto.getYear());
        car.setSalePrice(updateCarDto.getSalePrice());
        car.setCompany(findCompanyById(updateCarDto.getCompanyId()));
        car.setFipePrice(updateCarDto.getFipePrice());
        car.setFipeCode(updateCarDto.getFipeCode());
        car.setReferenceMonth(updateCarDto.getReferenceMonth());
        car.setFuelAcronym(updateCarDto.getFuelAcronym());
        car.setFuel(updateCarDto.getFuel());

        updateCarDto = mapper.map(this.carRepository.save(car), UpdateCarDto.class);
        updateCarDto.setCompanyId(car.getCompany().getId());

        return updateCarDto;
    }

    @Override
    public void delete(long id) {
        carRepository.delete(findById(id));
    }

    private Car findById(Long id) {
        return this.carRepository.findById(id).orElseThrow(() -> new NotFoundCarException(id));
    }

    private Company findCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(String.format("Company wasn't fount to id: %s", id)));
    }

}
