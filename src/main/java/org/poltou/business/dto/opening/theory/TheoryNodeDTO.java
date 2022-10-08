package org.poltou.business.dto.opening.theory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.poltou.business.opening.theory.TheoryNode;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import chess.Color;
import chess.Move;
import chess.Pos;
import chess.format.Forsyth;
import scala.Function1;
import scala.collection.JavaConverters;
import scala.jdk.CollectionConverters;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class TheoryNodeDTO {
    private TheoryNode node;

    public TheoryNodeDTO(TheoryNode node) {
        this.node = node;
    }

    public String getFen() {
        return Forsyth.$greater$greater(node.getSituation());
    }

    public Long getParentId() {
        return node.getParent() == null ? -1 : node.getParent().getId();
    }

    public String getColor() {
        return node.getSituation().color().equals(Color.White$.MODULE$) ? "white" : "black";
    }

    public List<TheoryNodeDTO> getChildren() {
        return node.getChildren().values().stream().sorted((n1, n2) -> n1.getSan().compareTo(n2.getSan()))
                .map(TheoryNodeDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return node.getId();
    }

    public String getSan() {
        return node.getSan();
    }

    public String getUci() {
        return node.getUci();
    }

    public Map<String, List<String>> getMoves() {
        Map<String, List<String>> moves = new HashMap<>();
        scala.collection.immutable.Map<Pos, scala.collection.immutable.List<Move>> movesMap = node.getSituation()
                .moves();
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
