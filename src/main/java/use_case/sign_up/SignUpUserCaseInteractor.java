package use_case.sign_up;

import data_access.DataAccessInterface;
import entity.User;

public class SignUpUserCaseInteractor implements SignUpInputBoundary{
    private SignUpOutputBoundary signUpPresenter;
    private DataAccessInterface dataAccess;

    public SignUpUserCaseInteractor(){
        this.signUpPresenter = new SignUpPresenter();
        this.dataAccess = new DataAccess();
    }

    public void execute(SignUpInputData inputData){
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        String email = inputData.getEmail();
        String billingAddress = inputData.getBillingAddress();

        try {
            User newUser = new User(username, password, email, billingAddress);
            dataAccess.createUser(newUser);


        } catch (IllegalArgumentException e) {
            SignUpOutputData outputData = new SignUpOutputData(e.getMessage());
            signUpPresenter.show(outputData);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
