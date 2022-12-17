package meShop.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDTO implements Serializable {
	
    private OrderIntent intent;
    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;
    @JsonProperty("application_context")
    private PayPalAppContextDTO applicationContext;
	public OrderIntent getIntent() {
		return intent;
	}
	public void setIntent(OrderIntent intent) {
		this.intent = intent;
	}
	public List<PurchaseUnit> getPurchaseUnits() {
		return purchaseUnits;
	}
	public void setPurchaseUnits(List<PurchaseUnit> purchaseUnits) {
		this.purchaseUnits = purchaseUnits;
	}
	public PayPalAppContextDTO getApplicationContext() {
		return applicationContext;
	}
	public void setApplicationContext(PayPalAppContextDTO applicationContext) {
		this.applicationContext = applicationContext;
	}
	@Override
	public String toString() {
		return "OrderDTO [intent=" + intent + ", purchaseUnits=" + purchaseUnits + ", applicationContext="
				+ applicationContext + "]";
	}
    
}
