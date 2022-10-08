package org.poltou.business.dto.opening.user;

import org.poltou.business.opening.user.UserOpening;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.PUBLIC_ONLY)
public class UserOpeningDTO {
    private UserOpening opening;

    public UserOpeningDTO(UserOpening opening) {
        this.opening = opening;
    }

    public String getName() {
        return opening.getUsername();
    }

    public UserNodeDTO getStartingNode() {
        return new UserNodeDTO(opening.getStartingNode());
    }

    public Long getId() {
        return opening.getId();
    }
}
