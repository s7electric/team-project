package use_case.manage_address;

import entity.Address;
import java.util.List;

/**
 * Response model for editing an address.
 */
public class EditAddressOutputData {
    private final String username;
    private final List<Address> addresses;

    public EditAddressOutputData(String username, List<Address> addresses) {
        this.username = username;
        this.addresses = addresses;
    }

    public String getUsername() { return username; }
    public List<Address> getAddresses() { return addresses; }
}