package br.com.domiciano.project.crud.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveCarDto {

    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Necessario informar o nome")
    @NotNull(message = "Necessario informar o nome")
    private String name;

    @NotEmpty(message = "Necessario informar a marca")
    @NotNull(message = "Necessario informar a marca")
    private String company;

    @Min(value = 1900, message = "Necessario informar o ano maior que 1900")
    @NotNull(message = "Necessario informar o ano")
    private Integer year;

    @Min(value = 1, message = "Necessario informar o preco maior que 0")
    @NotNull(message = "Necessario informar o preco")
    private BigDecimal price;

    @JsonIgnore
    private Calendar dateCreated;

    @JsonIgnore
    private Calendar dateUpdated;

    @JsonGetter("dateCreated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Calendar getDateCreated() {
        return dateCreated;
    }

    @JsonGetter("dateUpdated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Calendar getDateUpdated() {
        return dateUpdated;
    }

    @JsonGetter("id")
    public Long getId() {
        return id;
    }
}
