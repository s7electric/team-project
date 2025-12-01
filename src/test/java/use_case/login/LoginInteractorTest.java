package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        userRepository.save(new entity.User("Paul", "paul@example.com", "password", "123 Main Street"));

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void successWithTrimmedUsername() {
        LoginInputData inputData = new LoginInputData("  Paul  ", "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        userRepository.save(new entity.User("Paul", "paul@example.com", "password", "123 Main Street"));

        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername());
                assertEquals("Paul", userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        userRepository.save(new entity.User("Paul", "paul@example.com", "password", "123 Main Street"));

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyUsernameTest() {
        LoginInputData inputData = new LoginInputData("   ", "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyPasswordTest() {
        LoginInputData inputData = new LoginInputData("Paul", "   ");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureNullUsernameTest() {
        LoginInputData inputData = new LoginInputData(null, "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureNullPasswordTest() {
        LoginInputData inputData = new LoginInputData("Paul", null);
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDoesNotOverwriteExistingCurrentUser() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        userRepository.save(new entity.User("Paul", "paul@example.com", "password", "123 Main Street"));
        userRepository.setCurrentUsername("AlreadyLoggedIn");

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);

        assertEquals("AlreadyLoggedIn", userRepository.getCurrentUsername());
    }

    @Test
    void failureUsesTrimmedUsernameInMessage() {
        LoginInputData inputData = new LoginInputData("  Paul  ", "password");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Paul: Account does not exist.", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);

        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void invalidUsernameSkipsDataAccessCalls() {
        TrackingUserGateway userGateway = new TrackingUserGateway();
        LoginInputData inputData = new LoginInputData("   ", "password");

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userGateway, failurePresenter);
        interactor.execute(inputData);

        assertEquals(0, userGateway.getExistsCalls());
        assertEquals(0, userGateway.getGetCalls());
        assertEquals(0, userGateway.getSetCurrentCalls());
    }

    @Test
    void invalidPasswordSkipsDataAccessCalls() {
        TrackingUserGateway userGateway = new TrackingUserGateway();
        LoginInputData inputData = new LoginInputData("Paul", "   ");

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Password must not be empty", error);
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userGateway, failurePresenter);
        interactor.execute(inputData);

        assertEquals(0, userGateway.getExistsCalls());
        assertEquals(0, userGateway.getGetCalls());
        assertEquals(0, userGateway.getSetCurrentCalls());
    }

    @Test
    void logsInMultipleUsersSequentially() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        userRepository.save(new entity.User("Paul", "paul@example.com", "password1", "123 Main Street"));
        userRepository.save(new entity.User("Alice", "alice@example.com", "password2", "456 Elm Street"));

        List<String> successfulUsernames = new ArrayList<>();

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                successfulUsernames.add(user.getUsername());
                assertEquals(user.getUsername(), userRepository.getCurrentUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, presenter);

        interactor.execute(new LoginInputData("Paul", "password1"));
        assertEquals("Paul", userRepository.getCurrentUsername());

        interactor.execute(new LoginInputData("Alice", "password2"));
        assertEquals("Alice", userRepository.getCurrentUsername());

        assertEquals(List.of("Paul", "Alice"), successfulUsernames);
    }

    @Test
    void switchToSignUpView() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        AtomicBoolean called = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) { }

            @Override
            public void prepareFailView(String error) { }

            @Override
            public void switchToSignUpView() { called.set(true); }

            @Override
            public void switchToHomePage() { }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, presenter);
        interactor.switchToSignUpView();
        assertTrue(called.get(), "Presenter switchToSignUpView should be invoked");
    }

    @Test
    void switchToHomePage() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        AtomicBoolean called = new AtomicBoolean(false);

        LoginOutputBoundary presenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) { }

            @Override
            public void prepareFailView(String error) { }

            @Override
            public void switchToSignUpView() { }

            @Override
            public void switchToHomePage() { called.set(true); }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, presenter);
        interactor.switchToHomePage();
        assertTrue(called.get(), "Presenter switchToHomePage should be invoked");
    }

    private static class TrackingUserGateway implements LoginUserDataAccessInterface {
        private final AtomicInteger existsCalls = new AtomicInteger();
        private final AtomicInteger getCalls = new AtomicInteger();
        private final AtomicInteger setCurrentCalls = new AtomicInteger();
        private String currentUsername;

        @Override
        public boolean existsByName(String username) {
            existsCalls.incrementAndGet();
            return false;
        }

        @Override
        public entity.User get(String username) {
            getCalls.incrementAndGet();
            return null;
        }

        @Override
        public void setCurrentUsername(String username) {
            setCurrentCalls.incrementAndGet();
            this.currentUsername = username;
        }

        @Override
        public String getCurrentUsername() {
            return currentUsername;
        }

        int getExistsCalls() {
            return existsCalls.get();
        }

        int getGetCalls() {
            return getCalls.get();
        }

        int getSetCurrentCalls() {
            return setCurrentCalls.get();
        }
    }
}
