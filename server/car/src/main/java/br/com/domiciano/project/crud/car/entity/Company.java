package br.com.domiciano.project.crud.car.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "company")
public class Company extends Base implements Serializable {

    private String name;
    private boolean allowed;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Car> cars;

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

    public Company(Long id) {
        setId(id);
    }
}
