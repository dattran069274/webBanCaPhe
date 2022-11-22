package meShop.Constant;

public class ConstantPageId {
	public static final int CHECKOUT_PAGE=1;
	public static final int PRODUCT_PAGE=2;
	public static final int CART_PAGE=3;
	public static final int HISTORYBUY_PAGE=4;
	public static final int HOME_PAGE=5;
	public static final int CHAT_PAGE = 6;
	public static String getPageUrl(int pgaeId) {
		switch (pgaeId) {
		case CHECKOUT_PAGE:
			return "shopping-cart/checkout";
		case PRODUCT_PAGE:
		return "productIndex";
		case CART_PAGE:
			return "shopping-cart/views";
		case HISTORYBUY_PAGE:
			return "historyIndex";
		case HOME_PAGE:
			return "";
		case CHAT_PAGE:
			return "real";
		default:
			return "";
		}
	}
}
