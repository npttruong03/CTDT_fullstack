package CTDTFullStack.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import CTDTFullStack.Models.Chuongtrinh;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;
@Service
public class ChuongtrinhService {
	
	private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
	private String apiUrl = Utils.BASE_URL + "chuongtrinh";
	private HttpHeaders headers = new HttpHeaders();

	private AppConfig appConfig;
	@Autowired
	public ChuongtrinhService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	public List<Chuongtrinh> GetAll() throws JsonMappingException, JsonProcessingException{
		
		String apiurl = apiUrl;
		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(apiurl));
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
	    String json = response.getBody();
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 List<Chuongtrinh> listNganh = objectMapper.readValue(json,new TypeReference<List<Chuongtrinh>>() {});

		    return listNganh;
		
		
//		 String apiResponse = restTemplate.getForObject(apiUrl, String.class);
//	        List<Chuongtrinh> chuongtrinh = null;
//	        try {
//	        	chuongtrinh = objectMapper.readValue(apiResponse, new TypeReference<List<Chuongtrinh>>() {});
//	        } catch (Exception e) {
//	        	
//	        }
//	        return chuongtrinh;
	    }
	
	public Chuongtrinh post(Chuongtrinh chuongtrinh) {
		String api = apiUrl + "/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(chuongtrinh , appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<Chuongtrinh> response = restTemplate.exchange(requestEntity, Chuongtrinh.class);
	    return response.getBody();
	}
	
//	--------------------get và edit----------------------------
	public Chuongtrinh getById(int id) throws Exception {
	    List<Chuongtrinh> chuongtrinhs = GetAll();
	    Optional<Chuongtrinh> optionalChuong = chuongtrinhs.stream()
	            .filter(chuongtrinh -> chuongtrinh.getId() == id)
	            .findFirst();
	    if (optionalChuong.isPresent()) {
	        return optionalChuong.get();
	    } else {
	        throw new Exception("Chuong trinh not found with ID: " + id);
	    }
	}
	
	public Chuongtrinh editChuongtrinh(Chuongtrinh chuongtrinh) {
	    String api = apiUrl + "/edit/" + chuongtrinh.getId();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    RequestEntity<?> requestEntity = new RequestEntity<>(chuongtrinh, appConfig.cookieStore().getHeaders(), HttpMethod.PUT, URI.create(api));
	    ResponseEntity<Chuongtrinh> response = restTemplate.exchange(requestEntity, Chuongtrinh.class);
	    return response.getBody();
	}

//	-----------------------search-----------------------

	public List<Chuongtrinh> SearchCT(String keyword) throws JsonMappingException, JsonProcessingException {
		String api= apiUrl+"/search?keyword="+keyword;
	    RequestEntity<?> requestEntity = new RequestEntity<>( HttpMethod.GET, URI.create(api));
	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
	    String json = response.getBody();
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<Chuongtrinh> listCT = objectMapper.readValue(json, new TypeReference<List<Chuongtrinh>>() {});

		    return listCT;
	}
	
	
}
