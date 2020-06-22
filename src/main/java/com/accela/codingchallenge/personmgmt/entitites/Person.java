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
@Table(name = "Person")
public class Person {
	@Id
	@GeneratedValue
	private Integer Id;
	private String firstName;
	private String lastName;

	private Date createdAt;
	private Date modifedAt;

	/*
	  INSERT INTO PERSON(FIRSTNAME,LASTNAME) VALUES('Shishira','Chandra'); 
	  SELECT * FROM PERSON;
	 */
}
