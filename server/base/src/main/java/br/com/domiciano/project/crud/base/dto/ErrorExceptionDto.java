package br.com.domiciano.project.crud.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorExceptionDto {

    private Set<String> messages;
    private String path;
    private String statusCode;
    private Calendar timestamp;

}
