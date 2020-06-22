

package com.accela.codingchallenge.personmgmt.person.service;


import java.util.List;

import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;


public interface PersonService {

    public List<Person> listPersons();

    public List<PersonData> listAllPersonData();

    public Long getCount();

    public ApiResponse addPersonData(Person personData);

    public ApiResponse deletePerson(Integer personId);

    public ApiResponse updatePerson(Person personData, Integer personId);
}
