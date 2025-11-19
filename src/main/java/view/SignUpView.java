package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import interface_adapter.sign_up.SignUpViewModel;
import interface_adapter.sign_up.SignUpController;


/**
 * This class creates the view for the signup use case.
 * It contains a SignUpViewModel and a SignUpController and a string error.
 * */
public class SignUpView extends JPanel implements ActionListener, PropertyChangeListener {
    private final SignUpViewModel signUpViewModel;
    private SignUpController signUpController;
    private String error = "";

    /**
     * Creates a SignUpView object for the signup use case.
     * */
    public SignUpView(SignUpViewModel signUpViewModel) {
        this.signUpViewModel = signUpViewModel;
        SignUpViewModel.addPropertyChangeListener(this);

        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel(SignUpViewModel.USERNAME_LABEL);
        JTextField usernameTextField = new JTextField(10);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);

        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel(SignUpViewModel.PASSWORD_LABEL);
        JTextField passwordTextField = new JTextField(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);

        JPanel password2Panel = new JPanel();
        JLabel password2Label = new JLabel(SignUpViewModel.PASSWORD2_LABEL);
        JTextField password2TextField = new JTextField(10);
        password2Panel.add(password2Label);
        password2Panel.add(password2TextField);

        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel(SignUpViewModel.EMAIL_LABEL);
        JTextField emailTextField = new JTextField(10);
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextField);

        JPanel billingAddressPanel = new JPanel();
        JLabel billingAddressLabel = new JLabel(SignUpViewModel.BILLING_ADDRESS_LABEL);
        JTextField billingAddressTextField = new JTextField(10);
        billingAddressPanel.add(billingAddressLabel);
        billingAddressPanel.add(billingAddressTextField);

        JPanel buttonsPanel = new JPanel();
        JButton createButton = new JButton(SignUpViewModel.CREATE_BUTTON_LABEL);
        JButton loginButton = new JButton(SignUpViewModel.LOGIN_BUTTON_LABEL);
        buttonsPanel.add(createButton);
        buttonsPanel.add(loginButton);

        JPanel errorPanel = new JPanel();
        JLabel errorLabel = new JLabel(this.error);
        errorLabel.setForeground(new Color(255, 0, 0));
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

        createButton.addActionListener(event -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String password2 = password2TextField.getText();
            String email = emailTextField.getText();
            String billingAddress = billingAddressTextField.getText();
            if (password.equals(password2)) {
                this.signUpController.signUp(username, password, email, billingAddress);
            } else {
                this.error = "Passwords do not match!";
            }
        });

        loginButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        signUpController.switchToLoginView();
                    }
                }
        );
    }

    /**
     * Listens to the event of the property change to see if the process was successful or not
     * and updates the view accordingly
     * @param evt the event of the property change
     * */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: update signup state display once SignUpViewModel is integrated.
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
