package use_case.manage_address;

import java.util.Map;

/**
 * Output boundary for adding a new address.
 */
public interface AddAddressOutputBoundary {

    /**
     * Called when the address is added successfully.
     * @param outputData the output data including the updated user and addresses
     */
    void prepareSuccessView(AddAddressOutputData outputData);

    /**
     * Called when validation errors occur.
     * @param errors map of field -> error message
     */
    void prepareFailView(Map<String, String> errors);

    /**
     * Called when the target user is not found.
     * @param message error message
     */
    void prepareUserNotFoundView(String message);
}