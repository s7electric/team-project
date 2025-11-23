package interface_adapter.manage_address;

import entity.Address;
import use_case.manage_address.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Presenter converts use case output data into a state object for the ViewModel.
 */
public class ManageAddressPresenter implements
        AddAddressOutputBoundary,
        EditAddressOutputBoundary,
        DeleteAddressOutputBoundary {

    private final ManageAddressViewModel viewModel;

    public ManageAddressPresenter(ManageAddressViewModel viewModel) {
        this.viewModel = viewModel;
    }

    // ========== Add Address Success ==========
    @Override
    public void prepareSuccessView(AddAddressOutputData outputData) {
        updateStateFromUseCase(
                outputData.getUsername(),
                outputData.getAddresses(),
                null,
                "Address added."
        );
    }

    @Override
    public void prepareFailView(Map<String, String> errors) {
        ManageAddressState state = viewModel.getState();
        state.setFieldErrors(errors);
        state.setMessage("Validation errors.");
        viewModel.setState(state);
    }

    @Override
    public void prepareUserNotFoundView(String message) {
        ManageAddressState state = viewModel.getState();
        state.setMessage(message);
        viewModel.setState(state);
    }


    @Override
    public void prepareSuccessView(EditAddressOutputData outputData) {
        updateStateFromUseCase(
                outputData.getUsername(),
                outputData.getAddresses(),
                null,
                "Address updated."
        );
    }

    @Override
    public void prepareNotFoundView(String message) {
        ManageAddressState state = viewModel.getState();
        state.setMessage(message);
        viewModel.setState(state);
    }


    @Override
    public void prepareSuccessView(DeleteAddressOutputData outputData) {

        // Delete Use Case 不会返回地址列表，所以 View 只更新 message
        ManageAddressState state = viewModel.getState();
        state.setMessage("Address deleted: " + outputData.getDeletedAddressId());
        state.setFieldErrors(null); // clear old errors
        viewModel.setState(state);
    }


    private void updateStateFromUseCase(String username,
                                        List<Address> addresses,
                                        Map<String, String> errors,
                                        String message) {

        ManageAddressState newState = new ManageAddressState();

        newState.setUsername(username);

        List<String> ids = new ArrayList<>();
        List<String> displayTexts = new ArrayList<>();

        for (Address a : addresses) {
            ids.add(a.getLine1() + a.getPostalCode());

            displayTexts.add(a.toSingleLine());
        }

        newState.setAddressIds(ids);
        newState.setDisplayAddresses(displayTexts);

        newState.setFieldErrors(errors);
        newState.setMessage(message);

        viewModel.setState(newState);
    }
}