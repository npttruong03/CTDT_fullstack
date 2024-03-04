package CTDTFullStack.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ChuyenNganh {
	private Integer id;
	private String ma_chuyen_nganh;
	private String ten_chuyen_nganh;
	private Boolean stt;
	private LinhVuc linhvuc;
	private KhoaQuanLy khoaquanly;
	private Nganh nganh;
}


//

