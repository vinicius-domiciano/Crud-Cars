package br.com.domiciano.project.crud.car.exceptions;

import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.base.helpers.ExceptionsIndices;

public class NotFoundCarException extends NotFoundException {

    public NotFoundCarException(Object value) {
        super(ExceptionsIndices.CAR_NOT_FOUND_ID_FORMAT, value);
    }

}
