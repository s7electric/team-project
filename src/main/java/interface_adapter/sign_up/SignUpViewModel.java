package interface_adapter.sign_up;

import interface_adapter.ViewModel;

/**
 * This class extends the ViewModel and initialise it for the signup use case
 * */
public class SignUpViewModel extends ViewModel<SignUpState> {
    public static final String VIEW_NAME = "Sign up";
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Username:";
    public static final String PASSWORD_LABEL = "Password:";
    public static final String PASSWORD2_LABEL = "Confirm Password:";
    public static final String EMAIL_LABEL = "Email:";
    public static final String BILLING_ADDRESS_LABEL = "Billing Address:";

    public static final String CREATE_BUTTON_LABEL = "Sign up";
    public static final String LOGIN_BUTTON_LABEL = "Login";

    /**
     * Creates a SignUpViewModel object for the updating the SignUpView
     * */
    public SignUpViewModel() {
        super(VIEW_NAME);
        setState(new SignUpState());
    }
}
