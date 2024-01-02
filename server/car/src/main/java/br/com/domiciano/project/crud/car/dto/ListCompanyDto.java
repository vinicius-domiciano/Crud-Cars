package br.com.domiciano.project.crud.car.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ListCompanyDto implements Serializable  {

    private Long id;
    private String name;

}
