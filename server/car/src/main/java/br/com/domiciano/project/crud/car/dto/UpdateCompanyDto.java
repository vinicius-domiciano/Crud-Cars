package br.com.domiciano.project.crud.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompanyDto {

    @Min(value = 1, message = "Id deve ser maior que 0")
    @NotNull(message = "Necessario informar o id")
    private Long id;

    @NotEmpty(message = "Necessario informar o nome")
    @NotNull(message = "Necessario informar o nome")
    private String name;

    @NotEmpty(message = "Necessario informar a descrição")
    @NotNull(message = "Necessario informar a descrição")
    private String description;

}
