package com.msaas.camera;

import com.google.common.base.Objects;
import com.msaas.customer.Customer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author cj 
 * @since 31/12/14.
 */
@Entity
@SequenceGenerator(name = "camera_id_seq", sequenceName = "camera_id_seq")
public class Camera {
    
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "camera_id_seq")
    public long id;

    @NotEmpty
    public String name;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "camera_customer_id_fkey"))
    public Customer customer;
    
    public String state;
    
    public String tags;
    
    public Date nextViewingAt;
    
    public Integer startupDelay;
    
    public String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera camera = (Camera) o;

        return id == camera.id;
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
                .add("customer", customer)
                .add("tags", tags)
                .add("state", state)
                .add("nextViewingAt", nextViewingAt)
                .add("startupDelay", startupDelay)
                .add("url", url)
                .toString();
    }
}
