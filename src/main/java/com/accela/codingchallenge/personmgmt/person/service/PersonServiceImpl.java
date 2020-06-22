package com.accela.codingchallenge.personmgmt.person.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;

@Named
public class PersonServiceImpl implements PersonService{

    
    @Inject
    PersonRepository personRepository;
    
    @Override
    public List<Person> listPersons() {

        try {
            
            return (List<Person>)personRepository.findAll();
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<PersonData> listAllPersonData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getCount() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ApiResponse addPersonData() {
        // TODO Auto-generated method stub
        return null;
    }

}
