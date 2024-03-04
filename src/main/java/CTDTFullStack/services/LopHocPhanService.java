package CTDTFullStack.services;

import java.net.URI;
import java.util.List;

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

import CTDTFullStack.Models.LopHocPhan;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;


@Service
public class LopHocPhanService {
//	String apiURL = "http://222.255.130.100:7070/ctdt/api/lophocphan";
	String apiURL =  Utils.BASE_URL + "lophocphan";
	RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();

	private AppConfig appConfig;
	@Autowired
	public LopHocPhanService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	public List<LopHocPhan> quanLyLopHocPhan() throws JsonMappingException, JsonProcessingException {
		 RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(),HttpMethod.GET, URI.create(apiURL + "/getAll"));
		 ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		 String json = response.getBody();
		 ObjectMapper objectMapper = new ObjectMapper();
		 List<LopHocPhan> listSV = objectMapper.readValue(json, new TypeReference<List<LopHocPhan>>() {});

		 return listSV;
	}
	
	public Boolean themLopHocPhan(LopHocPhan lhp) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(lhp, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(apiURL + "/create"));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		Boolean rs = response.getBody();
		return rs;
	}
	
	public LopHocPhan layLopHocPhan(Integer id) throws JsonMappingException, JsonProcessingException {
		RequestEntity<?> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(apiURL + "/get/" + id));
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		String json = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		LopHocPhan lhp = objectMapper.readValue(json, new TypeReference<LopHocPhan>() {});

		return lhp;
	}
	
	public Boolean capnhatLopHocPhan(LopHocPhan lhp) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(lhp, appConfig.cookieStore().getHeaders(), HttpMethod.PUT, URI.create(apiURL + "/update"));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		Boolean rs = response.getBody();
		return rs;
	}
}
