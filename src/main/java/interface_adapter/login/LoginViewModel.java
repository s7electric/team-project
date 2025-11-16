package interface_adapter.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel {
    public static final String VIEW_NAME = "login";
    private LoginState state = new LoginState();

    public LoginViewModel() {
        super(VIEW_NAME);
    }

    public LoginState getState() {
        return state;
    }

    public void setState(LoginState state) {
        LoginState oldState = this.state;
        this.state = state;
        firePropertyChange("state", oldState, state);
    }
}
