package com.springboottdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboottdd.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findByCpf(String cpf);

	@Query("SELECT bean FROM Person bean JOIN bean.phones ph WHERE ph.ddd = :ddd and ph.number = :number")
	Optional<Person> findByPhoneDddAndPhoneNumber(@Param("ddd") String ddd, @Param("number") String number);

}
