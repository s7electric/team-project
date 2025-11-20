package use_case.manage_address;

import entity.Address;
import entity.User;

import java.util.Iterator;
import java.util.List;

/**
 * Interactor for deleting an address from a user.
 */
public class DeleteAddressInteractor implements DeleteAddressInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final DeleteAddressOutputBoundary presenter;

    public DeleteAddressInteractor(UserDataAccessInterface userDataAccess,
                                   DeleteAddressOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteAddressInputData in) {
        User user = userDataAccess.getUser(in.getUsername());
        if (user == null) {
            presenter.prepareNotFoundView("User not found: " + in.getUsername());
            return;
        }

        List<Address> addresses = user.getBillingAddresses();
        boolean removed = false;
        Iterator<Address> it = addresses.iterator();
        while (it.hasNext()) {
            Address a = it.next();
            if (a.getId().equals(in.getAddressId())) {
                it.remove();
                removed = true;
                break;
            }
        }

        if (!removed) {
            presenter.prepareNotFoundView("Address not found: " + in.getAddressId());
            return;
        }

        userDataAccess.saveUser(user);
        presenter.prepareSuccessView(new DeleteAddressOutputData(user.getUsername(), in.getAddressId()));
    }
}