package interface_adapter.add_to_cart;

public class AddToCartState {

    private String message;
    private String quantityError;

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public String getQuantityError() {return quantityError;}
    public void setQuantityError(String quantityError) {this.quantityError = quantityError;}
}
