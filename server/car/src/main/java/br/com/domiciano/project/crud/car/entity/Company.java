package br.com.domiciano.project.crud.car.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Calendar;

@Data
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company extends Base implements Serializable {

    private String name;
    private String description;
    private boolean allowed;

    public Company(Long id, Calendar dateCreated, Calendar dateUpdated, String name, boolean allowed) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.allowed = allowed;
    }

    public Company(long id, boolean allowed, String name) {
        this.setId(id);
        this.name = name;
        this.allowed = allowed;
    }
}
