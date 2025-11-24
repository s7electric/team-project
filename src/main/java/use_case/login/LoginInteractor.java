package use_case.login;

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

        if (username == null || username.isBlank()) {
            presenter.prepareFailView("Username must not be empty");
            return;
        }

        if (password == null || password.isBlank()) {
            presenter.prepareFailView("Password must not be empty");
            return;
        }

        boolean ok = userGateway.authenticate(username, password);
        if (!ok) {
            presenter.prepareFailView("Invalid credentials");
            return;
        }

        String resolvedUsername = userGateway.getCurrentUsername();
        if (resolvedUsername == null || resolvedUsername.isBlank()) {
            resolvedUsername = username;
        }
        LoginOutputData output = new LoginOutputData(resolvedUsername);
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
