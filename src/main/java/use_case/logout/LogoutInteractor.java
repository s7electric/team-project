package use_case.logout;

import entity.User;

/**
 * The Login Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutUserDataAccessInterface userGateway;
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface, LogoutOutputBoundary logoutOutputBoundary) {
        this.userGateway = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        final String username = userGateway.getCurrentUsername();
        userGateway.setCurrentUsername(null);
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username);
        logoutPresenter.prepareSuccessView(logoutOutputData);
    }

    @Override
    public void switchToHomePage() {
        logoutPresenter.switchToHomePage();
    }
}
