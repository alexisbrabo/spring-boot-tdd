package com.springboottdd.repository;

import java.util.Optional;

import com.springboottdd.models.Person;

public interface PersonRepository {

	Person save(Person person);

	Optional<Person> findByCpf(String cpf);

	Optional<Person> findByPhoneDddAndPhoneNumber(String ddd, String number);

}
