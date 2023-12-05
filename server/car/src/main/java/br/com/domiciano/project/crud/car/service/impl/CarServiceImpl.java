package br.com.domiciano.project.crud.car.service.impl;

import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;
import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.entity.Car;
import br.com.domiciano.project.crud.car.exceptions.NotFoundCarException;
import br.com.domiciano.project.crud.car.repository.CarRepository;
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

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ListCarDto> listCars() {
        return mapper.map(carRepository.findAll(), new TypeToken<List<ListCarDto>>() {
        }.getType());
    }

    @Override
    public FindCarDto findCarById(Long id) {
        return mapper.map(this.carRepository.findById(id).orElseThrow(() -> new NotFoundCarException(id)), FindCarDto.class);
    }

    @Override
    public UpdateCarDto update(UpdateCarDto updateCarDto) {
        this.findCarById(updateCarDto.getId());

        return mapper.map(
                this.carRepository.save(mapper.map(updateCarDto, Car.class)),
                UpdateCarDto.class
        );
    }

}
