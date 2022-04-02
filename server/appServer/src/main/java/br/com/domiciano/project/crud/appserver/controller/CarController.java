package br.com.domiciano.project.crud.appserver.controller;

import br.com.domiciano.project.crud.car.dto.UpdateCarDto;
import br.com.domiciano.project.crud.car.dto.SaveCarDto;
import br.com.domiciano.project.crud.car.dto.FindCarDto;
import br.com.domiciano.project.crud.car.dto.ListCarDto;
import br.com.domiciano.project.crud.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<ListCarDto>> listCars() {
        return new ResponseEntity<>(this.carService.listCars(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FindCarDto> findCarById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(this.carService.findCarById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaveCarDto> save(@Valid @RequestBody SaveCarDto saveCarDto) {
        return new ResponseEntity<>(this.carService.save(saveCarDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateCarDto> update(@Valid @RequestBody UpdateCarDto updateCarDto) {
        return new ResponseEntity<>(this.carService.update(updateCarDto), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        this.carService.delete(id);
    }

}
