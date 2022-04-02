package br.com.domiciano.project.crud.base.exceptions.handle;

import br.com.domiciano.project.crud.base.dto.ErrorExceptionDto;
import br.com.domiciano.project.crud.base.exceptions.BaseRestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Calendar;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var messagesError = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());

        var response = new ErrorExceptionDto(
                messagesError,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                HttpStatus.BAD_REQUEST.toString(),
                Calendar.getInstance()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseRestException.class)
    private ResponseEntity<ErrorExceptionDto> handleBaseRestException(BaseRestException e, ServletWebRequest request) {
        var response = new ErrorExceptionDto(
                Set.of(e.getMensagem()),
                request.getRequest().getRequestURI(),
                e.getHttpStatus().toString(),
                Calendar.getInstance()
        );

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

}
