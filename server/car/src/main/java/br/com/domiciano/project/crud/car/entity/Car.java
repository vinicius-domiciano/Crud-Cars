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
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Car(Long id, Calendar dateCreated, Calendar dateUpdated, String name, Integer year, BigDecimal price, Company company) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.year = year;
        this.price = price;
        this.company = company;
    }
}
