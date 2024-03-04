package CTDTFullStack.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nganh {
	private Integer id;
	private String ma_nganh;
	private Boolean stt;
	private String ten_nganh;
	private List<ChuyenNganh> chuyenNganh;
	private LinhVuc linhvuc;
	private KhoaQuanLy khoaquanly;
}
