package org.poltou.business.opening.user;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class UserOpening {

    public static UserOpening of(String username, String color, UserNode startingNode) {
        UserOpening userOpening = new UserOpening();
        userOpening.setUsername(username);
        userOpening.setColor(color);
        userOpening.setStartingNode(startingNode);
        return userOpening;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String color;

    @OneToOne(cascade = CascadeType.ALL)
    private UserNode startingNode;
    @UpdateTimestamp
    private Instant updatedate;

    public Instant getUpdatedate() {
        return updatedate;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }

    public UserNode getStartingNode() {
        return startingNode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStartingNode(UserNode startingNode) {
        this.startingNode = startingNode;
    }

}
