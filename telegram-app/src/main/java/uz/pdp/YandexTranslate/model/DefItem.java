package uz.pdp.YandexTranslate.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefItem{

	@SerializedName("pos")
	private String pos;

	@SerializedName("text")
	private String text;

	@SerializedName("tr")
	private List<TrItem> tr;

	@SerializedName("ts")
	private String ts;
}