package CTDTFullStack.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoiQuanHeHP {
	private int id;
	private	String ma_hoc_phan;
	private String hoc_truoc;
	private String tien_quyet;
	private String song_hanh;
	private boolean stt;
}
