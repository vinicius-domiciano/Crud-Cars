package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCarDto implements Serializable {

    @NotNull(message = "O nome deve ser preenchido")
    @NotEmpty(message = "O nome nao deve estar vazio")
    private String name;

    @NotNull(message = "O preco de venda deve ser preenchido")
    @Min(value = 1000, message = "O preco de venda deve ser maior que 999")
    private BigDecimal salePrice;

    @NotNull(message = "O preco da tabela fipe deve ser preenchido")
    @Min(value = 1000, message = "O preco da tabela de venda deve ser maior que 999")
    private BigDecimal fipePrice;

    @NotNull(message = "O id da empresa deve ser preenchido")
    private Long companyId;

    @Min(value = 1886, message = "O ano do carro deve ser maior que 1885")
    private int year;

    @NotNull(message = "O codigo da tabela fipe deve ser preenchido")
    private String fipeCode;

    @NotNull(message = "O mes de referencia deve ser informado")
    private String referenceMonth;

    @NotNull(message = "A sigla do combustivel deve ser informada")
    private String fuelAcronym;

    @NotNull(message = "O combustivel deve ser informado")
    private String fuel;

}
