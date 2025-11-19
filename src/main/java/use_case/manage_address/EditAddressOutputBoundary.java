package use_case.manage_address;

import java.util.Map;

/**
 * Output boundary for editing an address.
 */
public interface EditAddressOutputBoundary {
    void prepareSuccessView(EditAddressOutputData outputData);
    void prepareFailView(Map<String, String> errors);
    void prepareNotFoundView(String message);
}