package use_case.daily_deals;

/**
 * Output Data for the Add to Cart Use Case.
 */
public class AddToCartOutputData {

    private String username;
    private Integer cartTotal;

    public AddToCartOutputData(String username, Integer cartTotal) {
        this.username = username;
        this.cartTotal = cartTotal;
    }

    public String getUsername() {
        return username;
        }
        public Integer getCartTotal() {
        return cartTotal;
        }
}
