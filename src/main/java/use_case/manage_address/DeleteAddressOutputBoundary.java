package use_case.manage_address;

/**
 * Output boundary for deleting an address.
 */
public interface DeleteAddressOutputBoundary {
    void prepareSuccessView(DeleteAddressOutputData outputData);
    void prepareNotFoundView(String message);
}