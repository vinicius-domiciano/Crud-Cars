package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.*;

import java.util.List;

public interface CarService {

    SavedCarDto save(SaveCarDto dto);

    List<ListCarDto> listCars();

    FindCarDto findCarById(Long id);

    UpdateCarDto update(UpdateCarDto carroDto);

    void delete(long id);
}
