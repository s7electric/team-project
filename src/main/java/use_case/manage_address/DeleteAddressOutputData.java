package use_case.manage_address;

/**
 * Response model for deleting an address.
 */
public class DeleteAddressOutputData {
    private final String username;
    private final String deletedAddressId;

    public DeleteAddressOutputData(String username, String deletedAddressId) {
        this.username = username;
        this.deletedAddressId = deletedAddressId;
    }

    public String getUsername() { return username; }
    public String getDeletedAddressId() { return deletedAddressId; }
}