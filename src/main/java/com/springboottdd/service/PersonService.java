package com.springboottdd.service;

import com.springboottdd.models.Person;
import com.springboottdd.models.Phone;
import com.springboottdd.service.exception.DuplicateCpfException;
import com.springboottdd.service.exception.DuplicatePhoneException;
import com.springboottdd.service.exception.PhoneNotFoundException;

public interface PersonService {

	Person save(Person person) throws DuplicateCpfException, DuplicatePhoneException;

	Person findByPhone(Phone phone) throws PhoneNotFoundException;

}
