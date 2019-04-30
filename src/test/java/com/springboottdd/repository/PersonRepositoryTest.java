package com.springboottdd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboottdd.models.Person;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository sut;

	@Test
	public void must_find_person_by_cpf() throws Exception {
		Optional<Person> optional = sut.findByCpf("38767897100");

		assertThat(optional.isPresent()).isTrue();

		Person person = optional.get();
		assertThat(person.getCode()).isEqualTo(3L);
		assertThat(person.getName()).isEqualTo("Cauê");
		assertThat(person.getCpf()).isEqualTo("38767897100");

	}

	@Test
	public void must_not_find_person_wiht_inexistent_cpf() throws Exception {
		Optional<Person> optional = sut.findByCpf("18767897100");

		assertThat(optional.isPresent()).isFalse();
	}
	
	@Test
	public void must_find_person_by_ddd_and_phone_number() throws Exception {
		Optional<Person> optional = sut.findByPhoneDddAndPhoneNumber("86", "35006330");
		
		assertThat(optional.isPresent()).isTrue();

		Person person = optional.get();
		assertThat(person.getCode()).isEqualTo(3L);
		assertThat(person.getName()).isEqualTo("Cauê");
		assertThat(person.getCpf()).isEqualTo("38767897100");
	}
	
	@Test
	public void must_not_find_person_by_ddd_and_phone_number() throws Exception {
		Optional<Person> optional = sut.findByPhoneDddAndPhoneNumber("16", "35106330");
		
		assertThat(optional.isPresent()).isFalse();
	}

}
