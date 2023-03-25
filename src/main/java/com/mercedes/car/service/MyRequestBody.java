package com.mercedes.car.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyRequestBody {
	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	@JsonProperty("testCase")
	private int testCaseNumber;

	@JsonProperty("answerStr")
	private String answerStr;

	public MyRequestBody(String email, String password, int testCaseNumber, String answerStr) {
		this.email = email;
		this.password = password;
		this.testCaseNumber = testCaseNumber;
		this.answerStr = answerStr;
	}
}
