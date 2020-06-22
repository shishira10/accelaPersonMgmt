

package com.accela.codingchallenge.personmgmt.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
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
import com.accela.codingchallenge.personmgmt.address.service.AddressService;
import com.accela.codingchallenge.personmgmt.controller.PersonMgmtController;
import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;
import com.accela.codingchallenge.personmgmt.entitites.Person;
import com.accela.codingchallenge.personmgmt.entitites.PersonData;
import com.accela.codingchallenge.personmgmt.person.service.PersonService;

import reactor.core.publisher.Mono;


@ExtendWith(MockitoExtension.class)
public class PersonMgmtControllerTest {

    @InjectMocks
    private PersonMgmtController personController;

    private static PersonService personService;
    private static AddressService addressService;

    @BeforeAll
    public static void setup() {
        personService = Mockito.mock(PersonService.class);
        addressService = Mockito.mock(AddressService.class);
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

    public static List<PersonData> createPersonData() {

        List<PersonData> allPersonData = new ArrayList<>();
        Person person1 = Person.builder().firstName("Bob").lastName("Dylan").Id(1000).build();
        Address address1 = Address.builder().street("123 Blvd").city("Detroit").addressType("shippingAddress").postalCode("864982").personId(1000).build();
        Address address12 = Address.builder().street("124 Blvd").city("Detroit").addressType("homeAddress").postalCode("864982").personId(1000).build();

        Person person2 = Person.builder().firstName("Jimmy").lastName("Page").Id(1001).build();
        Address address2 = Address.builder().street("456 Downing Street").city("London").addressType("billingAddress").postalCode("AFG-ATY").personId(1001)
                    .build();
        allPersonData.add(PersonData.builder().addressInfo(Arrays.asList(address1, address12)).person(person1).build());
        allPersonData.add(PersonData.builder().addressInfo(Arrays.asList(address2)).person(person2).build());

        return allPersonData;
    }

    @Test
    public void testAddPersons() {
        List<Person> allPersons = createPersons();

        ApiResponse successResponse = ApiResponse.builder().status("Success").errorMessage(ApplicationConstants.EMPTY_STRING).apiStatus(HttpStatus.OK).build();
        Mockito.lenient().when(personService.addPersonData(Mockito.any())).thenReturn(successResponse);

        allPersons.stream().forEach(personData -> {
            final Mono<ResponseEntity<ApiResponse>> response = personController.addPerson(personData);

            assertEquals(HttpStatus.OK, response.block().getStatusCode());
            assertEquals(ApplicationConstants.EMPTY_STRING, response.block().getBody().getErrorMessage());
            assertEquals(successResponse, response.block().getBody());
        });
    }

    @Test
    public void testAddDuplicatePersons() {
        List<Person> allPersons = createDuplicatePersons();
        ApiResponse duplicateDataResponse = ApiResponse.builder().status("Success").errorMessage(ApplicationConstants.DUPLICATE_ENTRY_DB)
                    .apiStatus(HttpStatus.OK).build();

        allPersons.stream().forEach(mockData -> {
            Mockito.lenient().when(personService.addPersonData(mockData)).thenReturn(duplicateDataResponse);
        });


        allPersons.stream().forEach(personData -> {
            final Mono<ResponseEntity<ApiResponse>> response = personController.addPerson(personData);
            assertEquals(HttpStatus.OK, response.block().getStatusCode());
            assertNotEquals(response.block().getBody().getErrorMessage(), ApplicationConstants.EMPTY_STRING);
            assertEquals(duplicateDataResponse, response.block().getBody());
        });
    }

    @Test
    public void editPerson() {

        ApiResponse successResponse = ApiResponse.builder().status(ApplicationConstants.SUCCESS).errorMessage(ApplicationConstants.EMPTY_STRING)
                    .apiStatus(HttpStatus.OK).build();
        ApiResponse errorResponse = ApiResponse.builder().status(ApplicationConstants.FAILURE).errorMessage(ApplicationConstants.INVALID_RECORD)
                    .apiStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Person person1 = Person.builder().firstName("Bob").lastName("Dylan").Id(1000).build();
        Person person2 = Person.builder().firstName("Kurt").lastName("Cobain").Id(1100).build();

        Mockito.lenient().when(personService.updatePerson(person1, person1.getId())).thenReturn(successResponse);
        Mockito.lenient().when(personService.updatePerson(person2, person2.getId())).thenReturn(errorResponse);

        Mono<ResponseEntity<ApiResponse>> editPersonResponse1 = personController.editPerson(person1.getId(), person1);
        Mono<ResponseEntity<ApiResponse>> editPersonResponse2 = personController.editPerson(person2.getId(), person2);

        assertNotEquals(editPersonResponse1.block().getStatusCode(), editPersonResponse2.block().getStatusCode());
        assertNotEquals(editPersonResponse1.block().getBody(), editPersonResponse2.block().getBody());

        assertEquals(successResponse, editPersonResponse1.block().getBody());
        assertEquals(errorResponse, editPersonResponse2.block().getBody());
    }

    @Test
    public void testDeletePerson() {

        ApiResponse successResponse = ApiResponse.builder().status(ApplicationConstants.SUCCESS).errorMessage(ApplicationConstants.EMPTY_STRING)
                    .apiStatus(HttpStatus.OK).build();
        ApiResponse errorResponse = ApiResponse.builder().status(ApplicationConstants.FAILURE).errorMessage(ApplicationConstants.INVALID_RECORD)
                    .apiStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.lenient().when(personService.deletePerson(1000)).thenReturn(successResponse);
        Mockito.lenient().when(personService.deletePerson(2000)).thenReturn(errorResponse);

        Mono<ResponseEntity<ApiResponse>> editPersonResponse1 = personController.removePerson(1000);
        Mono<ResponseEntity<ApiResponse>> editPersonResponse2 = personController.removePerson(2000);

        assertNotEquals(editPersonResponse1.block().getStatusCode(), editPersonResponse2.block().getStatusCode());
        assertNotEquals(editPersonResponse1.block().getBody(), editPersonResponse2.block().getBody());

        assertEquals(successResponse, editPersonResponse1.block().getBody());
        assertEquals(errorResponse, editPersonResponse2.block().getBody());

    }

    @Test
    public void testAddAdress() {
        ApiResponse successResponse = ApiResponse.builder().status(ApplicationConstants.SUCCESS).errorMessage(ApplicationConstants.EMPTY_STRING)
                    .apiStatus(HttpStatus.OK).build();
        ApiResponse errorResponse = ApiResponse.builder().status(ApplicationConstants.FAILURE).errorMessage(ApplicationConstants.INVALID_ADDRESS)
                    .apiStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        
        Address address1 = Address.builder().street("123 Blvd").city("Detroit").addressType("shippingAddress").postalCode("864982").personId(1000).build();
        
        Mockito.lenient().when(addressService.addAddress(1000, address1)).thenReturn(successResponse);
        Mockito.lenient().when(addressService.addAddress(2000, address1)).thenReturn(errorResponse);
        
        Mono<ResponseEntity<ApiResponse>> editPersonResponse1 = personController.createPersonAddress(1000, address1);
        Mono<ResponseEntity<ApiResponse>> editPersonResponse2 = personController.createPersonAddress(2000, address1);

        assertNotEquals(editPersonResponse1.block().getStatusCode(), editPersonResponse2.block().getStatusCode());
        assertNotEquals(editPersonResponse1.block().getBody(), editPersonResponse2.block().getBody());

        assertEquals(successResponse, editPersonResponse1.block().getBody());
        assertEquals(errorResponse, editPersonResponse2.block().getBody());
        
    }
    
    @Test
    public void testEditAddress() {
        ApiResponse successResponse = ApiResponse.builder().status(ApplicationConstants.SUCCESS).errorMessage(ApplicationConstants.EMPTY_STRING)
                    .apiStatus(HttpStatus.OK).build();
        ApiResponse errorResponse = ApiResponse.builder().status(ApplicationConstants.FAILURE).errorMessage(ApplicationConstants.INVALID_ADDRESS_TYPE)
                    .apiStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
        
        Address address1 = Address.builder().street("456 Downing Street").city("London").addressType("billingAddress").postalCode("AFG-ATY").personId(1000).build();
        Address address2 = Address.builder().street("456 Downing Street").city("London").addressType("Address").postalCode("AFG-ATY").personId(2000).build();

        Mockito.lenient().when(addressService.updateAddress(1000, address1)).thenReturn(successResponse);
        Mockito.lenient().when(addressService.updateAddress(2000, address2)).thenReturn(errorResponse);

        Mono<ResponseEntity<ApiResponse>> editPersonResponse1 = personController.editAddress(1000, address1);
        Mono<ResponseEntity<ApiResponse>> editPersonResponse2 = personController.editAddress(2000, address2);

        assertNotEquals(editPersonResponse1.block().getStatusCode(), editPersonResponse2.block().getStatusCode());
        assertNotEquals(editPersonResponse1.block().getBody(), editPersonResponse2.block().getBody());

        assertEquals(successResponse, editPersonResponse1.block().getBody());
        assertEquals(errorResponse, editPersonResponse2.block().getBody());
        
    }
    
    @Test
    public void testDeleteAddress() {
        ApiResponse successResponse = ApiResponse.builder().status(ApplicationConstants.SUCCESS).errorMessage(ApplicationConstants.EMPTY_STRING)
                    .apiStatus(HttpStatus.OK).build();
        ApiResponse errorResponse = ApiResponse.builder().status(ApplicationConstants.FAILURE).errorMessage(ApplicationConstants.INVALID_RECORD)
                    .apiStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

        Mockito.lenient().when(addressService.deleteAllAddress(1000)).thenReturn(successResponse);
        Mockito.lenient().when(addressService.deleteAllAddress(2000)).thenReturn(errorResponse);

        Mono<ResponseEntity<ApiResponse>> editPersonResponse1 = personController.removePersonAddress(1000,"");
        Mono<ResponseEntity<ApiResponse>> editPersonResponse2 = personController.removePersonAddress(2000,"");

        assertNotEquals(editPersonResponse1.block().getStatusCode(), editPersonResponse2.block().getStatusCode());
        assertNotEquals(editPersonResponse1.block().getBody(), editPersonResponse2.block().getBody());

        assertEquals(successResponse, editPersonResponse1.block().getBody());
        assertEquals(errorResponse, editPersonResponse2.block().getBody());
    }
    
    @Test
    public void testNumberOfPersons() {
        List<Person> allPersons = createPersons();

        long count = 4;

        allPersons.stream().forEach(mockData -> {
            Mockito.lenient().when(personService.getCount()).thenReturn((long)count);
        });

        final Mono<ResponseEntity<Long>> numberOfPersons = personController.getCount();
        assertEquals(numberOfPersons.block().getBody().intValue(), count);
        assertEquals(numberOfPersons.block().getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testListAllPersons() {
        List<Person> allPersons = createPersons();
        Mockito.lenient().when(personService.listPersons()).thenReturn(allPersons);

        final Mono<ResponseEntity<List<Person>>> personList = personController.getAllPersons();
        assertEquals(HttpStatus.OK, personList.block().getStatusCode());
        assertEquals(allPersons, personList.block().getBody());
    }

    @Test
    public void testListAllPersonData() {
        List<PersonData> allPersonData = createPersonData();
        Mockito.lenient().when(personService.listAllPersonData()).thenReturn(allPersonData);

        Mono<ResponseEntity<List<PersonData>>> allPersonList = personController.getAllPersonData();
        assertEquals(HttpStatus.OK, allPersonList.block().getStatusCode());
        assertEquals(allPersonData, allPersonList.block().getBody());

    }
}
