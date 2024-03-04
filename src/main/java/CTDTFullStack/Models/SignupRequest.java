package CTDTFullStack.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@JsonProperty("username")
	  private String username;
		
		@JsonProperty("email")
	    private String email; 
		
	    private List<String> role = new ArrayList<>();
	    
	    @JsonProperty("password")
	    private String password;
}
