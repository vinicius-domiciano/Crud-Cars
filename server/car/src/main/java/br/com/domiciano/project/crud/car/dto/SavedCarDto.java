package br.com.domiciano.project.crud.car.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@NoArgsConstructor
public class SavedCarDto implements Serializable {

    private Long id;
    private String name;
    private int year;
    private BigDecimal salePrice;
    private BigDecimal fipePrice;
    private String fuel;
    private Calendar dateUpdated;
    private Calendar dateCreated;

}
