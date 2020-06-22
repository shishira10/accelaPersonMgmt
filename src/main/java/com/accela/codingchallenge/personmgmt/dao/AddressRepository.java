

package com.accela.codingchallenge.personmgmt.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.accela.codingchallenge.personmgmt.entitites.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<List<Address>> findByPersonId(int personId);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("delete from Address where personId = :personId")
    void deleteByPersonId(@Param("personId") Integer personId);
    // void deleteByPersonId(int personId);



    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Modifying
    @Query("delete from Address WHERE personId = :personId AND address_type= :address_type")
    void deleteByPersonIdAndType(@Param("personId") Integer personId, @Param("address_type") String address_type);
    // void deleteByPersonIdAndType(Integer personId, String address_type);
}
