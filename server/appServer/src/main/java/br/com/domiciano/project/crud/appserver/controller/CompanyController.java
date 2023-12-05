package br.com.domiciano.project.crud.appserver.controller;

import br.com.domiciano.project.crud.car.dto.FindCompanyDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;
import br.com.domiciano.project.crud.car.dto.UpdateCompanyDto;
import br.com.domiciano.project.crud.car.service.CompanyCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/companies", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CompanyController {

    @Autowired
    private CompanyCarService companyCarService;

    @GetMapping
    public ResponseEntity<List<ListCompanyDto>> listAllCompany() {
        return new ResponseEntity<>(companyCarService.listAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FindCompanyDto> findCompanyById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(companyCarService.findById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UpdateCompanyDto> update(@RequestBody @Valid UpdateCompanyDto updateCompanyDto) {
        return new ResponseEntity<>(companyCarService.update(updateCompanyDto), HttpStatus.OK);
    }

}
