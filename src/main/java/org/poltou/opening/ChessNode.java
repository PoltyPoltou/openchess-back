package org.poltou.opening;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.poltou.convertors.BoardConvertor;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import chess.format.Forsyth;
import scala.Function1;
import scala.collection.JavaConverters;
import scala.jdk.CollectionConverters;
import chess.Color;
import chess.Move;
import chess.Pos;
import chess.Situation;

@Entity
public class ChessNode {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = BoardConvertor.class)
    @Column(name = "fen")
    @JsonIgnore
    private Situation situation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChessNode> children;

    @ManyToOne
    @JsonIgnore
    private ChessNode parent;

    private String san = "root";
    private String uci = "";

    public ChessNode() {
        this.children = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public String getUci() {
        return uci;
    }

    public void setUci(String uci) {
        this.uci = uci;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public String getSan() {
        return san;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Collection<ChessNode> getChildren() {
        return children;
    }

    public void setChildren(List<ChessNode> children) {
        this.children = children;
    }

    public ChessNode getParent() {
        return parent;
    }

    public void setParent(ChessNode parent) {
        this.parent = parent;
    }

    @JsonGetter(value = "fen")
    public String getFen() {
        return Forsyth.$greater$greater(situation);
    }

    @JsonGetter(value = "parentId")
    public Long getParentId() {
        return this.parent == null ? -1 : this.parent.getId();
    }

    @JsonGetter(value = "color")
    public String getTurn() {
        return situation.color().equals(Color.White$.MODULE$) ? "white" : "black";
    }

    @JsonGetter(value = "moves")
    public Map<String, List<String>> getAvailableMoves() {
        Map<String, List<String>> moves = new HashMap<>();
        scala.collection.immutable.Map<Pos, scala.collection.immutable.List<Move>> movesMap = situation.moves();
        CollectionConverters.MapHasAsJava(movesMap).asJava().forEach(
                (Pos p, scala.collection.immutable.List<Move> mvLst) -> {
                    moves.put(p.key(), JavaConverters.asJava(
                            mvLst.map(
                                    new Function1<Move, String>() {
                                        @Override
                                        public String apply(Move mv) {
                                            return mv.toUci().uci().substring(2, 4);
                                        }

                                    })));
                });
        return moves;
    }
}
