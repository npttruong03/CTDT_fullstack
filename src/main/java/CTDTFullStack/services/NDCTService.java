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

import CTDTFullStack.Models.Log;
import CTDTFullStack.Models.NDCT;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;
@Service
public class NDCTService {
	private String apiURL = Utils.BASE_URL+"ndct";
	private String apilog = Utils.BASE_URL+"ghiLog";
	private RestTemplate restTemplate = new RestTemplate();
	private AppConfig appConfig;
	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	public NDCTService(AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public List<NDCT> getDataFromApi() throws Exception{

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
		    List<NDCT> list = objectMapper.readValue(json, new TypeReference<List<NDCT>>() {});
	return list;
	
	}
	public List<NDCT> getDataStt()throws Exception{
		ArrayList<NDCT> list = new ArrayList<>();
		for (NDCT dmhp : getDataFromApi()) {
			if(dmhp.isStt()==true)
				list.add(dmhp);
		}
		return list;
	}
	
	public NDCT getByid(int id) throws Exception {
		NDCT dmhp = new NDCT();
		for( NDCT dMucHP : getDataFromApi()) {
			if(dMucHP.getId() == id) {
				dmhp = dMucHP;
				break;
			}
		}
		return dmhp;
	}
	
//	public List<DanhMucHP>search(String key) throws JsonMappingException, JsonProcessingException{
//		String api=apiURL+"/search?key="+key;
////		RequestEntity<?> requestEntity = new RequestEntity<>(appConfig.cookieStore().getHeaders(), HttpMethod.GET, URI.create(api));
////	    ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
////	    String json = response.getBody();
////		
////	    ObjectMapper objectMapper = new ObjectMapper();
////	    List<DanhMucHP> list = objectMapper.readValue(json, new TypeReference<List<DanhMucHP>>() {});
//		ResponseEntity<String> response = restTemplate.getForEntity(apiURL, String.class);
//	    String json = response.getBody();
//	    ObjectMapper objectMapper = new ObjectMapper();
//	    List<DanhMucHP> list = objectMapper.readValue(json, new TypeReference<List<DanhMucHP>>() {});
//	    return list;
//	}
	
	public NDCT post(NDCT dmhp){
		Log logdmhp = new Log();
		logdmhp.setLogString(dmhp.toString());
		logdmhp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		System.out.println(logdmhp);
		String api = apiURL+"/create";
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?>requestEntity = new RequestEntity<>(dmhp, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<NDCT>response = restTemplate.exchange(requestEntity, NDCT.class);
//		ResponseEntity<NDCT> response = restTemplate.postForEntity(api, dmhp, NDCT.class);
		
		ResponseEntity<Boolean> logEntity = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);
	
//		ResponseEntity<DanhMucHP> responseLog = restTemplate.postForEntity(ghiLog, dmhp, Log.class);
		return response.getBody();
	}
	
	public NDCT put(NDCT dmhp, int id){
		Log logdmhp = new Log();
//		System.out.println(dmhp.toString());
//		String dmhpString = dmhp.getId()+";"+dmhp.getId_loai_hoc_phan()+";"+ dmhp.getMa_hoc_phan()+";"+dmhp.getSo_tin_chi()+";"+
//				 dmhp.getTc_do_an()+";"+dmhp.getTc_ly_thuyet()+";"+ dmhp.getTc_thuc_hanh()+";"+dmhp.getTen_hoc_phan()+";"+
//				 dmhp.getTg_batdau()+";"+dmhp.getTg_ketthuc()+";"+ dmhp.getGhi_chu()+".";
		logdmhp.setLogString(dmhp.toString());
		logdmhp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		String api = apiURL+"/edit/"+id;
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<NDCT>requesEntity = new HttpEntity<>(dmhp, appConfig.cookieStore().getHeaders());
		ResponseEntity<NDCT>responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requesEntity, NDCT.class);
		
//		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<NDCT> requestEntity = new HttpEntity<>(dmhp, headers);
//        ResponseEntity<NDCT> responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requestEntity, NDCT.class);
        
        //log
        ResponseEntity<Boolean> logEntity = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);
		return responseEntity.getBody();
	}
}
