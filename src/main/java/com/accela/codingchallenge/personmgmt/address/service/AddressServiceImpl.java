

package com.accela.codingchallenge.personmgmt.address.service;


import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.HttpStatus;

import com.accela.codingchallenge.personmgmt.ApplicationConstants;
import com.accela.codingchallenge.personmgmt.common.Utility;
import com.accela.codingchallenge.personmgmt.dao.AddressRepository;
import com.accela.codingchallenge.personmgmt.dao.PersonRepository;
import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;


@Named
public class AddressServiceImpl implements AddressService {

    @Inject
    PersonRepository personRepository;

    @Inject
    AddressRepository addressRepository;

    @Override
    public ApiResponse addAddress(final Integer personId, final Address addressData) {
        ApiResponse response = new ApiResponse();

        if (personRepository.findById(personId).isPresent()) {

            if (ApplicationConstants.VALID_ADDRESS_TYPES.contains(addressData.getAddressType())) {
                addressData.setPersonId(personId);
                addressData.setCreatedAt(new java.util.Date(System.currentTimeMillis()));
                addressRepository.save(addressData);
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
        return response;
    }

    @Override
    public ApiResponse updateAddress(final Integer personId, final Address addressData) {
        ApiResponse response = new ApiResponse();

        try {
            if (personRepository.findById(personId).isPresent() && ApplicationConstants.VALID_ADDRESS_TYPES.contains(addressData.getAddressType())) {
                Optional<List<Address>> currentAddress = addressRepository.findByPersonId(personId);

                if (currentAddress.isPresent()) {

                    Optional<Address> addressOfCorrectType = currentAddress.get().stream()
                                .filter(DBEntity -> DBEntity.getAddressType().equalsIgnoreCase(addressData.getAddressType())).findFirst();
                    if (addressOfCorrectType.isPresent()) {
                        addressData.setId(addressOfCorrectType.get().getId());
                        addressData.setPersonId(personId);
                        addressData.setModifedAt(new java.util.Date(System.currentTimeMillis()));
                        addressRepository.save(addressData);
                        response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.EDIT_ADDRESS,
                                    HttpStatus.OK);
                    }
                    else {
                        response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_ADDRESS_TYPE,
                                    ApplicationConstants.EDIT_ADDRESS, HttpStatus.OK);
                    }
                }
            }
            else {
                response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.EDIT_ADDRESS,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.EDIT_ADDRESS, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public ApiResponse deleteAllAddress(Integer personId) {
        ApiResponse response = new ApiResponse();
        if (personRepository.findById(personId).isPresent()) {
            try {
                addressRepository.deleteByPersonId(personId);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.DELETE_ADDRESS,
                            HttpStatus.OK);
            }
            catch (Exception e) {
                response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.DELETE_ADDRESS,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.DELETE_ADDRESS,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public ApiResponse deleteAddressByType(Integer personId, String addressType) {
        ApiResponse response = new ApiResponse();
        if (personRepository.findById(personId).isPresent()) {
            try {
                addressRepository.deleteByPersonIdAndType(personId, addressType);
                response = Utility.setApiResponse(ApplicationConstants.SUCCESS, ApplicationConstants.EMPTY_STRING, ApplicationConstants.DELETE_ADDRESS,
                            HttpStatus.OK);
            }
            catch (Exception e) {
                response = Utility.setApiResponse(ApplicationConstants.ERROR, e.getMessage(), ApplicationConstants.DELETE_ADDRESS,
                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            response = Utility.setApiResponse(ApplicationConstants.FAILURE, ApplicationConstants.INVALID_RECORD, ApplicationConstants.DELETE_ADDRESS,
                        HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

}
