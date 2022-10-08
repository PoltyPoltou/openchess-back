package org.poltou.business.dto.opening.theory;

import org.poltou.business.opening.theory.TheoryOpening;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class TheoryOpeningDTO {

    private TheoryOpening opening;

    public TheoryOpeningDTO(TheoryOpening opening) {
        this.opening = opening;
    }

    public String getName() {
        return opening.getName();
    }

    public TheoryNodeDTO getStartingNode() {
        return new TheoryNodeDTO(opening.getStartingNode());
    }

    public Long getId() {
        return opening.getId();
    }
}
