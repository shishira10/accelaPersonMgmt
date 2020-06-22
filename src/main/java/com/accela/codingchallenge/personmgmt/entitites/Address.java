

package com.accela.codingchallenge.personmgmt.entitites;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Address")
public class Address {

     @Id
     @GeneratedValue
     private Integer Id;
     private int personId;
    
    private String addressType;
    private String street;
    private String city;
    private String state;
    private String postalCode;

    private Date createdAt;
    private Date modifedAt;

}
