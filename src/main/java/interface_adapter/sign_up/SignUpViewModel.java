package interface_adapter.sign_up;

import interface_adapter.ViewModel;

public class SignUpViewModel extends ViewModel {
    public SignUpViewModel(){
        super("Sign up");
        this.setState(new SignUpState());
    }
}
