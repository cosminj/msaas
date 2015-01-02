package com.msaas.customer;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author cj
 * @since 28/12/14
 */
@Entity
@SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq")
public class Customer {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "customer_id_seq")
    public long id;

    @Column(unique = true)
    @NotEmpty
    public String name;

    @NotEmpty
    public String password;

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("password", password)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id == customer.id &&
                name.equals(customer.name) &&
                password.equals(customer.password);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}