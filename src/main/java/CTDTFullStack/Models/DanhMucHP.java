package CTDTFullStack.Models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DanhMucHP {
	
	private int id;
	
	private String ma_hoc_phan;
	
	private String ten_hoc_phan;
	
	private int id_loai_hoc_phan;
	
	private int id_dml_hp;
	
	private int so_tin_chi;
	
	private float tc_ly_thuyet;
	
	private float tc_thuc_hanh;
	
	private float tc_do_an;
	
	private boolean hp_cot_loi;
	
	private boolean stt;
	
	private String tg_batdau;

	private String tg_ketthuc;
	
	private String ghi_chu;
}
