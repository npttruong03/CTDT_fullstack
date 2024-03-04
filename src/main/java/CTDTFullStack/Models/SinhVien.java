package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinhVien {
	private int id;
	private String maSV;
	
	private String hoTen;
	
	private String khoa;
	
	private Integer nganh;
	
	private Integer chuyenNganh;
	
	private Integer hocKyHienTai;
}
