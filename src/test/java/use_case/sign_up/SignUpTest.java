package use_case.sign_up;

import data_access.DataAccessObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpTest {
    @Test
    void successTest(){
        String username = "Reza" + LocalDateTime.now();
        String password = "TestMacBookPro2%";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
        SignUpInputData signUpInputData = new SignUpInputData(username, password, email, billingAddress);
        SignUpDataAccessInterface signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                assertTrue(signUpOutputData.getUser() != null);
                assertTrue( signUpOutputData.getError() == null);
                assertEquals(username, signUpOutputData.getUser().getUsername());
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
    @Test
    void failureUserExistsTest(){
        String username = "Reza";
        String password = "TestMacBookPro2%";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
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
    @Test
    void failureUsernameEmptyTest(){
        String username = "";
        String password = "TestMacBookPro2%";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
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
    @Test
    void failurePasswordTest(){
        String username = "Reza" + LocalDateTime.now();
        String password = "";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
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
    @Test
    void failureEmailEmptyTest(){
        String username = "Reza" + LocalDateTime.now();
        String password = "TestMacBookPro2025%";
        String email = "";
        String billingAddress = "123 Main Street";
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
    @Test
    void failureBillingEmptyTest() {
        String username = "Reza" + LocalDateTime.now();
        String password = "TestMacBookPro2%";
        String email = "reza@mail.com";
        String billingAddress = "";
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
    @Test
    void failurePasswordWeaknessTest() {
        String username = "Reza" + LocalDateTime.now();
        String password = "TestMacBookpro";
        String email = "reza@mail.com";
        String billingAddress = "123 Main Street";
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
    @Test
    void testSwitchToLoginView() {
        SignUpDataAccessInterface  signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void updateFailure(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
            }

            @Override
            public void switchToLoginView() {
                assert true;
            }

            @Override
            public void switchToHomepageView() {
                fail("Use case failure is unexpected");
            }
        };
        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.switchToLoginView();
    }

    @Test
    void testSwitchToHomepageView() {
        SignUpDataAccessInterface  signUpDataAccessObject = new DataAccessObject();
        SignUpOutputBoundary signUpOutputBoundary = new SignUpOutputBoundary() {
            @Override
            public void updateSuccess(SignUpOutputData signUpOutputData) {
                fail("Use case failure is unexpected");
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
                assert true;
            }
        };
        SignUpInputBoundary signUpInputBoundary = new SignUpInteractor(signUpOutputBoundary, signUpDataAccessObject);
        signUpInputBoundary.switchToHomepageView();
    }
}
