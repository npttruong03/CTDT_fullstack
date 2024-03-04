package CTDTFullStack.services;

import java.net.URI;
import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import CTDTFullStack.Models.LoginRequest;
import CTDTFullStack.Models.SignupRequest;
import CTDTFullStack.Utils.Utils;
import CTDTFullStack.config.AppConfig;

@Service
public class LoginService {
	private Message message;
	private AppConfig appConfig;
    @Autowired
    public LoginService(AppConfig appConfig)
    {
    	this.appConfig = appConfig;
    }
	private final static String apiURL = Utils.BASE_URL + "auth";
	public String loginAndGetToken(String username, String password) throws Exception {

//   	 String loginUrl = "http://localhost:2221/api/auth/signin"; // Thay đổi thành URL của API đăng nhập
       String loginUrl = apiURL + "/signin";

   	LoginRequest loginRequest = new LoginRequest();


//   	 String hashedPassword = passwordEncoder.encode(password);
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        
        // Tạo các thông tin đăng nhập gửi đến server
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Tạo HttpEntity chứa headers
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest,headers);
        // Tạo RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Gọi API đăng nhập
        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {

            String json = response.getBody(); 
            ObjectMapper objectMapper = new ObjectMapper();
            LoggedInfo loggedInfo = objectMapper.readValue(json, LoginService.LoggedInfo.class);
            appConfig.cookieStore().setCookie(loggedInfo.getToken());
//
////            appConfig.cookieStore().setRoles(loggedInfo.getRole());
//
            appConfig.cookieStore().setRoles(loggedInfo.getRole());
            appConfig.cookieStore().setUsername(loggedInfo.getUsername());
            return "OK";
        } else {
            throw new RuntimeException("Failed to login. Status code: " + response.getStatusCodeValue());
        }
    }
   
   
   public String signupAndGetToken(String username, String password, String email, List<String> role) throws Exception {

//  	 String loginUrl = "http://localhost:2221/api/auth/signup"; // Thay đổi thành URL của API đăng nhập
      String loginUrl = apiURL + "/signup";

      SignupRequest signupRequest = new SignupRequest();
       signupRequest.setUsername(username);
       signupRequest.setPassword(password);
       signupRequest.setEmail(email);
       signupRequest.setRole(role);
       
       // Tạo các thông tin đăng nhập gửi đến server
       HttpHeaders headers = new HttpHeaders();
//       headers.setContentType(MediaType.APPLICATION_JSON);

       // Tạo HttpEntity chứa headers
//       HttpEntity<SignupRequest> entity = new HttpEntity<>(signupRequest,headers);
//       System.out.println("dmm" + entity);
       
       // Tạo RestTemplate
       RestTemplate restTemplate = new RestTemplate();
       
       
//       RequestEntity<?> requestEntity = new RequestEntity<>(signupRequest, appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(loginUrl));
       RequestEntity<?> requestEntity = new RequestEntity<>(signupRequest, HttpMethod.POST, URI.create(loginUrl));
       // Gọi API đăng nhập
   	ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);   
       String json = response.getBody();
       return json;
           
   }
   

   public String logout() {
//   	String api = "http:http://222.255.130.100:7070/ctdt/api/auth/signout";
   	String api = apiURL + "/signout";
   	
   	RestTemplate restTemplate = new RestTemplate();
//   	RequestEntity<?> requestEntity = new RequestEntity(appConfig.cookieStore().getHeaders(), HttpMethod.POST, URI.create(api));
   	RequestEntity<?> requestEntity = new RequestEntity<>(String.class, HttpMethod.POST, URI.create(api)); 
   	ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
   	if (responseEntity.getBody().equals("Logout OK"))
   	{
       	appConfig.cookieStore().setCookie("");
       	return "OK";
   	}
   	return "Logout fail";
   }
   
//   public String checkToken() {
//   	String token = appConfig.cookieStore().getCookie();
//   	System.out.println("get "+ token);
//   	return token;
//   }
//
//   public String addUsernameAnhRole() {
//   	String token = appConfig.cookieStore().getCookie();
//   	System.out.println("lay " + token);
//   	return token;
//   }
   
   static class LoggedInfo {
		  String token;
		  List<String> role;
		  String username;
		
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public List<String> getRole() {
			return role;
		}
		public void setRole(List<String> roles) {
			this.role = roles;
		}
		
		public String getUsername() {
			return username;
		}
		
		public void setUsername(String username) {
			this.username = username;
		}
 }
}
