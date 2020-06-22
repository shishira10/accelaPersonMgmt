

package com.accela.codingchallenge.personmgmt.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.accela.codingchallenge.personmgmt.ApplicationConstants;
import com.accela.codingchallenge.personmgmt.controller.PersonMgmtController;
import com.accela.codingchallenge.personmgmt.dao.AddressRepository;
import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;

import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
public class PersonMgmtControllerTest {

    @InjectMocks
    private PersonMgmtController personController;

    private static PersonRepository personRepository;
    private static AddressRepository addressRepository;

    @BeforeAll
    public static void setup() {
        personRepository = Mockito.mock(PersonRepository.class);
        addressRepository = Mockito.mock(AddressRepository.class);
    }

    public static List<Person> createPersons() {
        Person person1 = Person.builder().firstName("Bob").lastName("Dylan").build();
        Person person2 = Person.builder().firstName("Mark").lastName("Knopfler").build();
        Person person3 = Person.builder().firstName("Jimmy").lastName("Page").build();
        Person person4 = Person.builder().firstName("Freddie").lastName("Mercury").build();

        return Arrays.asList(person1, person2, person3, person4);
    }

    public static List<Person> createDuplicatePersons() {
        Person person1 = Person.builder().firstName("Bob").lastName("Dylan").build();
        Person person2 = Person.builder().firstName("Bob").lastName("Dylan").build();

        return Arrays.asList(person1, person2);
    }

    @Test
    public void testAddPersons() {
        List<Person> allPersons = createPersons();

        ApiResponse sampleResponse = ApiResponse.builder().status("Success").errorMessage(ApplicationConstants.EMPTY_STRING).build();
        Mockito.lenient().when(personController.addPerson(Mockito.any())).thenReturn(Mono.just(ResponseEntity.ok().body(sampleResponse)));

        allPersons.stream().forEach(personData -> {
            final Mono<ResponseEntity<ApiResponse>> response = personController.addPerson(personData);

            assertEquals(HttpStatus.OK, response.block().getStatusCode());
            assertEquals(ApplicationConstants.EMPTY_STRING, response.block().getBody().getErrorMessage());
            assertEquals(sampleResponse, response.block().getBody());
        });
        //
        // final Mono<ResponseEntity<List<Person>>> listPersons = personController.getAllPersons();
        //
        // int test2 = listPersons.block().getBody().size();
        // assertEquals(listPersons.block().getBody().size(), 1);
        //
        // final Mono<ResponseEntity<Long>> numberOfPersons = personController.getCount();
        //
        // int test = numberOfPersons.block().getBody().intValue();
        // assertEquals(numberOfPersons.block().getBody().intValue(), 4);
        // assertEquals(numberOfPersons.block().getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAddDuplicatePersons() {
        List<Person> allPersons = createDuplicatePersons();

        allPersons.stream().forEach(personData -> {
            final Mono<ResponseEntity<ApiResponse>> response = personController.addPerson(personData);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.block().getStatusCode());
            assertEquals(response.block().getBody().getErrorMessage(), ApplicationConstants.EMPTY_STRING);
        });
    }
    
    @Test
    public void testNumberOfPersons() {
        
        Mockito.lenient().when(personController.getCount()).thenReturn(Mono.just(ResponseEntity.ok().body((long)2)));
        
        
        final Mono<ResponseEntity<Long>> numberOfPersons = personController.getCount();
         assertEquals(numberOfPersons.block().getBody().intValue(), 4);
         assertEquals(numberOfPersons.block().getStatusCode(), HttpStatus.OK);
    }

}
