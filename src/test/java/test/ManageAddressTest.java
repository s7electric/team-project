package test;

import entity.Address;
import entity.User;
import use_case.manage_address.*;

import java.util.*;

public class ManageAddressTest {

    public static void main(String[] args) {
        System.out.println("Manage Address Use Case Tests");

        testAddAddressSuccess();
        testAddAddressValidationError();
        testAddAddressUserNotFound();

        testEditAddressSuccess();
        testEditAddressAddressNotFound();

        testDeleteAddressSuccess();
        testDeleteAddressNotFound();

        System.out.println(">>> All ManageAddress tests finished without assertion failures.");
    }


    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Assertion failed: " + message);
        }
    }

    private static void testAddAddressSuccess() {
        System.out.println("[Test] AddAddressInteractor - success case");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("alice", "alice@example.com", "password123", "123 Initial St, Toronto");
        userData.saveUser(user);

        TestAddPresenter presenter = new TestAddPresenter();
        AddAddressInteractor interactor = new AddAddressInteractor(userData, presenter);

        AddAddressInputData input = new AddAddressInputData(
                "alice",
                "456 New St",
                "Unit 789",
                "Toronto",
                "ON",
                "M5G 2C3",
                "Canada",
                true,
                false
        );

        interactor.execute(input);

        assertTrue(presenter.successCalled, "AddAddress success view should be called.");
        assertTrue(!presenter.validationCalled, "AddAddress validation error should NOT be called.");
        assertTrue(!presenter.userNotFoundCalled, "AddAddress user-not-found should NOT be called.");

        User updated = userData.getUser("alice");
        assertTrue(updated != null, "User 'alice' should still exist.");
        assertTrue(updated.getBillingAddresses().size() == 2,
                "User should have 2 addresses after adding a new one.");

        System.out.println("  -> OK");
    }

    private static void testAddAddressValidationError() {
        System.out.println("[Test] AddAddressInteractor - validation error");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("bob", "bob@example.com", "password123", "1 First St");
        userData.saveUser(user);

        TestAddPresenter presenter = new TestAddPresenter();
        AddAddressInteractor interactor = new AddAddressInteractor(userData, presenter);

        AddAddressInputData badInput = new AddAddressInputData(
                "bob",
                "",
                "Unit 10",
                "Toronto",
                "ON",
                "M5G 2C3",
                "Canada",
                false,
                false
        );

        interactor.execute(badInput);

        assertTrue(!presenter.successCalled, "AddAddress success should NOT be called.");
        assertTrue(presenter.validationCalled, "AddAddress validation error SHOULD be called.");
        assertTrue(presenter.validationErrors.containsKey("line1"),
                "Validation errors should contain 'line1'.");

        System.out.println("  -> OK");
    }

    private static void testAddAddressUserNotFound() {
        System.out.println("[Test] AddAddressInteractor - user not found");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();

        TestAddPresenter presenter = new TestAddPresenter();
        AddAddressInteractor interactor = new AddAddressInteractor(userData, presenter);

        AddAddressInputData input = new AddAddressInputData(
                "unknownUser",
                "100 Street",
                "",
                "City",
                "Province",
                "12345",
                "Country",
                false,
                false
        );

        interactor.execute(input);

        assertTrue(!presenter.successCalled, "AddAddress success should NOT be called.");
        assertTrue(!presenter.validationCalled, "AddAddress validation should NOT be called.");
        assertTrue(presenter.userNotFoundCalled, "User-not-found should be called.");

        System.out.println("  -> OK");
    }

    private static void testEditAddressSuccess() {
        System.out.println("[Test] EditAddressInteractor - success case");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("carol", "carol@example.com", "pwd", "Old Default Addr");
        Address extraAddress = new Address(
                "Carol",
                "200 Old St",
                "Unit 5",
                "Toronto",
                "ON",
                "M1M 1M1",
                "Canada",
                false,
                false
        );
        user.addAddress(extraAddress);
        userData.saveUser(user);

        String addressIdToEdit = extraAddress.getId();

        TestEditPresenter presenter = new TestEditPresenter();
        EditAddressInteractor interactor = new EditAddressInteractor(userData, presenter);

        EditAddressInputData input = new EditAddressInputData(
                "carol",
                addressIdToEdit,
                "200 New St",
                "Unit 999",
                "Waterloo",
                "ON",
                "N2L 3G1",
                "Canada",
                true,
                true
        );

        interactor.execute(input);

        assertTrue(presenter.successCalled, "EditAddress success should be called.");
        assertTrue(!presenter.validationCalled, "EditAddress validation errors should NOT be called.");
        assertTrue(!presenter.notFoundCalled, "EditAddress not-found should NOT be called.");

        User updated = userData.getUser("carol");
        Address edited = null;
        for (Address a : updated.getBillingAddresses()) {
            if (a.getId().equals(addressIdToEdit)) {
                edited = a;
                break;
            }
        }
        assertTrue(edited != null, "Edited address should still exist.");
        assertTrue("200 New St".equals(edited.getLine1()), "Line1 should be updated.");
        assertTrue("Unit 999".equals(edited.getLine2()), "Line2 should be updated.");
        assertTrue("Waterloo".equals(edited.getCity()), "City should be updated.");
        assertTrue(edited.isDefaultBilling(), "Edited address should be default billing.");
        assertTrue(edited.isDefaultShipping(), "Edited address should be default shipping.");

        System.out.println("  -> OK");
    }


    private static void testEditAddressAddressNotFound() {
        System.out.println("[Test] EditAddressInteractor - address not found");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("dave", "dave@example.com", "pwd", "Some Address");
        userData.saveUser(user);

        TestEditPresenter presenter = new TestEditPresenter();
        EditAddressInteractor interactor = new EditAddressInteractor(userData, presenter);

        EditAddressInputData input = new EditAddressInputData(
                "dave",
                "non-existent-id",
                "New Line 1",
                "",
                "City",
                "Prov",
                "00000",
                "Country",
                false,
                false
        );

        interactor.execute(input);

        assertTrue(!presenter.successCalled, "EditAddress success should NOT be called.");
        assertTrue(!presenter.validationCalled, "EditAddress validation should NOT be called.");
        assertTrue(presenter.notFoundCalled, "EditAddress not-found should be called.");

        System.out.println("  -> OK");
    }

    private static void testDeleteAddressSuccess() {
        System.out.println("[Test] DeleteAddressInteractor - success case");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("eric", "eric@example.com", "pwd", "First Addr");
        // Add another address to delete
        Address addr = new Address(
                "Eric",
                "Del St",
                "",
                "Toronto",
                "ON",
                "K1K 1K1",
                "Canada",
                false,
                false
        );
        user.addAddress(addr);
        userData.saveUser(user);

        String addressIdToDelete = addr.getId();

        TestDeletePresenter presenter = new TestDeletePresenter();
        DeleteAddressInteractor interactor = new DeleteAddressInteractor(userData, presenter);

        DeleteAddressInputData input = new DeleteAddressInputData("eric", addressIdToDelete);
        interactor.execute(input);

        assertTrue(presenter.successCalled, "DeleteAddress success should be called.");
        assertTrue(!presenter.notFoundCalled, "DeleteAddress not-found should NOT be called.");

        User updated = userData.getUser("eric");
        boolean stillExists = updated.getBillingAddresses().stream()
                .anyMatch(a -> a.getId().equals(addressIdToDelete));
        assertTrue(!stillExists, "Address should be removed from user's addresses.");

        System.out.println("  -> OK");
    }


    private static void testDeleteAddressNotFound() {
        System.out.println("[Test] DeleteAddressInteractor - address not found");

        InMemoryUserDataAccess userData = new InMemoryUserDataAccess();
        User user = new User("frank", "frank@example.com", "pwd", "Base Addr");
        userData.saveUser(user);

        TestDeletePresenter presenter = new TestDeletePresenter();
        DeleteAddressInteractor interactor = new DeleteAddressInteractor(userData, presenter);

        DeleteAddressInputData input = new DeleteAddressInputData("frank", "does-not-exist");
        interactor.execute(input);

        assertTrue(!presenter.successCalled, "DeleteAddress success should NOT be called.");
        assertTrue(presenter.notFoundCalled, "DeleteAddress not-found SHOULD be called.");

        System.out.println("  -> OK");
    }

    /**
     * In-memory implementation of UserDataAccessInterface
     */
    private static class InMemoryUserDataAccess implements UserDataAccessInterface {

        private final Map<String, User> users = new HashMap<>();

        @Override
        public User getUser(String username) {
            return users.get(username);
        }

        @Override
        public void saveUser(User user) {
            users.put(user.getUsername(), user);
        }

    }

    private static class TestAddPresenter implements AddAddressOutputBoundary {

        boolean successCalled = false;
        boolean validationCalled = false;
        boolean userNotFoundCalled = false;
        Map<String, String> validationErrors = Collections.emptyMap();

        @Override
        public void prepareSuccessView(AddAddressOutputData outputData) {
            successCalled = true;
        }

        @Override
        public void prepareFailView(Map<String, String> errors) {
            validationCalled = true;
            validationErrors = errors;
        }

        @Override
        public void prepareUserNotFoundView(String message) {
            userNotFoundCalled = true;
        }
    }

    private static class TestEditPresenter implements EditAddressOutputBoundary {

        boolean successCalled = false;
        boolean validationCalled = false;
        boolean notFoundCalled = false;

        @Override
        public void prepareSuccessView(EditAddressOutputData outputData) {
            successCalled = true;
        }

        @Override
        public void prepareFailView(Map<String, String> errors) {
            validationCalled = true;
        }

        @Override
        public void prepareNotFoundView(String message) {
            notFoundCalled = true;
        }
    }


    private static class TestDeletePresenter implements DeleteAddressOutputBoundary {

        boolean successCalled = false;
        boolean notFoundCalled = false;

        @Override
        public void prepareSuccessView(DeleteAddressOutputData outputData) {
            successCalled = true;
        }

        @Override
        public void prepareNotFoundView(String message) {
            notFoundCalled = true;
        }
    }
}

