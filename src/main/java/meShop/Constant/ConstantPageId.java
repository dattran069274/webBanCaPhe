package meShop.Constant;

public class ConstantPageId {
	public static final int CHECKOUT_PAGE=1;
	public static final int PRODUCT_PAGE=2;
	public static final int CART_PAGE=3;
	public static final int HISTORYBUY_PAGE=4;
	public static final int HOME_PAGE=5;
	public static final int CHAT_PAGE = 6;
	public static final int CAPHE_HAT = 17;
	public static final int CAPHE_PHIN = 16;
	public static final int CAPHE_HOATAN =11;
	public static final int CATEGORY_ADMIN_PAGE = 7;
	public static final int PRODUCT_ADMIN_PAGE = 8;
	public static final int INSERT_PRODUCT_ADMIN_PAGE = 9;
	public static final int ORDER_ADMIN_PAGE = 10;
	public static final int ACCOUNT_PAGE=11;
	public static final int ORDER_ACCEPTED_SHIPPER_PAGE=12;
	public static final int ORDER_DELIVERI_SHIPPER_PAGE=13;
	public static String getPageUrl(int pgaeId) {
		switch (pgaeId) {
		case CHECKOUT_PAGE:
			return "shopping-cart/checkout";
		case PRODUCT_PAGE:
		return "product/views";
		case CART_PAGE:
			return "shopping-cart/views";
		case HISTORYBUY_PAGE:
			return "history/views";
		case HOME_PAGE:
			return "";
		case CATEGORY_ADMIN_PAGE:
			return "admin/categorys/views";
		case PRODUCT_ADMIN_PAGE:
			return "admin/products/views";
		case INSERT_PRODUCT_ADMIN_PAGE:
			return "admin/products/insert";
		case ORDER_ADMIN_PAGE:
			return "admin/orders/views";
			case ACCOUNT_PAGE:
			return "account/view/";	
		case ORDER_ACCEPTED_SHIPPER_PAGE:
			return "shipper/views";
		case ORDER_DELIVERI_SHIPPER_PAGE:
			return "shipper/views?page=me";
		case CHAT_PAGE:
			return "real";
		default:
			return "";
		}
	}
	public static String getPageUrlIdBelow(int pgaeId,int id) {
		switch (pgaeId) {
		case CHECKOUT_PAGE:
			return "shopping-cart/checkout";
		case PRODUCT_PAGE:
		return "product/views";
		case CART_PAGE:
			return "shopping-cart/views";
		case HISTORYBUY_PAGE:
			return "history/views";
		case HOME_PAGE:
			return "";
		case CATEGORY_ADMIN_PAGE:
			return "admin/categorys/views";
		case PRODUCT_ADMIN_PAGE:
			return "admin/products/views";
		case INSERT_PRODUCT_ADMIN_PAGE:
			return "admin/products/insert";
		case ORDER_ADMIN_PAGE:
			return "admin/orders/views";
			case ACCOUNT_PAGE:
			return "account/view/"+id;	
		case CHAT_PAGE:
			return "real";
		default:
			return "";
		}
	}
	public static int getIdFromPageUrl(String  page) {
		switch (page) {
		case "shopping-cart/checkout":
			return CHECKOUT_PAGE;
		case "product/views":
		return PRODUCT_PAGE;
		case "shopping-cart/views":
			return CART_PAGE;
		case "historyIndex":
			return HISTORYBUY_PAGE;
		case "":
			return HOME_PAGE;
		case "real":
			return CHAT_PAGE;
		case "admin/categorys/views":
			return CATEGORY_ADMIN_PAGE;
		case "admin/products/views":
			return PRODUCT_ADMIN_PAGE;
		case "admin/products/insert":
			return INSERT_PRODUCT_ADMIN_PAGE;
		case "admin/orders/views":
			return ORDER_ADMIN_PAGE;
			case "shipper/views":
			return ORDER_ACCEPTED_SHIPPER_PAGE;
			case "shipper/views?page=me":
			return ORDER_DELIVERI_SHIPPER_PAGE;
		default:
			return HOME_PAGE;
		}
	}
}
