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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Company(Long id, Calendar dateCreated, Calendar dateUpdated, String name, String description) {
        super(id, dateCreated, dateUpdated);
        this.name = name;
        this.description = description;
    }

    public Company(long id, String name) {
        this.setId(id);
        this.name = name;
    }
}
