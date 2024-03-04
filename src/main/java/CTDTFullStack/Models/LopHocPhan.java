package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LopHocPhan {
	private int id;
	private String maLop;
	
	private String hocPhan;
	
	private String giangVien;

	private Integer hocKi;
	
	private String namHoc;
}
