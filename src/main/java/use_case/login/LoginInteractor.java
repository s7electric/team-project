package use_case.login;

import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary{
    private final LoginUserDataAccessInterface userGateway;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(LoginUserDataAccessInterface userGateway, LoginOutputBoundary presenter) {
        this.userGateway = userGateway;
        this.presenter = presenter;
    }

    @Override
    public void login(LoginInputData input){
        String username = input.getUsername();
        String password = input.getPassword();

        if (username == null || username.isBlank()){
            presenter.prepareFailView("Username must not be empty");
            return;
        }

        if (password == null || password.isBlank()){
            presenter.prepareFailView("Password must not be empty");
            return;
        }

        // Trim to avoid false mismatches due to spaces
        username = username.trim();
        if (!userGateway.existsByName(username)){
            presenter.prepareFailView(username + ": Account does not exist.");
            return;
        }

        User user = userGateway.get(username);
        if (!user.checkPassword(password)){
            presenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        userGateway.setCurrentUsername(username);
        LoginOutputData output = new LoginOutputData(user.getUsername());
        presenter.prepareSuccessView(output);
    }

    @Override
    public void switchToSignUpView() {
        presenter.switchToSignUpView();
    }

    @Override
    public void switchToHomePage() {
        presenter.switchToHomePage();
    }

}
