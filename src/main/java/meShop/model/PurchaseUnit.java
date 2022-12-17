package meShop.model;

import lombok.Data;

@Data
public class PurchaseUnit {
    private MoneyDTO amount;

	public MoneyDTO getAmount() {
		return amount;
	}

	public void setAmount(MoneyDTO amount) {
		this.amount = amount;
	}
    
}
