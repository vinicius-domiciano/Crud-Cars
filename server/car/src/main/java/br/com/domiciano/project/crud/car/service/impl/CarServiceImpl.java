package br.com.domiciano.project.crud.car.service.impl;

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

    @Autowired
    private CarRepository repository;

    private static final ModelMapper MAPPER = new ModelMapper();

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
                        .orElseThrow(() -> new NotFoundException(String.format("Car not found for id[%s]", id))),
                FindCarDto.class
        );
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public CreateCarDto save(CreateCarDto carroDto) {
        return MAPPER.map(
                this.repository.save(MAPPER.map(carroDto, Car.class)),
                CreateCarDto.class
        );
    }

    @Override
    public UpdateCarDto update(UpdateCarDto carroDto) {
        this.findCarById(carroDto.getId());

        return MAPPER.map(
                this.repository.save(MAPPER.map(carroDto, Car.class)),
                UpdateCarDto.class
        );
    }

    @Override
    public void delete(Long id) {
        this.findCarById(id);

        this.repository.deleteById(id);
    }
}
