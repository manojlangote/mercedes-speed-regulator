package com.mercedes.car.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mercedes.car.service.MyRequestBody;

@Component
public class DisparityUtils {
	public int minimizeDisparity(int n, int[] speeds) {

		int[] sortedSpeeds = Arrays.copyOf(speeds, n);
		Arrays.sort(sortedSpeeds);
		int sum = 0;
		for (int i = 0; i < n; i++) {
			int d;
			if (i == 0) {
				d = 0;
			} else {
				d = sortedSpeeds[i] - sortedSpeeds[i - 1];
			}
			sum += d;
		}
		return sum;
	}

	public int sendPostRequest(String json, int testCaseNumber) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://mercedes-hiring-2023.hackerearth.com/check";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		MyRequestBody requestBody = new MyRequestBody("manojlangote22@gmail.com", "@@@As1dfg2", testCaseNumber, json);
		HttpEntity<MyRequestBody> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		return response.getStatusCodeValue();
	}

	public static String convertObjectToJson(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(object);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getJSONFromURL(String dataURL) throws IOException {
		URL url = new URL(dataURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // If the response code is HTTP_OK (200)
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(response.toString(), Map.class);
		}
		return null;
	}
}
