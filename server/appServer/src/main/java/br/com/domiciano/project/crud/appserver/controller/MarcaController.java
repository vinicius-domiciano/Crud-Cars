package br.com.domiciano.project.crud.appserver.controller;

import br.com.domiciano.project.crud.car.dto.BuscaMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.CadastroMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.ListaMarcaCarroDto;
import br.com.domiciano.project.crud.car.service.MarcaCarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping(path = "/marcas")
public class MarcaController {

    @Autowired
    private MarcaCarroService marcaCarroService;

    @GetMapping
    public ResponseEntity<Set<ListaMarcaCarroDto>> listarMarcaCarro() {
        return new ResponseEntity<>(marcaCarroService.listarMarcaCarro(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BuscaMarcaCarroDto> buscarMarcaPorId(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(marcaCarroService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping(path = "/salvar")
    public ResponseEntity<CadastroMarcaCarroDto> salvaCarro(@RequestBody @Valid CadastroMarcaCarroDto cadastroMarcaCarroDto) {
        return new ResponseEntity<>(marcaCarroService.cadastroMarcaCarro(cadastroMarcaCarroDto), HttpStatus.CREATED);
    }



}
