package br.com.domiciano.project.crud.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyDto {

    @JsonIgnore
    private Long id;

    private String description;

    @NotEmpty(message = "Necessario informar o nomeMarca")
    @NotNull(message = "Necessario informar o nomeMarca")
    private String name;

    @JsonIgnore
    private Calendar createdAt;

    @JsonIgnore
    private Calendar updatedAt;

    @JsonGetter("createdAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Calendar getCreatedAt() {
        return createdAt;
    }

    @JsonGetter("updatedAt")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    @JsonGetter("id")
    public Long getId() {
        return id;
    }
}
