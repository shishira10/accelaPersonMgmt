

package com.accela.codingchallenge.personmgmt.address.service;


import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;


public interface AddressService {

    public ApiResponse addAddress(Integer personId, Address addressData);

    public ApiResponse updateAddress(Integer personId, Address addressData);

    public ApiResponse deleteAllAddress(Integer personId);

    public ApiResponse deleteAddressByType(Integer personId, String addressType);
}
