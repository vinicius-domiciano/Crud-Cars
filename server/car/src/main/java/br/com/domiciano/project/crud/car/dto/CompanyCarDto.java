package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCarDto implements Serializable {

    private Long id;
    private String name;

}
