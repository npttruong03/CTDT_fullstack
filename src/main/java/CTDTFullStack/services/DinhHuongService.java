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
import CTDTFullStack.Models.DinhHuong;
import CTDTFullStack.Models.Log;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class DinhHuongService {
	private String apiURL = Utils.BASE_URL+"dinhhuong";
	private String apilog = Utils.BASE_URL+"ghiLog";
	private RestTemplate restTemplate = new RestTemplate();
	private AppConfig appConfig;
	private HttpHeaders headers = new HttpHeaders();
	@Autowired
	public DinhHuongService(AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}
	
	public List<DinhHuong> getDataFromApi() throws Exception{

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
		    List<DinhHuong> list = objectMapper.readValue(json, new TypeReference<List<DinhHuong>>() {});
	return list;
	
	}
	
	
	public DinhHuong getByid(int id) throws Exception {
		DinhHuong dmhp = new DinhHuong();
		for( DinhHuong dMucHP : getDataFromApi()) {
			if(dMucHP.getId() == id) {
				dmhp = dMucHP;
				break;
			}
		}
		return dmhp;
	}
	

	
	public DinhHuong post(DinhHuong dmhp){
		Log logdmhp = new Log();
		logdmhp.setLogString(dmhp.toString());
		logdmhp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		String api = apiURL+"/create";
		String ghiLog = apilog+"/create";
		headers.setContentType(MediaType.APPLICATION_JSON);
		RequestEntity<?>requestEntity = new RequestEntity<>(dmhp, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
		ResponseEntity<DinhHuong>response = restTemplate.exchange(requestEntity, DinhHuong.class);
//		ResponseEntity<DinhHuong> response = restTemplate.postForEntity(api, dmhp, DinhHuong.class);
		
		ResponseEntity<Boolean> logEntity = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);
	
//		ResponseEntity<DanhMucHP> responseLog = restTemplate.postForEntity(ghiLog, dmhp, Log.class);
		return response.getBody();
	}
	
	public DinhHuong put(DinhHuong dmhp, int id){
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
		HttpEntity<DinhHuong>requesEntity = new HttpEntity<>(dmhp, appConfig.cookieStore().getHeaders());
		ResponseEntity<DinhHuong>responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requesEntity, DinhHuong.class);
		
//		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<DinhHuong> requestEntity = new HttpEntity<>(dmhp, headers);
//        ResponseEntity<DinhHuong> responseEntity = restTemplate.exchange(api, HttpMethod.PUT, requestEntity, DinhHuong.class);
        System.out.println(dmhp);
        //log
        ResponseEntity<Boolean> logEntity = restTemplate.postForEntity(ghiLog, logdmhp, Boolean.class);
		return responseEntity.getBody();
	}
}
