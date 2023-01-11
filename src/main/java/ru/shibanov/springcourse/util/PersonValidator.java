package ru.shibanov.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shibanov.springcourse.dao.PersonDAO;
import ru.shibanov.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDao;

    @Autowired
    public PersonValidator( PersonDAO personDAO) {
        this.personDao = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDao.show(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        }
    }
}
