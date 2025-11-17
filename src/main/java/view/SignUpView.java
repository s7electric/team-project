package view;

import javax.swing.*;

public class SignUpView {
    private final String title = "Sign Up";
    private final int x_dimension = 300;
    private final int y_dimension = 300;
    private SignUpViewModel signUpViewModel;
    public SignUpView(){
        signUpViewModel = new SignUpViewModel();
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setMinimumSize(new java.awt.Dimension(x_dimension, y_dimension));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

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
            buttonsPanel.add(createButton);
            buttonsPanel.add(loginButton);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(usernamePanel);
            mainPanel.add(passwordPanel);
            mainPanel.add(password2Panel);
            mainPanel.add(emailPanel);
            mainPanel.add(billingAddressPanel);
            mainPanel.add(buttonsPanel);

            frame.add(mainPanel);

            createButton.addActionListener(event -> {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                String password2 = password2TextField.getText();
                String email = emailTextField.getText();
                String billingAddress = billingAddressTextField.getText();
                if (password.equals(password2)){
                    SignUpController signUpController = new SignUpController();
                    signUpController.execute(username, password, email, billingAddress);
                }
            });

            loginButton.addActionListener(event -> {new LoginView();});



        });
    }

    public static void main(String[] args) {
        new SignUpView();
    }
}
