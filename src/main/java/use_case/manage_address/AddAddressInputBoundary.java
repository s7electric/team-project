package use_case.manage_address;

/**
 * Input boundary for adding a new address for a user.
 */
public interface AddAddressInputBoundary {
    void execute(AddAddressInputData inputData);
}
