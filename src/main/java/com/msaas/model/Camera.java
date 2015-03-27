package com.msaas.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author cj
 * @since 31/12/14.
 */
@Entity
public class Camera {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "camera_customer_id_fkey"))
    private User customer;

    @Enumerated(STRING)
    private CameraState state;

    @NotNull
    @Column(nullable = false)
    private String tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:mm:ss", timezone = "CET")
    private Date nextViewingAt;

    private Integer startupDelay;

    private String url;

    @JsonBackReference()
    @ManyToMany(mappedBy = "cameras", fetch = LAZY, cascade = REMOVE)
    private Set<Screen> screens = new HashSet<>();

    public Camera scheduleMe(Date at) {
        this.state = CameraState.SCHEDULED;
        this.nextViewingAt = at;
        return this;
    }

    public Camera makeWaiting() {
        this.state = CameraState.WAITING;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Camera)) {
            return false;
        }
        Camera camera = (Camera) o;
        return Objects.equals(id, camera.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}