package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.SaveCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;

import java.util.List;

public interface CarService {

    List<ListCarDto> listCars();
    FindCarDto findCarById(Long id);
    SaveCarDto save(SaveCarDto carroDto);
    UpdateCarDto update(UpdateCarDto carroDto);

    void delete(Long id);
}
