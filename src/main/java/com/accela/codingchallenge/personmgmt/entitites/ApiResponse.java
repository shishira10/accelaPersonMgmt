package com.accela.codingchallenge.personmgmt.entitites;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String status;
	private String errorMessage;
	private String apiName;
	@JsonIgnore
	private HttpStatus apiStatus;
}
