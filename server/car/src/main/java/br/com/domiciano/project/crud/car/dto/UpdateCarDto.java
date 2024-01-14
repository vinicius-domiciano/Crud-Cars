package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarDto implements Serializable {

    @Min(value = 1, message = "Id deve ser maior que 0")
    @NotNull(message = "Necessario informar o id")
    private Long id;

    private boolean allowed;

    private String name;

    private Integer year;

    private BigDecimal salePrice;

    private Long companyId;

    private BigDecimal fipePrice;

    private String fipeCode;

    private String referenceMonth;

    private String fuelAcronym;

    private String fuel;

}
