package br.com.domiciano.project.crud.base.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BaseRestException extends RuntimeException {

    private String mensagem;
    private HttpStatus httpStatus;

}
