package org.poltou.repository;

import org.poltou.opening.Opening;
import org.springframework.data.repository.CrudRepository;

public interface OpeningRepo extends CrudRepository<Opening, Long> {
    public Opening findByName(String name);
}
