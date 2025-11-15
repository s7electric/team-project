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
    public void execute(LoginInputData input){
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

        if (!userGateway.existsByName(username)){
            presenter.prepareFailView("Account " + username + " does not exist");
            return;
        }

        User user = userGateway.get(username);
        if (!user.checkPassword(password)){
            presenter.prepareFailView("Wrong password");
            return;
        }

        LoginOutputData output = new LoginOutputData(user.getUsername());
        presenter.prepareSuccessView(output);
    }
}
