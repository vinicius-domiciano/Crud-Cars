package br.com.domiciano.project.crud.appserver.controller;

import br.com.domiciano.project.crud.car.dto.*;
import br.com.domiciano.project.crud.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<ListCarDto>> listCars() {
        return new ResponseEntity<>(this.carService.listCars(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FindCarDto> findCarById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.carService.findCarById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SavedCarDto> saveCar(@Valid @RequestBody SaveCarDto saveCarDto) {
        return new ResponseEntity<>(carService.save(saveCarDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateCarDto> update(@Valid @RequestBody UpdateCarDto updateCarDto) {
        return new ResponseEntity<>(this.carService.update(updateCarDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        this.carService.delete(id);
    }

}
