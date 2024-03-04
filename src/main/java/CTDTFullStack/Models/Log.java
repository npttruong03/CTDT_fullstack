package CTDTFullStack.Models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
	private int id;

	private String logString;

	private Timestamp createTime;
}
