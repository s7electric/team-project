package app;

import interface_adapter.HomePagLoggedIN.HomePageLoggedInViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.sign_up.SignUpViewModel;
import view.HomePageLoggedInView;
import view.LoginView;
import view.SignUpView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

/**
 * Application builder that wires views, view models, and use cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // From CA-LAB
    // DAO version using local file storage
    // final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);

    // DAO version using a shared external database
    // final DBUserDataAccessObject userDataAccessObject = new DBUserDataAccessObject(userFactory);

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private SignUpView signUpView;
    private SignUpViewModel signUpViewModel;

    private HomePageLoggedInView homePageLoggedInView;
    private HomePageLoggedInViewModel homePageLoggedInViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public JFrame build() {
        JFrame frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(cardPanel);
        viewManagerModel.setActiveViewName(LoginViewModel.VIEW_NAME);
        return frame;
    }
}
