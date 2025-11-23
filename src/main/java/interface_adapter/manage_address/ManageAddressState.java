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
    private Map<String, String> fieldErrors;
    private String message;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}