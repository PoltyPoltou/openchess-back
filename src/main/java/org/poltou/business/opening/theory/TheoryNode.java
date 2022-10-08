package org.poltou.business.opening.theory;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.poltou.business.convertors.BoardConvertor;

import chess.Color;
import chess.Situation;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TheoryNode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Convert(converter = BoardConvertor.class)
    @Column(name = "fen")
    private Situation situation;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<String, TheoryNode> children;
    @ManyToOne
    private TheoryNode parent;
    private String san;
    private String uci;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUci() {
        return uci;
    }

    public void setUci(String uci) {
        this.uci = uci;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public void setChildren(Map<String, TheoryNode> children) {
        this.children = children;
    }

    public Map<String, TheoryNode> getChildren() {
        return children;
    }

    public TheoryNode getParent() {
        return parent;
    }

    public void setParent(TheoryNode parent) {
        this.parent = parent;
    }

    public String getTurn() {
        return getSituation().color().equals(Color.White$.MODULE$) ? "white" : "black";
    }

}
