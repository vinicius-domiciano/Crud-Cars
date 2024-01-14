package br.com.domiciano.project.crud.car.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Table(name = "car", indexes = {
        @Index(name = "fk_index", columnList = "company_id"),
        @Index(name = "index_01", columnList = "name,year")
})
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Car extends Base implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "price", nullable = false)
    private BigDecimal salePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column(name = "fipe_price")
    private BigDecimal fipePrice;

    @Column(name = "fipe_code")
    private String fipeCode;

    @Column(name = "reference_name")
    private String referenceMonth;

    @Column(name = "fuel_acronym")
    private String fuelAcronym;

    @Column(name = "fuel")
    private String fuel;

    public Car(Long id, Calendar dateCreated, Calendar dateUpdated, String name, Integer year, BigDecimal salePrice, Company company) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.year = year;
        this.salePrice = salePrice;
        this.company = company;
    }

    public Car(Long id, Calendar dateCreated, Calendar dateUpdated, String name, Integer year, BigDecimal salePrice, Company company, BigDecimal fipePrice, String fipeCode, String referenceMonth, String fuelAcronym, String fuel) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.year = year;
        this.salePrice = salePrice;
        this.company = company;
        this.fipePrice = fipePrice;
        this.fipeCode = fipeCode;
        this.referenceMonth = referenceMonth;
        this.fuelAcronym = fuelAcronym;
        this.fuel = fuel;
    }
}
