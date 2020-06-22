package com.accela.codingchallenge.personmgmt.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.accela.codingchallenge.personmgmt.entitites.ApiResponse;

public class Utility {

    public static <T> T getOrDefault(final T targetValue, final T defaultValue) {
        return targetValue != null ? targetValue : defaultValue;
    }

    public static String getOrDefault(final String targetValue, final String defaultValue) {
        return !StringUtils.isEmpty(targetValue) ? targetValue : defaultValue;
    }
    
	public static ApiResponse setApiResponse(String status, String errorMessage, String apiName,
			HttpStatus httpStatus) {

		return ApiResponse.builder().status(status).errorMessage(errorMessage).apiName(apiName).apiStatus(httpStatus)
				.build();

	}
}
