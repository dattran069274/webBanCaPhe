package meShop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoneyDTO {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    public MoneyDTO(){

	}
	public MoneyDTO(String a,String b){
		this.currencyCode = a;
		this.value = b;
	}
	
}