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

import CTDTFullStack.Models.DanhMucHP;
import CTDTFullStack.Models.KKT;
import CTDTFullStack.Models.Log;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class KKTService {
	private String apiURL = Utils.BASE_URL+"kkt";
	private String apilog = Utils.BASE_URL+"ghiLog";
	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();

	private AppConfig appConfig;
	@Autowired
	public KKTService(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
	public List<KKT> getDataFromApi() throws Exception{

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
		    List<KKT> list = objectMapper.readValue(json, new TypeReference<List<KKT>>() {});
	return list;
	
	}

	
	public KKT getByid(int id) throws Exception {
		KKT kkt = new KKT();
		for( KKT khoikt : getDataFromApi()) {
			if(khoikt.getId() == id) {
				kkt = khoikt;
				break;
			}
		}
		return kkt;
	}
	
	public List<KKT>search(String key) throws JsonMappingException, JsonProcessingException{
		String api=apiURL+"/search?name="+key;
//		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(api));
//	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
//	    String json = response.getBody();
//		
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    List<DanhMucHP> list = objectMapper.readValue(json, new TypeReference<List<DanhMucHP>>() {});
		ResponseEntity<String> response = restTemplate.getForEntity(apiURL, String.class);
	    String json = response.getBody();
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<KKT> list = objectMapper.readValue(json, new TypeReference<List<KKT>>() {});
	    return list;
	}
	
	public KKT post(KKT kkt){
		Log logdmhp = new Log();
		logdmhp.setLogString(kkt.toString());
		logdmhp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		String api = apiURL+"/create";
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?>requestEntity = new RequestEntity<>(kkt, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<KKT>response = restTemplate.exchange(requestEntity, KKT.class);
//		ResponseEntity<KKT> response = restTemplate.postForEntity(api, kkt, KKT.class);
		ResponseEntity<Boolean> responseLog = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);
		return response.getBody();
	}
	
	public KKT put(KKT kkt, int id){
		Log logdmhp = new Log();
		logdmhp.setLogString(kkt.toString());
		logdmhp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		String api = apiURL+"/edit/"+id;
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<KKT>requesEntity = new HttpEntity<>(kkt, appConfig.cookieStore().getHeaders());
		ResponseEntity<KKT>responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requesEntity, KKT.class);
		ResponseEntity<Boolean> responseLog = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);

//		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<KKT> requestEntity = new HttpEntity<>(kkt, headers);
//        ResponseEntity<KKT> responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requestEntity, KKT.class);
		return responseEntity.getBody();
	}

}

