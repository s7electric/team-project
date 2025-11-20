package use_case.manage_address;

/**
 * Request model for deleting an address.
 */
public class DeleteAddressInputData {
    private final String username;
    private final String addressId;

    public DeleteAddressInputData(String username, String addressId) {
        this.username = username;
        this.addressId = addressId;
    }

    public String getUsername() { return username; }
    public String getAddressId() { return addressId; }
}