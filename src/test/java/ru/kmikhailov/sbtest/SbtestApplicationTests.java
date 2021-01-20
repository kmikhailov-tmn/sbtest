package ru.kmikhailov.sbtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class SbtestApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;
	private JSONObject jsonObject;

	private JSONObject createOrder(String seller, String customer, String milkCode, String waterCode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seller", seller);
		jsonObject.put("customer", customer);

		JSONArray products = new JSONArray();
		JSONObject milkObject = new JSONObject();
		milkObject.put("name", "milk");
		milkObject.put("code", milkCode);
		products.add(milkObject);

		JSONObject waterObject = new JSONObject();
		waterObject.put("name", "water");
		waterObject.put("code", waterCode);
		products.add(waterObject);

		jsonObject.put("products", products);
		return jsonObject;
	}

	@Test
	public void testAsGivenJson() throws URISyntaxException, JsonProcessingException {
		String baseUrl = makeUrl();
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject orderJsonObject = createOrder("123534251", "648563524", "2364758363546", "3656352437590");
		HttpEntity<String> request =
				new HttpEntity<String>(orderJsonObject.toString(), headers);
		ResponseEntity<Order> entity = restTemplate.postForEntity(uri, request, Order.class);
		Order resultOrder = entity.getBody();

		ObjectMapper mapper = new ObjectMapper();
		Order sourceOrder = mapper.readValue(orderJsonObject.toJSONString(), Order.class);
		Assertions.assertEquals(mapper.writeValueAsString(resultOrder), mapper.writeValueAsString(sourceOrder));
	}

	@Test
	public void testWrongJson() throws URISyntaxException, JsonProcessingException {
		String baseUrl = makeUrl();
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject orderJsonObject = createOrder("12353425", "648563524", "23645758363546", "3656352437590");
		HttpEntity<String> request =
				new HttpEntity<String>(orderJsonObject.toString(), headers);
		ResponseEntity<Order> entity = restTemplate.postForEntity(uri, request, Order.class);
		Order resultOrder = entity.getBody();

		ObjectMapper mapper = new ObjectMapper();
		Order sourceOrder = mapper.readValue(orderJsonObject.toJSONString(), Order.class);
		Assertions.assertNotEquals(mapper.writeValueAsString(resultOrder), mapper.writeValueAsString(sourceOrder));
	}

	private String makeUrl() {
		return "http://localhost:" + randomServerPort + "/putOrder";
	}

}
