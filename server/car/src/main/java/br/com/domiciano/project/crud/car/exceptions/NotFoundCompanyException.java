package br.com.domiciano.project.crud.car.exceptions;

import br.com.domiciano.project.crud.base.exceptions.NotFoundException;
import br.com.domiciano.project.crud.base.helpers.ExceptionsIndices;

public class NotFoundCompanyException extends NotFoundException {
    public NotFoundCompanyException(Object value) {
        super(ExceptionsIndices.COMPANY_NOT_FOUND_ID_FORMAT, value);
    }
}
