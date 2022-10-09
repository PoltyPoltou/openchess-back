package org.poltou.business.repository;

import org.poltou.business.opening.user.UserOpening;
import org.springframework.data.repository.CrudRepository;

public interface UserOpeningRepo extends CrudRepository<UserOpening, Long> {
    public UserOpening findByUsernameAndColor(String username, String color);
}
