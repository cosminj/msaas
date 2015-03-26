package com.msaas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

/**
 * @author cj
 * @since 02/01/15.
 */
@Entity
public class Screen {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @JsonFormat(shape= STRING, pattern="yyyy-MM-dd,HH:mm:ss", timezone="CET")
    @NotNull
    private Date scheduledAt;

    @JsonFormat(shape= STRING, pattern="yyyy-MM-dd,HH:mm:ss", timezone="CET")
    private Date viewedAt;

    @JsonIgnore
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "observer_id", foreignKey = @ForeignKey(name = "screen_observer_id_fkey"))
    private User observer;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "screen_cameras",
            joinColumns = {
                    @JoinColumn(name = "screen_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "camera_id", referencedColumnName = "id")
            })
    private List<Camera> cameras = new LinkedList<>();

    @SuppressWarnings("unused")
    private Screen() {}

    public Screen(User user) {
        this.observer = user;
        scheduledAt = new Date();
    }

    public Screen(Date scheduledAt, Date viewedAt, User user, List<Camera> cameras) {
        this.scheduledAt = scheduledAt;
        this.viewedAt = viewedAt;
        this.observer = user;
        this.cameras = cameras;
    }

    public Date getScheduledAt() {
        return scheduledAt;
    }

    public Screen markViewed() {
        this.viewedAt = new Date();
        return this;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Screen)) {
            return false;
        }
        Screen screen = (Screen) o;
        return java.util.Objects.equals(id, screen.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("scheduledAt", scheduledAt)
            .add("viewedAt", viewedAt)
            .add("observer", observer)
            .add("cameras", cameras)
            .toString();
    }
}
