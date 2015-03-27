package com.msaas.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "msaas_user")
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    private String password;

    @OneToMany(mappedBy = "observer", fetch = LAZY)
    @OrderBy("scheduledAt")
    private List<Screen> screens = new LinkedList<>();

    public Screen getLastScreen() {
        Screen lastScreen;
        if (screens.isEmpty()) {
            lastScreen = new Screen(this);
            screens.add(lastScreen);
        } else {
            lastScreen = screens.get(screens.size() - 1);
        }
        return lastScreen;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}