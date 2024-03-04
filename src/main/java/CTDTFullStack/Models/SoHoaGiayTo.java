package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoHoaGiayTo {
	private int id;
	
	private String ten_giay_to;
	
	private String phan_can_cu;
	
	private String phan_noi_dung;
	
	private String chiu_trach_nhiem;
	
	private boolean status;
}
