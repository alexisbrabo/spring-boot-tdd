package com.springboottdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboottdd.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findByCpf(String cpf);

	Optional<Person> findByPhoneDddAndPhoneNumber(String ddd, String number);

}
