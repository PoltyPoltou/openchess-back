package org.poltou.business.dto.opening.user;

import org.poltou.business.dto.opening.theory.TheoryNodeDTO;
import org.poltou.business.opening.user.UserNode;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class UserNodeDTO extends TheoryNodeDTO {

    private UserNode node;

    public UserNodeDTO(UserNode node) {
        super(node);
        this.node = node;
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
