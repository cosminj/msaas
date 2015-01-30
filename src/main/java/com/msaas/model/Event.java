package com.msaas.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.base.MoreObjects;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = AUTO)
    public long id;

    @NotNull
    @NotEmpty
    public EventType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "camera_id", foreignKey = @ForeignKey(name = "event_camera_id_fKey"))
    public Camera camera;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "screen_id", foreignKey = @ForeignKey(name = "event_screen_id_fKey"))
    public Screen screen;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("type", type)
            .add("camera", camera)
            .add("screen", screen)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        return id == event.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
