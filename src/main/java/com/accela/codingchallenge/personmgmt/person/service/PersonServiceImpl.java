

package com.accela.codingchallenge.personmgmt.person.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.HttpStatus;

import com.accela.codingchallenge.personmgmt.ApplicationConstants;
import com.accela.codingchallenge.personmgmt.common.Utility;
import com.accela.codingchallenge.personmgmt.dao.AddressRepository;
import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;


@Named
public class PersonServiceImpl implements PersonService {


    @Inject
    PersonRepository personRepository;

    @Inject
    AddressRepository addressRepository;

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
        List<PersonData> response = new ArrayList<PersonData>();
        List<Person> allPersons = personRepository.findAll();

        allPersons.stream().forEach(person -> {
            PersonData personData = new PersonData();
            addressRepository.findByPersonId(person.getId()).ifPresent(data -> {
                personData.setAddressInfo(data);
            });
            personData.setPerson(person);
            response.add(personData);
        });
        return response;
    }

    @Override
    public Long getCount() {
        return personRepository.count();
    }

    @Override
    public ApiResponse addPersonData(final Person personData) {
        ApiResponse response = new ApiResponse();
        List<Person> allPersons = (List<Person>)personRepository.findAll();

        if (allPersons.stream().filter(DBEntity -> DBEntity.getFirstName().equalsIgnoreCase(personData.getFirstName()) &&
                    DBEntity.getLastName().equalsIgnoreCase(personData.getLastName())).findFirst().isPresent()) {

            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.DUPLICATE_ENTRY_DB, ApplicationConstants.ADD_PERSON,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            Person newPerson = new Person();

            try {
                newPerson.setFirstName(Utility.getOrDefault(personData.getFirstName(), ApplicationConstants.EMPTY_STRING));
                newPerson.setLastName(Utility.getOrDefault(personData.getLastName(), ApplicationConstants.EMPTY_STRING));
                newPerson.setCreatedAt(new java.util.Date(System.currentTimeMillis()));

                personRepository.save(newPerson);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.ADD_PERSON,
                            HttpStatus.OK);
            }
            catch (Exception e) {
                response = Utility.setApiResponse(ApplicationConstants.ERROR, ApplicationConstants.DATABASE_INSERT_ERROR, ApplicationConstants.ADD_PERSON,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }

    @Override
    public ApiResponse deletePerson(final Integer personId) {
        ApiResponse response = new ApiResponse();

        try {
            if (personRepository.findById(personId).isPresent()) {
                personRepository.deleteById(personId);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.DELETE_PERSON,
                            HttpStatus.OK);
            }
            else {
                response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.DELETE_PERSON,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.DELETE_PERSON, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ApiResponse updatePerson(final Person personData, final Integer personId) {
        ApiResponse response = new ApiResponse();
        try {
            if (personRepository.findById(personId).isPresent()) {
                personData.setModifedAt(new java.util.Date(System.currentTimeMillis()));
                personData.setId(personId);
                personRepository.save(personData);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.EDIT_PERSON,
                            HttpStatus.OK);
            }
            else {
                response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.EDIT_PERSON,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.EDIT_PERSON, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
