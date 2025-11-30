package test;

import data_access.DataAccessObject;
import interface_adapter.sign_up.SignUpPresenter;
import use_case.sign_up.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {
    void successTest(String username, String password, String email, String billingAddress){
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                assertTrue(signUpOutputData.getUser() != null);
                assertTrue( signUpOutputData.getError() == null);
                assertEquals(username, signUpOutputData.getUser());
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failureUserExistsTest(String username, String password, String email, String billingAddress){
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue(signUpOutputData.getError() != null);
                assertEquals("An user with the same username exists, try to login!", signUpOutputData.getError());
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failureUsernameEmptyTest(String username, String password, String email, String billingAddress){
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue( signUpOutputData.getError() != null);
                assertEquals("The username cannot be empty or contain spaces!", signUpOutputData.getError());
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failurePasswordTest(String username, String password, String email, String billingAddress){
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue( signUpOutputData.getError() != null);
                assertEquals("The password cannot be empty!", signUpOutputData.getError());
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failureEmailEmptyTest(String username, String password, String email, String billingAddress){
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue( signUpOutputData.getError() != null);
                assertEquals("The email cannot be empty!", signUpOutputData.getError());
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failureBillingEmptyTest(String username, String password, String email, String billingAddress) {
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue( signUpOutputData.getError() != null);
                assertEquals("The billing address cannot be empty!", signUpOutputData.getError());
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    void failurePasswordWeaknessTest(String username, String password, String email, String billingAddress) {
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                assertTrue( signUpOutputData.getUser() == null);
                assertTrue( signUpOutputData.getError() != null);
                assertTrue( signUpOutputData.getError().contains("Your password"));
            }

            @Override
            public void switchToLoginView() {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };

        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.execute(signUpInputData);
    }

    public static void main(String[] args) {
        SignUpTest signUpTest = new SignUpTest();
        String username = "Reza" + LocalDateTime.now();
        String password = "TestMacBookPro2025%";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
        signUpTest.successTest(username, password, email, billingAddress);
        signUpTest.failureUserExistsTest(username, password, email, billingAddress);
        username = "";
        password = "TestMacBookPro2025%";
        email = "reza@mail.com";
        billingAddress = "123 Main Street";
        signUpTest.failureUsernameEmptyTest(username, password, email, billingAddress);
        username = "Reza" + LocalDateTime.now();
        password = "TestMacBookPro2025%";
        email = "";
        billingAddress = "123 Main Street";
        signUpTest.failureEmailEmptyTest(username, password, email, billingAddress);
        username = "Reza" + LocalDateTime.now();
        password = "TestMacBookPro2025%";
        email = "reza@mail.com";
        billingAddress = "";
        signUpTest.failureBillingEmptyTest(username, password, email, billingAddress);
        username = "Reza" + LocalDateTime.now();
        password = "TestMacBookpro";
        email = "reza@mail.com";
        billingAddress = "123 Main Street";
        signUpTest.failureBillingEmptyTest(username, password, email, billingAddress);
        username = "Reza" + LocalDateTime.now();
        password = "Test";
        email = "reza@mail.com";
        billingAddress = "123 Main Street";
        signUpTest.failureBillingEmptyTest(username, password, email, billingAddress);
        username = "Reza" + LocalDateTime.now();
        password = "RezaMacBookPro2025%";
        email = "reza@mail.com";
        billingAddress = "123 Main Street";
        signUpTest.failureBillingEmptyTest(username, password, email, billingAddress);
    }
}
