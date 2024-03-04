package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NDCT {
	private int id;

	private String ma_hoc_phan;

	private int id_ktt;

	private int id_danh_muc_lhp;

	private int id_dinh_huong;

	private int id_mqh;

	private int id_hptt;

	private int id_chuongtrinh;

	private int hoc_ky_du_kien;

	private boolean stt;
}
