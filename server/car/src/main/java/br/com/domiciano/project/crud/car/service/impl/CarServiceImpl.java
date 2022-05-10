package br.com.domiciano.project.crud.car.service.impl;

import br.com.domiciano.project.crud.base.helpers.ExceptionsIndices;
import br.com.domiciano.project.crud.car.service.CarService;
import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.CreateCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository repository;

    private static final ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public CarServiceImpl(CarRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ListCarDto> listCars() {
        return MAPPER.map(
                repository.findAll(),
                new TypeToken<List<ListCarDto>>() {}.getType()
        );
    }

    @Override
    public FindCarDto findCarById(Long id) {
        return MAPPER.map(
                this.repository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT, id)),
                FindCarDto.class
        );
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public CreateCarDto save(CreateCarDto createCarDto) {
        return MAPPER.map(
                this.repository.save(MAPPER.map(createCarDto, Car.class)),
                CreateCarDto.class
        );
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public UpdateCarDto update(UpdateCarDto updateCarDto) {
        this.findCarById(updateCarDto.getId());

        return MAPPER.map(
                this.repository.save(MAPPER.map(updateCarDto, Car.class)),
                UpdateCarDto.class
        );
    }

    @Override
    public void delete(Long id) {
        this.findCarById(id);

        this.repository.deleteById(id);
    }
}
