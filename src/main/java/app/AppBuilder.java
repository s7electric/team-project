package app;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.sign_up.SignUpViewModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutController;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepagePresenter;
import interface_adapter.homepage.HomepageState;
import interface_adapter.sign_up.SignUpController;
import interface_adapter.sign_up.SignUpPresenter;
import interface_adapter.sign_up.SignUpState;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchController;
import interface_adapter.manage_address.ManageAddressViewModel;
import interface_adapter.manage_address.ManageAddressPresenter;
import interface_adapter.manage_address.ManageAddressController;
import use_case.filter.FilterOutputBoundary;
import use_case.login.LoginInteractor;
import use_case.logout.LogoutInteractor;
import use_case.homepage.HomepageInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.homepage.HomepageInputBoundary;
import use_case.homepage.HomepageOutputBoundary;
import use_case.sign_up.SignUpInteractor;
import use_case.sign_up.SignUpOutputBoundary;
import use_case.sign_up.SignUpInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterDataAccessInterface;
import use_case.filter.FilterInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchOutputBoundary;
import use_case.manage_address.AddAddressInteractor;
import use_case.manage_address.DeleteAddressInteractor;
import use_case.manage_address.EditAddressInteractor;
import use_case.manage_address.AddAddressInputBoundary;
import use_case.manage_address.DeleteAddressInputBoundary;
import use_case.manage_address.EditAddressInputBoundary;
import use_case.manage_address.AddAddressOutputBoundary;
import use_case.manage_address.DeleteAddressOutputBoundary;
import use_case.manage_address.EditAddressOutputBoundary;
import data_access.SupabaseAuthGateway;
import data_access.SupabaseSession;
import data_access.SupabaseProductGateway;
import data_access.SupabaseAddressGateway;
import view.FilterView;
import view.SearchView;
import view.LoginView;
import view.LogoutView;
import view.SignUpView;
import view.HomepageView;
import view.ManageAddressView;
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

    private LoginView loginView;
    private LoginViewModel loginViewModel;

    private SignUpView signUpView;
    private SignUpViewModel signUpViewModel;

    private HomepageView homepageView;
    private HomepageViewModel homePageLoggedInViewModel;

    private LogoutView logoutView;
    private LogoutViewModel logoutViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addLoginView() {
        // Config
        final String supabaseUrl = System.getenv("SUPABASE_URL");
        final String supabaseKey = System.getenv("SUPABASE_KEY");
        SupabaseSession session = new SupabaseSession();
        SupabaseAuthGateway authGateway = new SupabaseAuthGateway(supabaseUrl, supabaseKey, session);
        SupabaseProductGateway productGateway = new SupabaseProductGateway(supabaseUrl, supabaseKey, session);
        SupabaseAddressGateway addressGateway = new SupabaseAddressGateway(supabaseUrl, supabaseKey, session);

        loginViewModel = new LoginViewModel();
        signUpViewModel = new SignUpViewModel();
        homePageLoggedInViewModel = new HomepageViewModel();
        logoutViewModel = new LogoutViewModel();
        FilterViewModel filterViewModel = new FilterViewModel();
        SearchViewModel searchViewModel = new SearchViewModel();
        ManageAddressViewModel manageAddressViewModel = new ManageAddressViewModel();
        HomepageState homepageState = new HomepageState("");
        homepageView = new HomepageView(homePageLoggedInViewModel);
        HomepageOutputBoundary homepagePresenter = new HomepagePresenter(
                signUpViewModel,
                viewManagerModel,
                loginViewModel,
                homePageLoggedInViewModel,
                homepageState,
                manageAddressViewModel,
                filterViewModel,
                searchViewModel
        );
        HomepageInputBoundary homepageInteractor = new HomepageInteractor(homepagePresenter);
        HomepageController homepageController = new HomepageController(homepageInteractor);
        homepageView.setController(homepageController);
        cardPanel.add(homepageView, HomepageView.VIEW_NAME);
        // Load initial products into homepage state
        homepageState.setProducts(productGateway.getAllProducts());
        homePageLoggedInViewModel.setState(homepageState);

        // Filter/Search wiring
        FilterOutputBoundary filterPresenter = new FilterPresenter(filterViewModel, new interface_adapter.filter.FilterState(), viewManagerModel, homePageLoggedInViewModel, homepageState);
        FilterInputBoundary filterInteractor = new FilterInteractor(filterPresenter, productGateway);
        FilterView filterView = new FilterView(filterViewModel);
        filterView.setController(new interface_adapter.filter.FilterController(filterInteractor));
        cardPanel.add(filterView, filterView.getViewName());

        SearchOutputBoundary searchPresenter = new SearchPresenter(searchViewModel, homePageLoggedInViewModel, homepageState, viewManagerModel);
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, productGateway);
        SearchController searchController = new SearchController(searchInteractor);
        SearchView searchView = new SearchView(searchViewModel);
        searchView.setController(searchController);
        cardPanel.add(searchView, searchView.getViewName());

        // Manage Address wiring
        ManageAddressPresenter manageAddressPresenter = new ManageAddressPresenter(manageAddressViewModel);
        AddAddressInputBoundary addAddressInteractor = new AddAddressInteractor(addressGateway, manageAddressPresenter);
        EditAddressInputBoundary editAddressInteractor = new EditAddressInteractor(addressGateway, manageAddressPresenter);
        DeleteAddressInputBoundary deleteAddressInteractor = new DeleteAddressInteractor(addressGateway, manageAddressPresenter);
        ManageAddressController manageAddressController = new ManageAddressController(addAddressInteractor, editAddressInteractor, deleteAddressInteractor);
        ManageAddressView manageAddressView = new ManageAddressView(manageAddressController, manageAddressViewModel);
        cardPanel.add(manageAddressView, ManageAddressViewModel.VIEW_NAME);

        LoginOutputBoundary loginPresenter = new LoginPresenter(viewManagerModel, homePageLoggedInViewModel, loginViewModel, signUpViewModel);
        LoginInputBoundary loginInteractor = new LoginInteractor(authGateway, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        loginView = new LoginView(loginViewModel);
        loginView.setLoginController(loginController);
        cardPanel.add(loginView, loginView.getViewName());

        SignUpState signUpState = new SignUpState();
        SignUpOutputBoundary signUpPresenter = new SignUpPresenter(signUpViewModel, signUpState, viewManagerModel, loginViewModel, homePageLoggedInViewModel, homepageState);
        SignUpInputBoundary signUpInteractor = new SignUpInteractor(signUpPresenter, authGateway);
        SignUpController signUpController = new SignUpController(signUpInteractor);
        signUpView = new SignUpView(signUpViewModel);
        signUpView.setController(signUpController);
        cardPanel.add(signUpView, signUpView.getViewName());

        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(viewManagerModel, homePageLoggedInViewModel, loginViewModel);
        LogoutInputBoundary logoutInteractor = new LogoutInteractor(session, logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor);
        logoutView = new LogoutView(logoutViewModel);
        logoutView.setLogoutController(logoutController);
        cardPanel.add(logoutView, logoutView.getViewName());

        return this;
    }

    public JFrame build() {
        JFrame frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(cardPanel);
        viewManagerModel.setActiveViewName(HomepageView.VIEW_NAME);
        return frame;
    }
}
