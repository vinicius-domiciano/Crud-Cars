package br.com.domiciano.project.crud.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindCarDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar dateUpdated;

    private BigDecimal price;
    private String name;
    private String company;
    private Integer year;

}
