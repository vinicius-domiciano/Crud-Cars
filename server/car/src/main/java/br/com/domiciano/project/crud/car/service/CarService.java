package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.CreateCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;

import java.util.List;

public interface CarService {

    List<ListCarDto> listCars();
    FindCarDto findCarById(Long id);
    CreateCarDto save(CreateCarDto carroDto);
    UpdateCarDto update(UpdateCarDto carroDto);

    void delete(Long id);
}
