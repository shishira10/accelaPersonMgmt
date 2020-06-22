

package com.accela.codingchallenge.personmgmt.entitites;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "Address")
public class Address {

     @Id
     @GeneratedValue
     private Integer Id;
     private int personId;
    
    private String address_type;
    private String street;
    private String city;
    private String state;
    private String postalCode;

    private Date createdAt;
    private Date modifedAt;

}
