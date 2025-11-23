package use_case.checkout;

import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutInteractor implements CheckoutInputBoundary {
    private final CheckoutDataAccessInterface dataAccess;
    private final CheckoutOutputBoundary outputBoundary;

    public CheckoutInteractor(CheckoutDataAccessInterface dataAccess,
                              CheckoutOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CheckoutInputData inputData) {
        try {
            //Get user and cart data
            User user = dataAccess.getUser(inputData.getUserId());
            Cart cart = user.getCart();

            //Get default billing address
            String billingAddress = getDefaultBillingAddress(user);

            //Prepare cart items for display
            List<CartItemDisplay> cartItemDisplays = prepareCartItemDisplays(cart);

            //Calculate totals
            double totalPrice = calculateTotalPrice(cart);
            int totalItems = cart.getTotalQuantity();

            // 5. Create output data and present
            CheckoutOutputData outputData = new CheckoutOutputData(
                    user.getUsername(),
                    user.getEmail(),
                    billingAddress,
                    cartItemDisplays,
                    totalPrice,
                    totalItems
            );

            outputBoundary.presentOrderConfirmation(outputData);

        } catch (Exception e) {
            outputBoundary.presentCheckoutError("Checkout failed: " + e.getMessage());
        }
    }

    private String getDefaultBillingAddress(User user) {
        for (Address address : user.getBillingAddresses()) {
            if (address.isDefaultBilling()) {
                return address.toSingleLine();
            }
        }
        return user.getBillingAddresses().isEmpty() ?
                "No billing address" :
                user.getBillingAddresses().get(0).toSingleLine();
    }

    private List<CartItemDisplay> prepareCartItemDisplays(Cart cart) {
        List<CartItemDisplay> displays = new ArrayList<>();
        Map<Integer, CartItem> products = cart.getProducts();

        for (CartItem item : products.values()) {
            Product product = item.getProduct();
            displays.add(new CartItemDisplay(
                    product.getName(),
                    product.getPrice(),
                    item.getQuantity(),
                    product.getPrice() * item.getQuantity()
            ));
        }
        return displays;
    }

    private double calculateTotalPrice(Cart cart) {
        double total = 0.0;
        Map<Integer, CartItem> products = cart.getProducts();
        for (CartItem item : products.values()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}