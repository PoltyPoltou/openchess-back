package org.poltou.business.dto.opening;

import java.util.List;
import java.util.stream.Collectors;

import org.poltou.business.opening.theory.TheoryNode;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import chess.Color;
import chess.format.Forsyth;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class NodeDTO {
    private TheoryNode node;

    protected TheoryNode getNode() {
        return node;
    }

    public NodeDTO(TheoryNode node) {
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

    public List<NodeDTO> getChildren() {
        return node.getChildren().values().stream().sorted((n1, n2) -> n1.getSan().compareTo(n2.getSan()))
                .map(NodeDTO::new)
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

}
