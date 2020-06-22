

package com.accela.codingchallenge.personmgmt.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accela.codingchallenge.personmgmt.ApplicationConstants;
import com.accela.codingchallenge.personmgmt.common.Utility;
import com.accela.codingchallenge.personmgmt.dao.AddressRepository;
import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;
import com.accela.codingchallenge.personmgmt.person.service.PersonService;
import com.accela.codingchallenge.personmgmt.person.service.PersonServiceImpl;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(ApplicationConstants.ROOT_PATH)
public class PersonMgmtController {

    @Inject
    PersonRepository personRepository;

    @Inject
    AddressRepository addressRepository;

    @Inject 
    PersonServiceImpl impl;

    @GetMapping("/fetchAllPersons")
    public Mono<ResponseEntity<List<Person>>> getAllPersons() {
        return Mono.just(ResponseEntity.ok(impl.listPersons()));
    }

    @GetMapping("/fetchAllData")
    public Mono<ResponseEntity<List<PersonData>>> getAllPersonnellData() {

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

        return Mono.just(ResponseEntity.ok(response));
    }

    @GetMapping("/numberOfPersons")
    public Mono<ResponseEntity<Long>> getCount() {

        return Mono.just(ResponseEntity.ok(personRepository.count()));
    }


    @PutMapping("/addPerson")
    public Mono<ResponseEntity<ApiResponse>> addPerson(@RequestBody final Person personData) {

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

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));

    }

    @DeleteMapping("/deletePerson/{personId}")
    public Mono<ResponseEntity<ApiResponse>> removePerson(@PathVariable("personId") final Integer personId) {

        ApiResponse response = new ApiResponse();

        if (personRepository.findById(personId).isPresent()) {
            personRepository.deleteById(personId);
            response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.DELETE_PERSON,
                        HttpStatus.OK);
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.DELETE_PERSON,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PatchMapping("/editPerson/{personId}")
    public Mono<ResponseEntity<ApiResponse>> editPerson(@PathVariable("personId") final Integer personId, @RequestBody final Person personData) {

        ApiResponse response = new ApiResponse();
        if (personRepository.findById(personId).isPresent()) {
            personData.setModifedAt(new java.util.Date(System.currentTimeMillis()));
            personData.setId(personId);
            personRepository.save(personData);
            response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.EDIT_PERSON, HttpStatus.OK);
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.EDIT_PERSON,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PutMapping("/addAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> createPersonAddress(@PathVariable("personId") final Integer personId, @RequestBody final Address address) {
        ApiResponse response = new ApiResponse();
        if (personRepository.findById(personId).isPresent()) {

            if (ApplicationConstants.VALID_ADDRESS_TYPES.contains(address.getAddress_type())) {
                address.setPersonId(personId);
                address.setCreatedAt(new java.util.Date(System.currentTimeMillis()));
                addressRepository.save(address);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.ADD_ADDRESS,
                            HttpStatus.OK);
            }
            else {
                response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_ADDRESS, ApplicationConstants.ADD_ADDRESS,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.ADD_ADDRESS,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PatchMapping("/editAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> editAddress(@PathVariable("personId") final Integer personId, @RequestBody final Address address) {
        ApiResponse response = new ApiResponse();

        try {
            if (personRepository.findById(personId).isPresent()) {
                Optional<List<Address>> currentAddress = addressRepository.findByPersonId(personId);

                currentAddress.ifPresent(data -> {
                    data.stream().filter(DBEntity -> DBEntity.getAddress_type().equalsIgnoreCase(address.getAddress_type())).findAny().ifPresent(info -> {
                        address.setId(info.getId());
                        address.setPersonId(personId);
                        addressRepository.save(address);
                    });
                });

                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.EDIT_ADDRESS,
                            HttpStatus.OK);
            }
            else {
                response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.EDIT_ADDRESS,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.EDIT_ADDRESS, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @DeleteMapping("/deleteAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> removePerson(@PathVariable("personId") final Integer personId,
                @RequestParam(value = "addressType", required = false) String addressType) {
        ApiResponse response = new ApiResponse();

        if (personRepository.findById(personId).isPresent()) {
            if (addressType != null)
                addressRepository.deleteByPersonIdAndType(personId, addressType);
            else
                addressRepository.deleteByPersonId(personId);

            response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.DELETE_ADDRESS,
                        HttpStatus.OK);
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.DELETE_PERSON,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }
}
