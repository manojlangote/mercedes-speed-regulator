package com.mercedes.car.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercedes.car.utils.DisparityUtils;

@Component
public class ServiceController implements ApplicationContextAware {
	private static final String TEST_CASE_NUMBER = "testCaseNumber";
	private static final String STATUS_CODE = "status";
	private static final String JSON_URL_FROM_CLIENT = "url";
	private static final String TOTAL_TEST_CASES = "Total Test Cases";
	private DisparityUtils utils;

	public Map<Object, Object> handleGetDisaparity(Map<Object, Object> testCases)
			throws JsonMappingException, JsonProcessingException {
		int totalTestCases = (int) testCases.get(TOTAL_TEST_CASES);
		Map<Object, Object> ans = new HashMap<>();
		for (int i = 1; i <= totalTestCases; i++) {
			Map<Object, Object> test = (Map<Object, Object>) testCases.get(String.valueOf(i));
			int N = (int) test.get("N");
			Object arrObj = (Object) test.get("S");
			try {
				ArrayList<Integer> list = (ArrayList<Integer>) arrObj;
				ans.put(i, utils.minimizeDisparity(N, list.stream().mapToInt(Integer::intValue).toArray()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return ans;
	}

	public ResponseEntity<Map<Object, Object>> handleGetDisaparityV2(Map<Object, Object> requestBody)
			throws IOException {
		try {
			String url = (String) requestBody.get(JSON_URL_FROM_CLIENT);
			int testCaseNumber = (Integer)requestBody.get(TEST_CASE_NUMBER);
			Object jsonReponse = utils.getJSONFromURL(url);
			Map<Object, Object> testCases = (Map<Object, Object>) jsonReponse;
			Map<Object, Object> ans = handleGetDisaparity(testCases);
			String answerStr = utils.convertObjectToJson(ans);
			int statusCode = utils.sendPostRequest(answerStr, testCaseNumber);
			ans.put(STATUS_CODE, statusCode);
			return ResponseEntity.ok(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.utils = applicationContext.getBean(DisparityUtils.class);
	}
}
