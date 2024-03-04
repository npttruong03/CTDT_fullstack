package CTDTFullStack.services;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import CTDTFullStack.Models.HPThayThe;
import CTDTFullStack.Models.Log;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class HPTTService {
	private String apiURL = Utils.BASE_URL+"hptt";
	private String apilog = Utils.BASE_URL+"ghiLog";
	private RestTemplate restTemplate = new RestTemplate();
	private AppConfig appConfig;
	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	public HPTTService(AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public List<HPThayThe> getDataFromApi() throws Exception{

//		RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create(apiURL));
//
	    RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(apiURL));
//
//	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
//	    String json = response.getBody();
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    List<DanhMucHP> list = objectMapper.readValue(json, new TypeReference<List<DanhMucHP>>() {});
		
		 ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
		    String json = response.getBody();
		    ObjectMapper objectMapper = new ObjectMapper();
		    List<HPThayThe> list = objectMapper.readValue(json, new TypeReference<List<HPThayThe>>() {});
	return list;
	
	}
	public List<HPThayThe> getDataStt()throws Exception{
		ArrayList<HPThayThe> list = new ArrayList<>();
		for (HPThayThe hptt : getDataFromApi()) {
			if(hptt.isStt()==true)
				list.add(hptt);
		}
		return list;
	}
	
	public HPThayThe getByid(int id) throws Exception {
		HPThayThe hptt = new HPThayThe();
		for( HPThayThe HP : getDataFromApi()) {
			if(HP.getId() == id) {
				hptt = HP;
				break;
			}
		}
		return hptt;
	}
	
	public List<HPThayThe>search(String key) throws JsonMappingException, JsonProcessingException{
		String api=apiURL+"/search?key="+key;
//		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(api));
//	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
//	    String json = response.getBody();
//		
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    List<DanhMucHP> list = objectMapper.readValue(json, new TypeReference<List<DanhMucHP>>() {});
		ResponseEntity<String> response = restTemplate.getForEntity(apiURL, String.class);
	    String json = response.getBody();
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<HPThayThe> list = objectMapper.readValue(json, new TypeReference<List<HPThayThe>>() {});
	    return list;
	}
	
	public HPThayThe post(HPThayThe hptt){
		Log loghptt = new Log();
		loghptt.setLogString(hptt.toString());
		loghptt.setCreateTime(new Timestamp(System.currentTimeMillis()));
		String api = apiURL+"/create";
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?>requestEntity = new RequestEntity<>(hptt, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<HPThayThe>response = restTemplate.exchange(requestEntity, HPThayThe.class);
//		ResponseEntity<HPThayThe> response = restTemplate.postForEntity(api, hptt, HPThayThe.class);
		ResponseEntity<Boolean> responseLog = restTemplate.postForEntity(ghiLog, loghptt, Boolean.class);
		return response.getBody();
	}
	
	public HPThayThe put(HPThayThe hptt, int id){
		String api = apiURL+"/edit/"+id;
		String ghiLog = apilog+"/create";
		Log loghptt = new Log();
		loghptt.setLogString(hptt.toString());
		loghptt.setCreateTime(new Timestamp(System.currentTimeMillis()));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<HPThayThe>requesEntity = new HttpEntity<>(hptt, appConfig.cookieStore().getHeaders());
		ResponseEntity<HPThayThe>responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requesEntity, HPThayThe.class);
		ResponseEntity<Boolean> responseLog = restTemplate.postForEntity(ghiLog, loghptt, Boolean.class);

//		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<HPThayThe> requestEntity = new HttpEntity<>(hptt, headers);
//        ResponseEntity<HPThayThe> responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requestEntity, HPThayThe.class);
		return responseEntity.getBody();
	}
}
