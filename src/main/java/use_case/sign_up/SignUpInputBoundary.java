package use_case.sign_up;

/**
 * This interface is for the SignUpInteractor to implement and for the SignUpController to call its methods
 * */
public interface SignUpInputBoundary {
    /**
     * Executes the signup use case using the input data
     * @param inputData the formatted input data needed for the execution of the signup use case
     * */
    public void execute(SignUpInputData inputData);
}
