package test;

import entity.Address;
import entity.Cart;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.manage_address.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class ManageAddressTest {

    @Test
    void testAddAddressSuccess() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeAddPresenter presenter = new FakeAddPresenter();

        HashSet<Address> initialAddresses = new HashSet<>();
        User user = new User(
                "alice",
                "alice@example.com",
                123456,
                0.0,
                initialAddresses,
                new ArrayList<>(),
                new Cart("alice")
        );
        userData.saveUser(user);

        AddAddressInteractor interactor =
                new AddAddressInteractor(userData, presenter);

        AddAddressInputData input = new AddAddressInputData(
                "alice",
                "123 King St",
                "Unit 8",
                "Toronto",
                "ON",
                "M5V 1A1",
                "Canada",
                true,
                true
        );

        interactor.execute(input);

        assertNull(presenter.lastErrors, "There should be no validation errors.");
        assertNotNull(presenter.lastOutput, "Output data should not be null.");
        assertEquals("alice", presenter.lastOutput.getUsername());

        User savedUser = userData.getUser("alice");
        assertNotNull(savedUser);
        assertEquals(1, savedUser.getBillingAddresses().size());

        Address added = savedUser.getBillingAddresses().iterator().next();
        assertEquals("123 King St", added.getLine1());
        assertEquals("Toronto", added.getCity());
        assertTrue(added.isDefaultBilling());
        assertTrue(added.isDefaultShipping());
    }

    @Test
    void testAddAddressValidationError() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeAddPresenter presenter = new FakeAddPresenter();

        User user = new User(
                "bob",
                "bob@example.com",
                123456,
                0.0,
                new HashSet<>(),
                new ArrayList<>(),
                new Cart("bob")
        );
        userData.saveUser(user);

        AddAddressInteractor interactor =
                new AddAddressInteractor(userData, presenter);

        AddAddressInputData input = new AddAddressInputData(
                "bob",
                "",
                "",
                "",
                "ON",
                "M5V 1A1",
                "Canada",
                false,
                false
        );

        interactor.execute(input);

        assertNull(presenter.lastOutput);
        assertNotNull(presenter.lastErrors);
        assertTrue(presenter.lastErrors.containsKey("line1"));
        assertTrue(presenter.lastErrors.containsKey("city"));
    }


    @Test
    void testEditAddressSuccess() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeEditPresenter presenter = new FakeEditPresenter();

        Address addr = new Address(
                "Alice", "123 King St", "Unit 8",
                "Toronto", "ON", "M5V 1A1", "Canada",
                false, false
        );

        HashSet<Address> addrSet = new HashSet<>();
        addrSet.add(addr);

        User user = new User(
                "alice",
                "alice@example.com",
                123456,
                0.0,
                addrSet,
                new ArrayList<>(),
                new Cart("alice")
        );
        userData.saveUser(user);

        EditAddressInteractor interactor =
                new EditAddressInteractor(userData, presenter);

        EditAddressInputData input = new EditAddressInputData(
                "alice",
                addr.getId(),
                "456 Queen St",
                "Floor 2",
                "Toronto",
                "ON",
                "M5V 2A2",
                "Canada",
                true,
                true
        );

        interactor.execute(input);

        assertNull(presenter.lastErrors);
        assertNotNull(presenter.lastOutput);

        User savedUser = userData.getUser("alice");
        assertNotNull(savedUser);
        Address edited = savedUser.getBillingAddresses().iterator().next();

        assertEquals("456 Queen St", edited.getLine1());
        assertEquals("M5V 2A2", edited.getPostalCode());
        assertTrue(edited.isDefaultBilling());
        assertTrue(edited.isDefaultShipping());
    }

    @Test
    void testEditAddressNotFound() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeEditPresenter presenter = new FakeEditPresenter();

        User user = new User(
                "alice",
                "alice@example.com",
                123456,
                0.0,
                new HashSet<>(),
                new ArrayList<>(),
                new Cart("alice")
        );
        userData.saveUser(user);

        EditAddressInteractor interactor =
                new EditAddressInteractor(userData, presenter);

        EditAddressInputData input = new EditAddressInputData(
                "alice",
                "NON_EXIST_ID",
                "456 Queen St",
                "Floor 2",
                "Toronto",
                "ON",
                "M5V 2A2",
                "Canada",
                false,
                false
        );

        interactor.execute(input);

        assertNull(presenter.lastOutput);
        assertEquals("Address not found: NON_EXIST_ID", presenter.lastNotFoundMessage);
    }


    @Test
    void testDeleteAddressSuccess() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeDeletePresenter presenter = new FakeDeletePresenter();

        Address addr = new Address(
                "Alice", "123 King St", "Unit 8",
                "Toronto", "ON", "M5V 1A1", "Canada",
                false, false
        );
        HashSet<Address> addrSet = new HashSet<>();
        addrSet.add(addr);

        User user = new User(
                "alice",
                "alice@example.com",
                123456,
                0.0,
                addrSet,
                new ArrayList<>(),
                new Cart("alice")
        );
        userData.saveUser(user);

        DeleteAddressInteractor interactor =
                new DeleteAddressInteractor(userData, presenter);

        DeleteAddressInputData input =
                new DeleteAddressInputData("alice", addr.getId());

        interactor.execute(input);

        User savedUser = userData.getUser("alice");
        assertNotNull(savedUser);
        assertTrue(savedUser.getBillingAddresses().isEmpty(),
                "Address should be removed from user's addresses.");
        assertEquals(addr.getId(), presenter.lastDeletedId);
    }

    @Test
    void testDeleteAddressNotFound() {
        FakeUserDataAccess userData = new FakeUserDataAccess();
        FakeDeletePresenter presenter = new FakeDeletePresenter();

        User user = new User(
                "bob",
                "bob@example.com",
                123456,
                0.0,
                new HashSet<>(),
                new ArrayList<>(),
                new Cart("bob")
        );
        userData.saveUser(user);

        DeleteAddressInteractor interactor =
                new DeleteAddressInteractor(userData, presenter);

        DeleteAddressInputData input =
                new DeleteAddressInputData("bob", "UNKNOWN_ID");

        interactor.execute(input);

        assertEquals("Address not found: UNKNOWN_ID", presenter.lastNotFoundMessage);
    }

    private static class FakeUserDataAccess implements UserDataAccessInterface {
        private final Map<String, User> store = new HashMap<>();

        @Override
        public User getUser(String username) {
            return store.get(username);
        }

        @Override
        public void saveUser(User user) {
            store.put(user.getUsername(), user);
        }
    }

    private static class FakeAddPresenter implements AddAddressOutputBoundary {
        AddAddressOutputData lastOutput;
        Map<String, String> lastErrors;
        String lastUserNotFoundMessage;

        @Override
        public void prepareSuccessView(AddAddressOutputData outputData) {
            this.lastOutput = outputData;
            this.lastErrors = null;
        }

        @Override
        public void prepareFailView(Map<String, String> errors) {
            this.lastErrors = errors;
            this.lastOutput = null;
        }

        @Override
        public void prepareUserNotFoundView(String message) {
            this.lastUserNotFoundMessage = message;
        }
    }

    private static class FakeEditPresenter implements EditAddressOutputBoundary {
        EditAddressOutputData lastOutput;
        Map<String, String> lastErrors;
        String lastNotFoundMessage;

        @Override
        public void prepareSuccessView(EditAddressOutputData outputData) {
            this.lastOutput = outputData;
            this.lastErrors = null;
        }

        @Override
        public void prepareFailView(Map<String, String> errors) {
            this.lastErrors = errors;
            this.lastOutput = null;
        }

        @Override
        public void prepareNotFoundView(String message) {
            this.lastNotFoundMessage = message;
        }
    }

    private static class FakeDeletePresenter implements DeleteAddressOutputBoundary {
        String lastDeletedId;
        String lastNotFoundMessage;

        @Override
        public void prepareSuccessView(DeleteAddressOutputData outputData) {
            this.lastDeletedId = outputData.getDeletedAddressId();
        }

        @Override
        public void prepareNotFoundView(String message) {
            this.lastNotFoundMessage = message;
        }
    }
}