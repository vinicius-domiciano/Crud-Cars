package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCarDto {

    private String name;
    private BigDecimal salePrice;
    private BigDecimal fipePrice;
    private Long companyId;
    private int year;
    private String fipeCode;
    private String referenceMonth;
    private String fuelAcronym;
    private String fuel;

}
