package uz.pdp.YandexTranslate.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExItem{

	@SerializedName("text")
	private String text;

	@SerializedName("tr")
	private List<TrItem> tr;
}