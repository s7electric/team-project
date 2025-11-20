package use_case.manage_address;

/**
 * Input boundary for deleting an address.
 */
public interface DeleteAddressInputBoundary {
    void execute(DeleteAddressInputData inputData);
}