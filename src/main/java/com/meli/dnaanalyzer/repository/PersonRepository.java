package com.meli.dnaanalyzer.repository;

import com.meli.dnaanalyzer.model.dao.PersonDAO;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonDAO, Long> {
}
