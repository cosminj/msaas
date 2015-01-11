package com.msaas.model;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    public Long id;

    @NotNull
    public Date scheduledAt;

    public Date viewedAt;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "observer_id", foreignKey = @ForeignKey(name = "screen_operator_id_fkey"))
    public Observer observer;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "screen_cameras",
            joinColumns = {
                    @JoinColumn(name = "screen_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "camera_id", referencedColumnName = "id")
            })
    public List<Camera> cameras = new LinkedList<>();

    @SuppressWarnings("unused")
    private Screen() {}

    public Screen(Observer observer) {
        this.observer = observer;
        scheduledAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Screen screen = (Screen) o;
        return id.equals(screen.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("scheduledAt", scheduledAt)
                .add("viewedAt", viewedAt)
                .add("cameras", cameras)
                .toString();
    }
}
