package CTDTFullStack.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinhVuc{
	 	private Integer id;
	 	
	    private String ma_linh_vuc;
	
		private String ten_linh_vuc;

	    private boolean stt;
}