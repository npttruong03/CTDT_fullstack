package CTDTFullStack.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CTDTFullStack.Models.ChuyenNganh;
import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class ChuyenNganhService {
	private final static String apiURL = Utils.BASE_URL + "chuyennganh";
	private HttpHeaders headers = new HttpHeaders();
	RestTemplate restTemplate = new RestTemplate();

	private AppConfig appConfig;
	@Autowired
	public ChuyenNganhService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	public List<ChuyenNganh> getAllChuyenNganhAPI() throws JsonMappingException, JsonProcessingException {
		String apiurl = apiURL;
		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders() ,HttpMethod.GET, URI.create(apiurl));
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		String json = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<ChuyenNganh> listchuyennganh = objectMapper.readValue(json, new TypeReference<List<ChuyenNganh>>() {
		});
		return listchuyennganh;
	}
	
	public Boolean create(ChuyenNganh chuyenNganh) {
		String apiurl = apiURL + "/add";
		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<ChuyenNganh> requestEntity = new HttpEntity<>(chuyenNganh, headers);
//		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(),headers, HttpMethod.POST, URI.create(apiurl));
//		HttpEntity<ChuyenNganh> requestEntity = new HttpEntity<>(chuyenNganh, headers);
//		ResponseEntity<Boolean> response = restTemplate.exchange(apiurl, HttpMethod.POST, requestEntity, Boolean.class);
		RequestEntity<?>requestEntity = new RequestEntity<>(chuyenNganh, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(apiurl));
		ResponseEntity<Boolean>response = restTemplate.exchange(requestEntity, Boolean.class);
		return response.getBody();
	}
	
	public Boolean editChuyenNganh(ChuyenNganh chuyenNganh, int id) {
		String urlapi = apiURL + "/update/" + id;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ChuyenNganh> requestEntity = new HttpEntity<>(chuyenNganh, appConfig.cookieStore().getHeaders());
		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(urlapi, HttpMethod.PUT, requestEntity, Boolean.class);
		if(responseEntity.getBody().equals(false)) {
			return false;
		}
		return responseEntity.getBody();
	}
	
	public ChuyenNganh getByID(int id) throws Exception {
		for(ChuyenNganh chuyenNganh : getAllChuyenNganhAPI()) {
			if(chuyenNganh.getId() == id) {
				return chuyenNganh;
			}
		}
		return null;
	}
	
	public Boolean countSV(int id, ChuyenNganh chuyenNganh) {
		String urlcount = apiURL + "/countSV/" + id;
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(chuyenNganh, appConfig.cookieStore().getHeaders() ,HttpMethod.GET, URI.create(urlcount));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		System.out.println(response);
		if (response.getBody().equals(true)) {
			return true;
		}
		return response.getBody();
	}
	
	

	
}
