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
import CTDTFullStack.Models.Nganh;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class NganhService {
	private AppConfig appConfig;
	@Autowired
	public NganhService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
	private String apiUrl = Utils.BASE_URL + "nganh";
	private HttpHeaders headers = new HttpHeaders();

	public List<Nganh> GetAll() throws JsonMappingException, JsonProcessingException{
		
		
		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(apiUrl));
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
	    String json = response.getBody();
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 List<Nganh> listNganh = objectMapper.readValue(json,new TypeReference<List<Nganh>>() {});

		    return listNganh;
		
		
//		 String apiResponse = restTemplate.getForObject(apiUrl, String.class);
//	        List<Nganh> nganh = null;
//	        try {
//	            nganh = objectMapper.readValue(apiResponse, new TypeReference<List<Nganh>>() {});
//	        } catch (Exception e) {
//	        	
//	        }
//	        return nganh;
	    }
	
	public Nganh post(Nganh nganh) {
		String api = apiUrl + "/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(nganh ,appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<Nganh> response = restTemplate.exchange(requestEntity, Nganh.class);
	    return response.getBody();
	}
	
//	--------------------get và edit----------------------------
	public Nganh getById(int id) throws Exception {
	    List<Nganh> nganhs = GetAll();
	    Optional<Nganh> optionalNganh = nganhs.stream()
	            .filter(nganh -> nganh.getId() == id)
	            .findFirst();
	    if (optionalNganh.isPresent()) {
	        return optionalNganh.get();
	    } else {
	        throw new Exception("Nganh not found with ID: " + id);
	    }
	}

	
	public Nganh editNganh(Nganh nganh) {

		String api = apiUrl + "/edit/"+ nganh.getId();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(nganh ,appConfig.cookieStore().getHeaders(), HttpMethod.PUT, URI.create(api));
		ResponseEntity<Nganh> response = restTemplate.exchange(requestEntity, Nganh.class);
		return response.getBody();
	}
	
//	------------search---------------
	public List<Nganh> SearchNganh(String keyword) throws JsonMappingException, JsonProcessingException {
		String api= apiUrl+"/search?keyword="+keyword;
	    RequestEntity<?> requestEntity = new RequestEntity<>( HttpMethod.GET, URI.create(api));
	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
	    String json = response.getBody();
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<Nganh> listNganh = objectMapper.readValue(json, new TypeReference<List<Nganh>>() {});

		    return listNganh;
	}
	
	
	
}