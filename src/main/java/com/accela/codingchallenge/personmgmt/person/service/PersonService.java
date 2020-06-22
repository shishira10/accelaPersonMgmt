

package com.accela.codingchallenge.personmgmt.person.service;


import java.util.List;

import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;


public interface PersonService {

    public List<Person> listPersons();

    public List<PersonData> listAllPersonData();

    public Long getCount();

    /**
     * Creates a new person.
     * 
     * @param The information of the person to be created.
     * @return Success or Failure.
     */
    public ApiResponse createPerson(Person personData);

    /**
     * Deletes a person.
     * 
     * @param personId The id of the deleted person.
     * @return Success or Failure.
     */
    public ApiResponse deletePerson(Integer personId);

    /**
     * Updates the information of a person.
     * 
     * @param updated The information of the updated person.
     * @return Success or Failure.
     * @implNote Error thrown is person not found
     */
    public ApiResponse updatePerson(Person personData, Integer personId);
}
