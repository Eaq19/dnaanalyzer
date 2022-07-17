package com.meli.dnaanalyzer.repository;

import com.meli.dnaanalyzer.model.dao.PersonDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<PersonDAO, Long> {

    @Query("SELECT COUNT(u) FROM PersonDAO u WHERE u.type.id=?1")
    long getCountOfPersonByType(long type);
}
