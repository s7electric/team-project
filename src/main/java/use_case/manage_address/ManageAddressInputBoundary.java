package use_case.manage_address;

/**
 * Input Boundary for the Manage Addresses use case.
 * The Controller will call this interface.
 */
public interface ManageAddressInputBoundary {

    void addAddress(ManageAddressInputData inputData);

    void editAddress(ManageAddressInputData inputData);

    void deleteAddress(String addressId);

    void loadAddresses();
}