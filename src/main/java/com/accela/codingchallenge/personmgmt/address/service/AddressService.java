

package com.accela.codingchallenge.personmgmt.address.service;


import com.accela.codingchallenge.personmgmt.entitites.Address;
import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;


public interface AddressService {

    /**
     * Creates a new address for a person.
     * 
     * @param The information of the address to be added to personId.
     * @return Success or Failure.
     * @implSpec shippingAddress, billingAddress, homeAddress, officeAddress are the valid address types
     */
    public ApiResponse createAddress(Integer personId, Address addressData);

    /**
     * Updates an existing address for a person.
     * 
     * @param The information of the address to be updated to personId.
     * @return Success or Failure.
     * @implSpec If addressType is non existent beforehand, error thrown
     */

    public ApiResponse updateAddress(Integer personId, Address addressData);

    /**
     * Deletes an existing address for a person.
     * 
     * @param personId for whom the address needs to be deleted
     * @return Success or Failure.
     * @implSpec If address is non existent beforehand, error thrown
     */

    public ApiResponse deleteAllAddress(Integer personId);

    /**
     * Deletes an existing address for a person.
     * 
     * @param personId and addressType that should be deleted
     * @return Success or Failure.
     * @implSpec If address is non existent beforehand or if addressType is invalid, error thrown
     */
    public ApiResponse deleteAddressByType(Integer personId, String addressType);
}
