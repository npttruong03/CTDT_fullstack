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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CTDTFullStack.Models.KhoaQuanLy;
import CTDTFullStack.Models.LinhVuc;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class KhoaQuanlyService {
	private AppConfig appConfig;
	@Autowired
	public KhoaQuanlyService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	private String apiURL = Utils.BASE_URL + "khoaquanly";
	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	
	public List<KhoaQuanLy> getDataFromAPI() throws JsonMappingException, JsonProcessingException {
		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(),HttpMethod.GET,
				URI.create(apiURL));
		 ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		    String json = response.getBody();
		    
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<KhoaQuanLy> listEthnic = objectMapper.readValue(json, new TypeReference<List<KhoaQuanLy>>() {});

		    return listEthnic;
	}
	
	public String creat(KhoaQuanLy khoaQuanLy) {
		String apiurl = apiURL + "/add";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<KhoaQuanLy> requestEntity = new HttpEntity<>(khoaQuanLy,  appConfig.cookieStore().getHeaders());
		ResponseEntity<String> response = restTemplate.exchange(apiurl, HttpMethod.POST, requestEntity, String.class);
		return response.getBody();
	}
	
	public ResponseEntity<String> editKhoaquanly(KhoaQuanLy khoaQuanLy, int id) {
		String urlapi = apiURL + "/update/" + id;
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<KhoaQuanLy> requestEntity = new HttpEntity<>(khoaQuanLy, appConfig.cookieStore().getHeaders());
		ResponseEntity<String> responseEntity = restTemplate.exchange(urlapi, HttpMethod.PUT, requestEntity, String.class);
		return responseEntity;
	}
	
	public KhoaQuanLy getByID(int id) throws Exception {
		for(KhoaQuanLy khoaQuanLy : getDataFromAPI()) {
			if(khoaQuanLy.getId() == id) {
				return khoaQuanLy;
			}
		}
		return null;
	}
	
	public KhoaQuanLy getKhoaQuanLyById(int id) {
	    String url = apiURL + "/" + id;
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
	    String json = response.getBody();
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        KhoaQuanLy khoaQuanLy = objectMapper.readValue(json, KhoaQuanLy.class);
	        return khoaQuanLy;
	    } catch (JsonProcessingException e) {
	        // Handle exception (e.g., log it or throw a custom exception)
	        e.printStackTrace();
	        return null;
	    }
	}
}
