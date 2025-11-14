package use_case.sign_up;

/**
 * This class contains the formatted input data for the execution of the signup use case
 * SignUpInputData contains a username, a password, an email, a billing address.
 * */
public class SignUpInputData {
    private String username;
    private String password;
    private String email;
    private String billingAddress;

    /**
     * Creates an input data object for the signup use case
     * @param username the name of the customer
     * @param password the password of the customer
     * @param email the email of the customer
     * @param billingAddress the initial billing address of the customer
     * */
    public SignUpInputData(String username, String password, String email, String billingAddress){
        this.username = username;
        this.password = password;
        this.email = email;
        this.billingAddress = billingAddress;
    }

    public String getUsername(){return username;}

    public String getPassword(){return password;}

    public String getEmail(){return email;}

    public String getBillingAddress(){return billingAddress;}
}
