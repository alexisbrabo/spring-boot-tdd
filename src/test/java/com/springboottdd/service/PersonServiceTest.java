package com.springboottdd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboottdd.models.Person;
import com.springboottdd.models.Phone;
import com.springboottdd.repository.PersonRepository;
import com.springboottdd.service.exception.DuplicateCpfException;
import com.springboottdd.service.exception.DuplicatePhoneException;
import com.springboottdd.service.exception.PhoneNotFoundException;
import com.springboottdd.service.impl.PersonServiceImpl;

@RunWith(SpringRunner.class)
public class PersonServiceTest {

	private static final String NUMBER = "32873232";
	private static final String DDD = "091";
	private static final String NAME = "Alexis Amaral";
	private static final String CPF = "00533231221";

	@MockBean
	private PersonRepository personRepository;

	private PersonService sut;

	private Person person;
	private Phone phone;

	@Before
	public void setUp() throws Exception {
		sut = new PersonServiceImpl(personRepository);

		person = new Person();
		person.setName(NAME);
		person.setCpf(CPF);

		phone = new Phone();
		phone.setDdd(DDD);
		phone.setNumber(NUMBER);

		person.setPhones(Arrays.asList(phone));

		when(personRepository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(personRepository.findByPhoneDddAndPhoneNumber(DDD, NUMBER)).thenReturn(Optional.empty());
	}

	@Test
	public void must_save_person_in_repository() throws Exception {
		sut.save(person);

		verify(personRepository).save(person);
	}

	@Test(expected = DuplicateCpfException.class)
	public void must_not_save_two_persons_with_same_cpf() throws Exception {
		when(personRepository.findByCpf(CPF)).thenReturn(Optional.of(person));

		sut.save(person);
	}

	@Test(expected = DuplicatePhoneException.class)
	public void must_not_save_two_persons_with_same_phone() throws Exception {
		when(personRepository.findByPhoneDddAndPhoneNumber(DDD, NUMBER)).thenReturn(Optional.of(person));

		sut.save(person);
	}

	@Test(expected = PhoneNotFoundException.class)
	public void must_return_exception_of_not_found_when_not_exists_person_with_ddd_and_phone_number() throws Exception {
		sut.findByPhone(phone);
	}

	@Test
	public void must_find_person_by_ddd_and_phone_number() throws Exception {
		when(personRepository.findByPhoneDddAndPhoneNumber(DDD, NUMBER)).thenReturn(Optional.of(person));

		Person personTest = sut.findByPhone(phone);

		verify(personRepository).findByPhoneDddAndPhoneNumber(DDD, NUMBER);

		assertThat(personTest).isNotNull();
		assertThat(personTest.getName()).isEqualTo(NAME);
		assertThat(personTest.getCpf()).isEqualTo(CPF);

	}

}
