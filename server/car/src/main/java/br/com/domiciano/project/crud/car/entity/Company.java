package br.com.domiciano.project.crud.car.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Calendar;

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

    public Company(Long id, Calendar dateCreated, Calendar dateUpdated, String name, String description) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.description = description;
    }
}
