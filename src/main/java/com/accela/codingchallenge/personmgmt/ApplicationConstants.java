

package com.accela.codingchallenge.personmgmt;


import java.util.Arrays;
import java.util.List;


public interface ApplicationConstants {

    /*
     * General
     */
    String EMPTY_STRING = "";
    String ROOT_PATH = "/api/v1/";

    /*
     * API Statuses
     */
    String FAILURE = "Failure";
    String ERROR = "Error";
    String SUCCESS = "Success";

    /*
     * Errors
     */
    String DUPLICATE_ENTRY_DB = "Duplicate data found in DB";
    String DATABASE_INSERT_ERROR = "Error while inserting record";
    String DATABASE_OPERATION_ERROR = "Error during DB operations";
    String INVALID_RECORD = "Record not found - Invalid data for Edit/Delete";
    String INVALID_ADDRESS = "Invalid Address Data";
    String INVALID_ADDRESS_TYPE = "AddressType mismatch";
    String INCORRECT_ADDRESSTYPE = "AddressType is wrong, please use one among (shippingAddress, billingAddress, homeAddress, officeAddress)";

    /*
     * Operations
     */
    String ADD_PERSON = "addPerson";
    String DELETE_PERSON = "deletePerson";
    String EDIT_PERSON = "editPerson";
    String ADD_ADDRESS = "addAddress";
    String EDIT_ADDRESS = "editAddress";
    String DELETE_ADDRESS = "deleteAddress";

    /*
     * Static Enums (Shipping Address, Billing Address, Home Address,Office Address
     */

    List<String> VALID_ADDRESS_TYPES = Arrays.asList("shippingAddress", "billingAddress", "homeAddress", "officeAddress");
}
