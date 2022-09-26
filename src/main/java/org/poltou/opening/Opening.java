package org.poltou.opening;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Opening {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @OneToOne
    private ChessNode startingNode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChessNode getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(ChessNode startingNode) {
        this.startingNode = startingNode;
    }

    public Long getId() {
        return id;
    }

    @JsonProperty
    public String getColor() {
        return startingNode.getTurn();
    }

}
