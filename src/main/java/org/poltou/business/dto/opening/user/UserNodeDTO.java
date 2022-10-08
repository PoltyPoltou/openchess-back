package org.poltou.business.dto.opening.user;

import java.util.List;
import java.util.stream.Collectors;

import org.poltou.business.dto.opening.NodeDTO;
import org.poltou.business.opening.theory.TheoryNode;
import org.poltou.business.opening.user.UserNode;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class UserNodeDTO extends NodeDTO {

    private UserNode node;

    public UserNodeDTO(TheoryNode node) {
        super(node);
        this.node = (UserNode) node;
    }

    public List<NodeDTO> getChildren() {
        return node.getChildren().values().stream().sorted((n1, n2) -> n1.getSan().compareTo(n2.getSan()))
                .map(UserNodeDTO::new)
                .collect(Collectors.toList());
    }

    public int getWins() {
        return node.getWins();
    }

    public int getLosses() {
        return node.getLosses();
    }

    public int getDraws() {
        return node.getDraws();
    }

    public int getEncounters() {
        return node.getEncounters();
    }
}
