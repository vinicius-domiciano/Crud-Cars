package br.com.domiciano.project.crud.car.exceptions;

import br.com.domiciano.project.crud.base.exceptions.BadRequestException;
import br.com.domiciano.project.crud.base.helpers.ExceptionsIndices;

public class NeedToSetCompanyIdException extends BadRequestException {
    public NeedToSetCompanyIdException() {
        super(ExceptionsIndices.NEED_TO_SET_COMPANY_ID);
    }
}
