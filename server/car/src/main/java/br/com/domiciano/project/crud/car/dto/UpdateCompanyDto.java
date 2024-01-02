package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyDto implements Serializable {

    @Min(value = 1, message = "Id deve ser maior que 0")
    @NotNull(message = "Necessario informar o id")
    private Long id;

    private boolean allowed;

}
