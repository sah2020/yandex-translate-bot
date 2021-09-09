package uz.pdp.YandexTranslate.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrItem{

	@SerializedName("pos")
	private String pos;

	@SerializedName("mean")
	private List<MeanItem> mean;

	@SerializedName("text")
	private String text;
}