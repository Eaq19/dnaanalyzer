package com.meli.dnaanalyzer.model.dao;

import com.meli.dnaanalyzer.repository.PersonRepository;
import com.meli.dnaanalyzer.util.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("dev")
public class PersonDAOTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void save() {
        // given
        PersonDAO personDAO = createPersonDAO();
        entityManager.persist(personDAO);
        entityManager.flush();
        // when
        Optional<PersonDAO> person = personRepository.findById(1L);
        // then
        assertThat(person.get().getDna()).isEqualTo("{\"ATGCGAAT\"}");
        assertThat(person.get().getType().getId()).isEqualTo(2L);
        assertThat(person.get().getType().getName()).isEqualTo("Mutante");
    }

    private PersonDAO createPersonDAO() {
        PersonDAO personDAO = new PersonDAO();
        personDAO.setType(TypeDAO.builder().id(Type.MUTANT.getId()).name("Mutante").build());
        personDAO.setDna("{\"ATGCGAAT\"}");
        return personDAO;
    }

}