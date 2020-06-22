package com.accela.codingchallenge.personmgmt.entitites;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {

    private Person person;
    private List<Address> addressInfo;
}
