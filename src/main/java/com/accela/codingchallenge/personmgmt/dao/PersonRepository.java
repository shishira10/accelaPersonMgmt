package com.accela.codingchallenge.personmgmt.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accela.codingchallenge.personmgmt.entitites.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}
