package use_case.manage_address;

import entity.Address;
import entity.User;

import java.util.LinkedHashMap;
import java.util.Map;

public class AddAddressInteractor implements AddAddressInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final AddAddressOutputBoundary presenter;

    public AddAddressInteractor(UserDataAccessInterface userDataAccess,
                                AddAddressOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(AddAddressInputData inputData) {
        User user = userDataAccess.getUser(inputData.getUsername());
        if (user == null) {
            presenter.prepareUserNotFoundView("User not found: " + inputData.getUsername());
            return;
        }

        Map<String, String> errors = validate(inputData);
        if (!errors.isEmpty()) {
            presenter.prepareFailView(errors);
            return;
        }

        Address newAddress = new Address(
                inputData.getUsername(),
                inputData.getLine1(),
                inputData.getLine2(),
                inputData.getCity(),
                inputData.getProvinceOrState(),
                inputData.getPostalCode(),
                inputData.getCountry(),
                inputData.isDefaultBilling(),
                inputData.isDefaultShipping()
        );

        if (newAddress.isDefaultShipping()) {
            user.getBillingAddresses().forEach(a -> a.setDefaultShipping(false));
        }
        if (newAddress.isDefaultBilling()) {
            user.getBillingAddresses().forEach(a -> a.setDefaultBilling(false));
        }

        user.addAddress(newAddress);
        userDataAccess.saveUser(user);

        presenter.prepareSuccessView(
                new AddAddressOutputData(user.getUsername(), user.getBillingAddresses())
        );
    }

    private Map<String, String> validate(AddAddressInputData in) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (isBlank(in.getLine1())) errors.put("line1", "Line 1 is required.");
        if (isBlank(in.getCity())) errors.put("city", "City is required.");
        if (isBlank(in.getProvinceOrState())) errors.put("provinceOrState", "Province/State is required.");
        if (isBlank(in.getPostalCode())) errors.put("postalCode", "Postal/ZIP is required.");
        if (isBlank(in.getCountry())) errors.put("country", "Country is required.");
        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}