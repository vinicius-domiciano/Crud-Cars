package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.CadastroMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.BuscaMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.ListaMarcaCarroDto;

import java.util.Set;

public interface MarcaCarroService {
    Set<ListaMarcaCarroDto> listarMarcaCarro();

    BuscaMarcaCarroDto buscarPorId(Long id);

    CadastroMarcaCarroDto cadastroMarcaCarro(CadastroMarcaCarroDto cadastroMarcaCarroDto);
}
