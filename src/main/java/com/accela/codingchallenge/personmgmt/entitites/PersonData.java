package com.accela.codingchallenge.personmgmt.entitites;

import java.util.List;

import lombok.Data;

@Data
public class PersonData {

    private Person person;
    private List<Address> addressInfo;
}
