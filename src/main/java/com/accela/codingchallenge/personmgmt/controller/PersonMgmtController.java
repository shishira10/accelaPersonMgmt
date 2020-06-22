

package com.accela.codingchallenge.personmgmt.controller;


import java.util.List;

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
import com.accela.codingchallenge.personmgmt.address.service.AddressService;
import com.accela.codingchallenge.personmgmt.dao.AddressRepository;
import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;
import com.accela.codingchallenge.personmgmt.person.service.PersonService;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(ApplicationConstants.ROOT_PATH)
public class PersonMgmtController {

    @Inject
    PersonRepository personRepository;

    @Inject
    AddressRepository addressRepository;

    private final PersonService personService;
    private final AddressService addressService;

    public PersonMgmtController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }

    @PutMapping("/addPerson")
    public Mono<ResponseEntity<ApiResponse>> addPerson(@RequestBody final Person personData) {

        ApiResponse response = personService.addPersonData(personData);
        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));

    }

    @DeleteMapping("/deletePerson/{personId}")
    public Mono<ResponseEntity<ApiResponse>> removePerson(@PathVariable("personId") final Integer personId) {

        ApiResponse response = personService.deletePerson(personId);

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PatchMapping("/editPerson/{personId}")
    public Mono<ResponseEntity<ApiResponse>> editPerson(@PathVariable("personId") final Integer personId, @RequestBody final Person personData) {

        ApiResponse response = personService.updatePerson(personData, personId);

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PutMapping("/addAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> createPersonAddress(@PathVariable("personId") final Integer personId, @RequestBody final Address address) {
        ApiResponse response = addressService.addAddress(personId, address);
        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @PatchMapping("/editAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> editAddress(@PathVariable("personId") final Integer personId, @RequestBody final Address address) {
        ApiResponse response = addressService.updateAddress(personId, address);

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }

    @DeleteMapping("/deleteAddress/{personId}")
    public Mono<ResponseEntity<ApiResponse>> removePerson(@PathVariable("personId") final Integer personId,
                @RequestParam(value = "addressType", required = false) String addressType) {
        ApiResponse response = new ApiResponse();

        if (addressType != null)
            response = addressService.deleteAddressByType(personId, addressType);
        else
            response = addressService.deleteAllAddress(personId);

        return Mono.just(ResponseEntity.status(response.getApiStatus()).body(response));
    }


    @GetMapping("/fetchAllPersons")
    public Mono<ResponseEntity<List<Person>>> getAllPersons() {

        try {
            return Mono.just(ResponseEntity.ok(personService.listPersons()));
        }
        catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping("/fetchAllData")
    public Mono<ResponseEntity<List<PersonData>>> getAllPersonData() {
        try {
            return Mono.just(ResponseEntity.ok(personService.listAllPersonData()));
        }
        catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @GetMapping("/numberOfPersons")
    public Mono<ResponseEntity<Long>> getCount() {
        try {
            return Mono.just(ResponseEntity.ok(personService.getCount()));
        }
        catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

}
