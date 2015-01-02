package com.msaas.observer;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author cj
 * @since 01/01/15.
 */
@Entity
@SequenceGenerator(name = "observer_id_seq", sequenceName = "observer_id_seq")
public class Observer {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "observer_id_seq")
    public long id;

    @Column(unique = true)
    @NotEmpty
    public String name;

    @NotEmpty
    public String password;
    
    @NotNull
    public String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Observer observer = (Observer) o;

        return id == observer.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("password", password)
                .add("state", state)
                .toString();
    }
}
