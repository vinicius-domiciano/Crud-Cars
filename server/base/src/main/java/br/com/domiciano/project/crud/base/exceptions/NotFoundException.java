package br.com.domiciano.project.crud.base.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseRestException{

    public NotFoundException(String mensagem) {
        super(mensagem, HttpStatus.NOT_FOUND);
    }

}
