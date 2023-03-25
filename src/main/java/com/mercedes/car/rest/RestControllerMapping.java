package com.mercedes.car.rest;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mercedes.car.service.ServiceController;

@RestController
public class RestControllerMapping implements ApplicationContextAware {
	private ServiceController service;

	@GetMapping("/v1/getdisparity")
	public ResponseEntity<Map<Object, Object>> getDisparity(@RequestBody Map<Object, Object> testCases)
			throws JsonMappingException, JsonProcessingException {
		return ResponseEntity.ok(service.handleGetDisaparity(testCases));
	}

	@PostMapping("/v2/getdisparity")
	public ResponseEntity<Map<Object, Object>> getDisparityV2(@RequestBody Map<Object, Object> url) throws IOException {
		return service.handleGetDisaparityV2(url);
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.service = applicationContext.getBean(ServiceController.class);
	}
}
