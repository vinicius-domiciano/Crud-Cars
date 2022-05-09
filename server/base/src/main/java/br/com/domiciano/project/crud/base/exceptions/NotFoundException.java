package br.com.domiciano.project.crud.base.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseRestException{

    public NotFoundException(String format, Object value) {
        super(String.format(format, value), HttpStatus.NOT_FOUND);
    }

}
