package br.com.domiciano.project.crud.car.service;

import br.com.domiciano.project.crud.car.dto.CadastroMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.BuscaMarcaCarroDto;
import br.com.domiciano.project.crud.car.dto.ListCompanyDto;

import java.util.List;

public interface CompanyCarService {
    List<ListCompanyDto> listarMarcaCarro();

    BuscaMarcaCarroDto buscarPorId(Long id);

    CadastroMarcaCarroDto cadastroMarcaCarro(CadastroMarcaCarroDto cadastroMarcaCarroDto);
}
