package CTDTFullStack.services;

import java.net.URI;
import java.util.Arrays;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.Models.SinhVien;
import CTDTFullStack.Models.SinhVienLopHocPhan;
import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;



@Service
public class LinhVucService {
	private String apiURL = Utils.BASE_URL + "field";
	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	private AppConfig appConfig;
	
	@Autowired
	public LinhVucService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	
	public List<LinhVuc> getDataFromAPI() throws JsonMappingException, JsonProcessingException {
		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders() ,HttpMethod.GET,
				URI.create(apiURL));
		 ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		    String json = response.getBody();
		    
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<LinhVuc> listEthnic = objectMapper.readValue(json, new TypeReference<List<LinhVuc>>() {});

		    return listEthnic;
	}
	
	
	public List<LinhVuc> searchDataFromAPI(String keyword) throws JsonMappingException, JsonProcessingException {
		String api= apiURL+"/search?keyword="+keyword;
	    RequestEntity<?> requestEntity = new RequestEntity<>( HttpMethod.GET, URI.create(api));
	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
	    String json = response.getBody();
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<LinhVuc> listLinhVuc = objectMapper.readValue(json, new TypeReference<List<LinhVuc>>() {});

		    return listLinhVuc;
	}
	
	
	
	
	public Boolean post(LinhVuc linhvuc) {
		String api = apiURL + "/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(linhvuc , appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
	    Boolean rs = response.getBody();
		return rs;
	}
	

	
	
	
//	--------------------get và edit----------------------------
	public LinhVuc getById(int id) throws Exception {
	    List<LinhVuc> linhvucs = getDataFromAPI();
	    Optional<LinhVuc> optionalLinhVuc = linhvucs.stream()
	            .filter(religion -> religion.getId() == id)
	            .findFirst();
	    if (optionalLinhVuc.isPresent()) {
	        return optionalLinhVuc.get();
	    } else {
	        throw new Exception("LinhVuc not found with ID: " + id);
	    }
	}

	
	public Boolean editLinhVuc(LinhVuc linhvuc) {

		String api = apiURL + "/edit/"+ linhvuc.getId();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(linhvuc , appConfig.cookieStore().getHeaders(), HttpMethod.PUT, URI.create(api));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		Boolean rs = response.getBody();
		return  rs;
	}
	
	
	
}
