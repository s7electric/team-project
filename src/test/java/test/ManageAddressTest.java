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


