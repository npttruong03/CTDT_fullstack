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

import CTDTFullStack.Models.SinhVien;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;


@Service
public class SinhVienService {
//	String apiURL = "http://222.255.130.100:7070/ctdt/api/sinhvien";
	String apiURL =  Utils.BASE_URL + "sinhvien";
	RestTemplate restTemplate = new RestTemplate();
	private AppConfig appConfig;
	private HttpHeaders headers = new HttpHeaders();

	@Autowired
	public SinhVienService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	public List<SinhVien> quanLySinhVien() throws JsonMappingException, JsonProcessingException {
		 RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(),HttpMethod.GET, URI.create(apiURL + "/getAll"));
		 ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		 String json = response.getBody();
		 ObjectMapper objectMapper = new ObjectMapper();
		 List<SinhVien> listSV = objectMapper.readValue(json, new TypeReference<List<SinhVien>>() {});

		 return listSV;
	}
	
	public Boolean themSinhVien(SinhVien sv) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(sv,appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(apiURL + "/create"));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		Boolean rs = response.getBody();
		return rs;
	}
	
	public SinhVien laySinhVien(Integer id) throws JsonMappingException, JsonProcessingException {
		RequestEntity<?> requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(apiURL + "/get/" + id));
		ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		String json = response.getBody();
		ObjectMapper objectMapper = new ObjectMapper();
		SinhVien lhp = objectMapper.readValue(json, new TypeReference<SinhVien>() {});

		return lhp;
	}
	
	public Boolean capnhatSinhVien(SinhVien sv) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?> requestEntity = new RequestEntity<>(sv,appConfig.cookieStore().getHeaders(), HttpMethod.PUT, URI.create(apiURL + "/update"));
		ResponseEntity<Boolean> response = restTemplate.exchange(requestEntity, Boolean.class);
		Boolean rs = response.getBody();
		return rs;
	}
}
