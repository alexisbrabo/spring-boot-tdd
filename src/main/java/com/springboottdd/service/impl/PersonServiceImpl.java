package com.springboottdd.service.impl;

import java.util.Optional;

import com.springboottdd.models.Person;
import com.springboottdd.models.Phone;
import com.springboottdd.repository.PersonRepository;
import com.springboottdd.service.PersonService;
import com.springboottdd.service.exception.DuplicateCpfException;
import com.springboottdd.service.exception.DuplicatePhoneException;
import com.springboottdd.service.exception.PhoneNotFoundException;

public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;

	public PersonServiceImpl(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Person save(Person person) throws DuplicateCpfException, DuplicatePhoneException {
		Optional<Person> optional = personRepository.findByCpf(person.getCpf());

		if (optional.isPresent()) {
			throw new DuplicateCpfException();
		}

		final String ddd = person.getPhones().get(0).getDdd();
		final String number = person.getPhones().get(0).getNumber();
		optional = personRepository.findByPhoneDddAndPhoneNumber(ddd, number);

		if (optional.isPresent()) {
			throw new DuplicatePhoneException();
		}

		return personRepository.save(person);
	}

	@Override
	public Person findByPhone(Phone phone) throws PhoneNotFoundException {
		Optional<Person> optional = personRepository.findByPhoneDddAndPhoneNumber(phone.getDdd(), phone.getNumber());
		return optional.orElseThrow(PhoneNotFoundException::new);
	}

}
