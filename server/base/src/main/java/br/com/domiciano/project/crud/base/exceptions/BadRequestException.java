package br.com.domiciano.project.crud.base.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseRestException {

    public BadRequestException(String mensagem) {
        super(mensagem, HttpStatus.BAD_REQUEST);
    }

}
