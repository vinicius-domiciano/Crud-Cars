package br.com.domiciano.project.crud.car.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
public class Company extends Base implements Serializable {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
    private List<Car> cars;

    public Company(Long id, Calendar dateCreated, Calendar dateUpdated, String name, String description) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.description = description;
    }

    public Company(String name) {
        this.name = name;
    }
}
