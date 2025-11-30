package use_case.logout;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    @Test
    void logoutClearsCurrentUserAndReturnsUsername() {
        TrackingLogoutGateway userGateway = new TrackingLogoutGateway("Paul");

        LogoutOutputBoundary presenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertEquals("Paul", outputData.getUsername());
            }

            @Override
            public void switchToHomePage() { }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userGateway, presenter);
        interactor.execute();

        assertNull(userGateway.currentUsernameValue());
        assertEquals(1, userGateway.getGetCalls());
        assertEquals(1, userGateway.getSetCalls());
        assertNull(userGateway.getLastSetValue());
    }

    @Test
    void logoutWhenNoUserLoggedInStillSucceeds() {
        TrackingLogoutGateway userGateway = new TrackingLogoutGateway(null);

        LogoutOutputBoundary presenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) {
                assertNull(outputData.getUsername());
            }

            @Override
            public void switchToHomePage() { }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userGateway, presenter);
        interactor.execute();

        assertNull(userGateway.currentUsernameValue());
        assertEquals(1, userGateway.getGetCalls());
        assertEquals(1, userGateway.getSetCalls());
        assertNull(userGateway.getLastSetValue());
    }

    @Test
    void switchToHomePageDelegatesToPresenter() {
        TrackingLogoutGateway userGateway = new TrackingLogoutGateway("Paul");
        AtomicBoolean called = new AtomicBoolean(false);

        LogoutOutputBoundary presenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData outputData) { }

            @Override
            public void switchToHomePage() {
                called.set(true);
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userGateway, presenter);
        interactor.switchToHomePage();

        assertTrue(called.get(), "Presenter switchToHomePage should be invoked");
    }

    private static class TrackingLogoutGateway implements LogoutUserDataAccessInterface {
        private String currentUsername;
        private final AtomicInteger getCalls = new AtomicInteger();
        private final AtomicInteger setCalls = new AtomicInteger();
        private String lastSetValue;

        TrackingLogoutGateway(String currentUsername) {
            this.currentUsername = currentUsername;
        }

        @Override
        public String getCurrentUsername() {
            getCalls.incrementAndGet();
            return currentUsername;
        }

        @Override
        public void setCurrentUsername(String username) {
            setCalls.incrementAndGet();
            this.currentUsername = username;
            this.lastSetValue = username;
        }

        int getGetCalls() {
            return getCalls.get();
        }

        int getSetCalls() {
            return setCalls.get();
        }

        String getLastSetValue() {
            return lastSetValue;
        }

        String currentUsernameValue() {
            return currentUsername;
        }
    }
}
