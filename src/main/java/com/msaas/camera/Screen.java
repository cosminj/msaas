package com.msaas.camera;

import com.google.common.base.Objects;
import com.msaas.observer.Observer;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author cj
 * @since 02/01/15.
 */
@Entity
@SequenceGenerator(name = "screen_id_seq", sequenceName = "screen_id_seq")
public class Screen {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "screen_id_seq")
    public Long id;

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
    public List<Camera> cameras = new LinkedList<Camera>();

    public Screen() {
    }

    public Screen(Observer observer) {
        this.observer = observer;
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
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("scheduledAt", scheduledAt)
                .add("viewedAt", viewedAt)
                .add("observer", observer)
                .toString();
    }
}
