package use_case.manage_address;

import entity.Address;
import entity.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Interactor for editing an existing address of a user.
 */
public class EditAddressInteractor implements EditAddressInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final EditAddressOutputBoundary presenter;

    public EditAddressInteractor(UserDataAccessInterface userDataAccess,
                                 EditAddressOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditAddressInputData in) {
        User user = userDataAccess.getUser(in.getUsername());
        if (user == null) {
            presenter.prepareNotFoundView("User not found: " + in.getUsername());
            return;
        }

        List<Address> addresses = user.getBillingAddresses();
        Address target = null;
        for (Address a : addresses) {
            if (a.getId().equals(in.getAddressId())) {
                target = a;
                break;
            }
        }
        if (target == null) {
            presenter.prepareNotFoundView("Address not found: " + in.getAddressId());
            return;
        }

        Map<String, String> errors = validate(in);
        if (!errors.isEmpty()) {
            presenter.prepareFailView(errors);
            return;
        }

        // update fields
        target.setLine1(in.getLine1());
        target.setLine2(in.getLine2());
        target.setCity(in.getCity());
        target.setProvinceOrState(in.getProvinceOrState());
        target.setPostalCode(in.getPostalCode());
        target.setCountry(in.getCountry());

        // handle default flags
        if (in.isDefaultShipping()) {
            addresses.forEach(a -> a.setDefaultShipping(false));
        }
        if (in.isDefaultBilling()) {
            addresses.forEach(a -> a.setDefaultBilling(false));
        }
        target.setDefaultShipping(in.isDefaultShipping());
        target.setDefaultBilling(in.isDefaultBilling());

        userDataAccess.saveUser(user);
        presenter.prepareSuccessView(new EditAddressOutputData(user.getUsername(), user.getBillingAddresses()));
    }

    private Map<String, String> validate(EditAddressInputData in) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (isBlank(in.getLine1())) errors.put("line1", "Line 1 is required.");
        if (isBlank(in.getCity())) errors.put("city", "City is required.");
        if (isBlank(in.getProvinceOrState())) errors.put("provinceOrState", "Province/State is required.");
        if (isBlank(in.getPostalCode())) errors.put("postalCode", "Postal/ZIP is required.");
        if (isBlank(in.getCountry())) errors.put("country", "Country is required.");
        return errors;
    }

    private boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}