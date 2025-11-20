package view;

import entity.User;
import interface_adapter.sign_up.SignUpController;
import interface_adapter.sign_up.SignUpViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class creates the view for the signup use case.
 * It contains a view name, a SignUpViewModel and a SignUpController and a string error.
 * */
public class SignUpView extends JPanel implements PropertyChangeListener {
    private final String signUpViewName = "sign up";
    private SignUpViewModel signUpViewModel;
    private SignUpController signUpController;
    private String error = "";

    /**
     * Creates a SignUpView object for the signup use case.
     * @param signUpViewModel the view model for the signup use case
     * */
    public SignUpView(SignUpViewModel signUpViewModel){
        this.signUpViewModel = signUpViewModel;
        this.signUpViewModel.addPropertyChangeListener(this);

        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTextField = new JTextField(10);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Password:");
        JTextField passwordTextField = new JTextField(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);

        JPanel password2Panel = new JPanel();
        JLabel password2Label = new JLabel("Confirm Password:");
        JTextField password2TextField = new JTextField(10);
        password2Panel.add(password2Label);
        password2Panel.add(password2TextField);

        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField(10);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);

        JPanel billingAddressPanel = new JPanel();
        JLabel billingAddressLabel = new JLabel("Billing Address:");
        JTextField billingAddressTextField = new JTextField(10);
        billingAddressPanel.add(billingAddressLabel);
        billingAddressPanel.add(billingAddressTextField);

        JPanel buttonsPanel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        buttonsPanel.add(createButton);
        buttonsPanel.add(loginButton);
        buttonsPanel.add(backButton);

        JPanel errorPanel = new JPanel();
        JLabel errorLabel = new JLabel(this.error);
        errorLabel.setForeground(new Color(255,0,0));
        errorPanel.add(errorLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(password2Panel);
        mainPanel.add(emailPanel);
        mainPanel.add(billingAddressPanel);
        mainPanel.add(errorPanel);
        mainPanel.add(buttonsPanel);

        createButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameTextField.getText();
                    String password = passwordTextField.getText();
                    String password2 = password2TextField.getText();
                    String email = emailTextField.getText();
                    String billingAddress = billingAddressTextField.getText();
                    if (password.equals(password2)){
                        signUpController.execute(username, password, email, billingAddress);
                    } else {
                        error = "Passwords do not match!";
                    }
                }
            }
        );

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpController.switchToLoginView();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpController.switchToLoggedOutView();
            }
        });

    }

    /**
     * Sets the controller for the signup use case.
     * @param signUpController the controller for signup use case
     * */
    public void setController(SignUpController signUpController){
        this.signUpController = signUpController;
    }

    /**
     * Listens to the event of the property change to see if the process was successful or not
     * and updates the view accordingly
     * @param evt the event of the property change
     * */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("SignUpSuccess")){
            User loggedInUser = this.signUpViewModel.getState().getSuccess();
            this.signUpController.switchToLoggedInView();
        } else if (evt.getPropertyName().equals("SgnUpFailure")){
            String error = this.signUpViewModel.getState().getFailure();
            this.error = error;
        }
    }

    public String getViewName(){
        return this.signUpViewName;
    }
}
