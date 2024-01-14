package br.com.domiciano.project.crud.car.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Data
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Calendar dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "date_updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Calendar dateUpdated;

    public Base(Long id, Calendar dateCreated, Calendar dateUpdated) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

}
