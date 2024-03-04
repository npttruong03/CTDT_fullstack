package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinhVienLopHocPhan {
	private int id;
	
	private String maSV;
	
	private int lhp;
	
	private Double diem10;
	
	private Character diem4;
}
