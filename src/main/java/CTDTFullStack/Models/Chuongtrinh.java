package CTDTFullStack.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Chuongtrinh {
	private Integer id;
	private Integer id_chuyen_nganh;
	private String ma_chuong_trinh;
	private int id_nganh;
	private Integer nam_ap_dung;
	private Integer nam_het_han;
	private String khoa_ap_dung;
	private Boolean stt;
	private String ten_chuong_trinh;
	private int tong_so_tin_chi;
	
	private ChuyenNganh chuyenNganh;
	private Nganh nganh;
	private KKT khoiKienThuc;
	
}
