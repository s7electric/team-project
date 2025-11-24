package interface_adapter.manage_address;

import entity.Address;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Simple state object for the ManageAddressViewModel.
 */
public class ManageAddressState {
    private String username;
    private List<Address> addresses = new ArrayList<>();
    private List<String> addressIds = new ArrayList<>();
    private List<String> displayAddresses = new ArrayList<>();
    private Map<String, String> fieldErrors;
    private String message;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public List<String> getAddressIds() { return addressIds; }
    public void setAddressIds(List<String> addressIds) { this.addressIds = addressIds; }

    public List<String> getDisplayAddresses() { return displayAddresses; }
    public void setDisplayAddresses(List<String> displayAddresses) { this.displayAddresses = displayAddresses; }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
