package com.msaas.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

/**
 * @author cj
 * @since 31/12/14.
 */
@Entity
public class Camera {

    @Id
    @GeneratedValue(strategy = AUTO)
    public long id;

    @NotEmpty
    public String name;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "camera_customer_id_fkey"))
    public Customer customer;

    @Enumerated(STRING)
    public CameraState state;

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
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("customer", customer)
                .add("state", state)
                .add("tags", tags)
                .add("nextViewingAt", nextViewingAt)
                .add("startupDelay", startupDelay)
                .add("url", url)
                .toString();
    }
}
