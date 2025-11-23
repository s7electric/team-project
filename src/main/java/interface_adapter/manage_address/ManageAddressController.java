package interface_adapter.manage_address;

import use_case.manage_address.*;

/**
 * Controller for the Manage Address use case.
 * Takes raw UI inputs and constructs input data objects for the interactors.
 */
public class ManageAddressController {

    private final AddAddressInputBoundary addInteractor;
    private final EditAddressInputBoundary editInteractor;
    private final DeleteAddressInputBoundary deleteInteractor;

    public ManageAddressController(AddAddressInputBoundary addInteractor,
                                   EditAddressInputBoundary editInteractor,
                                   DeleteAddressInputBoundary deleteInteractor) {
        this.addInteractor = addInteractor;
        this.editInteractor = editInteractor;
        this.deleteInteractor = deleteInteractor;
    }

    public void addAddress(String username,
                           String line1,
                           String line2,
                           String city,
                           String provinceOrState,
                           String postalCode,
                           String country,
                           boolean defaultShipping,
                           boolean defaultBilling) {
        AddAddressInputData input = new AddAddressInputData(
                username, line1, line2, city, provinceOrState, postalCode, country, defaultShipping, defaultBilling
        );
        addInteractor.execute(input);
    }

    public void editAddress(String username,
                            String addressId,
                            String line1,
                            String line2,
                            String city,
                            String provinceOrState,
                            String postalCode,
                            String country,
                            boolean defaultShipping,
                            boolean defaultBilling) {
        EditAddressInputData input = new EditAddressInputData(
                username, addressId, line1, line2, city, provinceOrState, postalCode, country, defaultShipping, defaultBilling
        );
        editInteractor.execute(input);
    }

    public void deleteAddress(String username, String addressId) {
        DeleteAddressInputData input = new DeleteAddressInputData(username, addressId);
        deleteInteractor.execute(input);
    }
}