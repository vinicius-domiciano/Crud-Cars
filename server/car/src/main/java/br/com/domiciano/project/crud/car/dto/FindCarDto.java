package br.com.domiciano.project.crud.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindCarDto implements Serializable {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar dateCreated;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Calendar dateUpdated;

    private BigDecimal salePrice;
    private String name;
    private CompanyCarDto company;
    private Integer year;

    private BigDecimal fipePrice;
    private String fipeCode;
    private String referenceMonth;
    private String fuel;

}
