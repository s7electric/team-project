package app;

import data_access.DataAccessObject;
import entity.Product;
import interface_adapter.Product.ProductController;
import interface_adapter.Product.ProductPresenter;
import interface_adapter.Product.ProductState;
import interface_adapter.Product.ProductViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_to_cart.AddToCartController;
import interface_adapter.add_to_cart.AddToCartPresenter;
import interface_adapter.add_to_cart.AddToCartViewModel;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterPresenter;
import interface_adapter.filter.FilterState;
import interface_adapter.filter.FilterViewModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepagePresenter;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.make_listing.MakeListingController;
import interface_adapter.make_listing.MakeListingPresenter;
import interface_adapter.make_listing.MakeListingViewModel;
import interface_adapter.manage_address.ManageAddressController;
import interface_adapter.manage_address.ManageAddressPresenter;
import interface_adapter.manage_address.ManageAddressState;
import interface_adapter.manage_address.ManageAddressViewModel;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.sign_up.SignUpController;
import interface_adapter.sign_up.SignUpPresenter;
import interface_adapter.sign_up.SignUpState;
import interface_adapter.sign_up.SignUpViewModel;
import use_case.add_to_cart.AddToCartInteractor;
import use_case.add_to_cart.AddToCartOutputBoundary;
import use_case.checkout.CheckoutDataAccessInterface;
import use_case.checkout.CheckoutInputData;
import use_case.checkout.CheckoutInteractor;
import use_case.checkout.CheckoutOutputBoundary;
import use_case.filter.FilterInputBoundary;
import use_case.filter.FilterInteractor;
import use_case.filter.FilterOutputBoundary;
import use_case.homepage.HomepageInteractor;
import use_case.login.LoginInteractor;
import use_case.logout.LogoutInteractor;
import use_case.make_listing.MakeListingInteractor;
import use_case.manage_address.AddAddressInteractor;
import use_case.manage_address.DeleteAddressInteractor;
import use_case.manage_address.EditAddressInteractor;
import use_case.open_product.OpenProductInteractor;
import use_case.open_product.OpenProductOutputBoundary;
import use_case.search.SearchInputBoundary;
import use_case.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import use_case.sign_up.SignUpInputBoundary;
import use_case.sign_up.SignUpInteractor;
import use_case.sign_up.SignUpOutputBoundary;
import interface_adapter.checkout.CheckoutController;
import interface_adapter.checkout.CheckoutPresenter;
import interface_adapter.checkout.CheckoutViewModel;
import view.OrderConfirmationWindow;
import view.PaymentWindow;
import view.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.CardLayout;
import java.util.*;

/**
 * App builder with explicit add* methods for views and use cases.
 * Views are only added when their respective add*View methods are called.
 * Use cases/controllers are wired via add*UseCase methods.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final DataAccessObject dataAccessObject = new DataAccessObject();
    private final DataAccessObject dataAccessObject2 = new DataAccessObject();
    // View models

    private LoginViewModel loginViewModel = new LoginViewModel();
    private SignUpViewModel signUpViewModel = new SignUpViewModel();
    private HomepageViewModel homepageViewModel = new HomepageViewModel();
    private HomepageState homepageState;
    private LogoutViewModel logoutViewModel =  new LogoutViewModel();
    private SearchViewModel searchViewModel =  new SearchViewModel();
    private FilterViewModel filterViewModel =  new FilterViewModel();
    private ProductViewModel productViewModel =  new ProductViewModel();
    private ProductState productState;
    private AddToCartViewModel addToCartViewModel = new AddToCartViewModel();
    private ManageAddressViewModel manageAddressViewModel = new ManageAddressViewModel();
    private CheckoutPresenter checkoutPresenter;
    private CheckoutViewModel checkoutViewModel;
    private MakeListingViewModel makeListingViewModel = new MakeListingViewModel();

    // Views
    private LoginView loginView;
    private SignUpView signUpView;
    private HomepageView homepageView;
    private LogoutView logoutView;
    private ProductView productView;
    private ManageAddressView manageAddressView;
    private SearchView searchView;
    private FilterView filterView;
    private MakeListingView makeListingView;

    // Controllers / interactors shared
    private LoginController loginController;
    private SignUpController signUpController;
    private HomepageController homepageController;
    private LogoutController logoutController;
    private CheckoutController checkoutController;
    private CheckoutInteractor checkoutInteractor;
    private ProductController productController;
    private AddToCartController addToCartController;
    private AddToCartInteractor addToCartInteractor;
    private OpenProductInteractor openProductInteractor;
    private FilterController filterController;
    private SearchController searchController;
    private MakeListingController makeListingController;
    private MakeListingInteractor makeListingInteractor;

    private Runnable openManageAddress;
    private Runnable openCart;


    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /* ---------- View adders ---------- */

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addSignUpView() {
        signUpViewModel = new SignUpViewModel();
        signUpView = new SignUpView(signUpViewModel);
        cardPanel.add(signUpView, signUpView.getViewName());
        return this;
    }

    public AppBuilder addHomepageView() {
        homepageState = new HomepageState(null);
        homepageViewModel = new HomepageViewModel();
        homepageViewModel.setState(homepageState);
        homepageView = new HomepageView(homepageViewModel);
        cardPanel.add(homepageView, homepageView.getViewName());
        // Load products at boot so homepage has content
        loadProductsIntoHomepage();
        return this;
    }

    @SuppressWarnings("checkstyle:FinalLocalVariable")
    private void loadProductsIntoHomepage() {
        if (homepageViewModel == null) {
            return;
        }
        HomepageState current = homepageViewModel.getState();
        HomepageState next = new HomepageState(current == null ? null : current.getUsername());
        next.setSearchText("All");
        var products = dataAccessObject.getAllProducts();
        if (products == null || products.isEmpty()) {
            System.err.println("[App] No products loaded. Check network/API access.");
            products = java.util.Collections.emptyList();
        }
        Map<String, List<Object>> mappedProducts = new LinkedHashMap<>();
        for (Product p: products){
            if (!mappedProducts.containsKey(p.getProductUUID())){
                mappedProducts.put(p.getProductUUID(), new ArrayList<>(Arrays.asList(p.getName(), p.getimageBase64(), p.getPrice())));
            }
        }
        next.setProducts(mappedProducts);
        homepageViewModel.setState(next);
    }

    public AppBuilder addLogoutView() {
        logoutViewModel = new LogoutViewModel();
        logoutView = new LogoutView(logoutViewModel);
        cardPanel.add(logoutView, logoutView.getViewName());
        return this;
    }

    public AppBuilder addProductView() {
        productState = new ProductState();
        productView = new ProductView(productViewModel, addToCartViewModel);
        cardPanel.add(productView, productView.getViewName());
        return this;
    }

    public AppBuilder addMakeListingView() {
        makeListingViewModel = new MakeListingViewModel();
        makeListingView = new MakeListingView(makeListingViewModel);
        cardPanel.add(makeListingView, makeListingView.getViewName());
        return this;
    }

    /**
        * Manage Address uses a separate window; call this to construct it.
        */
    public AppBuilder addManageAddressWindow() {
        manageAddressViewModel = new ManageAddressViewModel();
        manageAddressViewModel.setState(new ManageAddressState());
        return this;
    }

    public AppBuilder addSearchView() {
        searchViewModel = new SearchViewModel();
        searchView = new SearchView(searchViewModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    public AppBuilder addFilterView() {
        filterViewModel = new FilterViewModel();
        filterView = new FilterView(filterViewModel);
        cardPanel.add(filterView, filterView.getViewName());
        return this;
    }

    /* ---------- Use case wiring ---------- */

    public AppBuilder addLoginUseCase() {
        LoginPresenter presenter = new LoginPresenter(viewManagerModel, homepageViewModel, loginViewModel, signUpViewModel);
        LoginInteractor interactor = new LoginInteractor(dataAccessObject, presenter);
        loginController = new LoginController(interactor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addSignUpUseCase() {;
        SignUpOutputBoundary presenter = new SignUpPresenter(signUpViewModel, new SignUpState(), viewManagerModel, loginViewModel, homepageViewModel, homepageState);
        SignUpInputBoundary interactor = new SignUpInteractor(presenter, dataAccessObject);
        signUpController = new SignUpController(interactor);
        signUpView.setController(signUpController);
        return this;
    }

    public AppBuilder addHomepageUseCase() {
        if (openManageAddress == null) {
            openManageAddress = () -> JOptionPane.showMessageDialog(null, "Manage Address not configured.", "Not configured", JOptionPane.WARNING_MESSAGE);
        }
        if (openCart == null) {
            openCart = () -> JOptionPane.showMessageDialog(null, "Cart/Checkout not configured.", "Not configured", JOptionPane.WARNING_MESSAGE);
        }

        HomepagePresenter presenter = new HomepagePresenter(
                signUpViewModel, viewManagerModel, loginViewModel, homepageViewModel,
                homepageState, productViewModel, productState,
                searchViewModel, filterViewModel, logoutViewModel, makeListingViewModel,
                openManageAddress, openCart);
        HomepageInteractor interactor = new HomepageInteractor(presenter, dataAccessObject);
        homepageController = new HomepageController(interactor);
        homepageView.setController(homepageController);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        LogoutPresenter presenter = new LogoutPresenter(viewManagerModel, homepageViewModel, loginViewModel, logoutViewModel);
        LogoutInteractor interactor = new LogoutInteractor(dataAccessObject, presenter);
        logoutController = new LogoutController(interactor);
        logoutView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addManageAddressUseCase() {
        if (manageAddressViewModel == null) {
            manageAddressViewModel = new ManageAddressViewModel();
            manageAddressViewModel.setState(new ManageAddressState());
        }
        ManageAddressPresenter manageAddressPresenter = new ManageAddressPresenter(manageAddressViewModel);
        AddAddressInteractor addAddressInteractor = new AddAddressInteractor(dataAccessObject, manageAddressPresenter);
        EditAddressInteractor editAddressInteractor = new EditAddressInteractor(dataAccessObject, manageAddressPresenter);
        DeleteAddressInteractor deleteAddressInteractor = new DeleteAddressInteractor(dataAccessObject, manageAddressPresenter);
        ManageAddressController manageAddressController =
                new ManageAddressController(addAddressInteractor, editAddressInteractor, deleteAddressInteractor);
        if (manageAddressView == null) {
            manageAddressView = new ManageAddressView(manageAddressController, manageAddressViewModel);
        }
        openManageAddress = () -> {
            String currentUser = dataAccessObject.getCurrentUsername();
            if (currentUser == null || currentUser.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please log in to manage addresses.", "Not logged in", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ManageAddressState state = new ManageAddressState();
            state.setUsername(currentUser);
            if (dataAccessObject.getAddresses(currentUser) != null) {
                state.setAddresses(new java.util.ArrayList<>(dataAccessObject.getAddresses(currentUser)));
            }
            manageAddressViewModel.setState(state);
            manageAddressView.setVisible(true);
        };
        return this;
    }

    public AppBuilder addFilterUseCase() {
        FilterOutputBoundary filterPresenter = new FilterPresenter(filterViewModel, new FilterState(), viewManagerModel, homepageViewModel, homepageState);
        FilterInputBoundary filterInteractor = new FilterInteractor(filterPresenter, dataAccessObject);
        filterController = new FilterController(filterInteractor);
        filterView.setController(filterController);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        SearchOutputBoundary searchPresenter = new SearchPresenter(searchViewModel, new SearchState(), viewManagerModel, homepageViewModel, homepageState);
        SearchInputBoundary searchInteractor = new SearchInteractor(searchPresenter, dataAccessObject);
        searchController = new SearchController(searchInteractor);
        searchView.setController(searchController);
        return this;
    }

    public AppBuilder addCheckoutUseCase() {
        // Create the presenter
        checkoutPresenter = new CheckoutPresenter();

        // Create the interactor with the presenter
        checkoutInteractor = new CheckoutInteractor((CheckoutDataAccessInterface) dataAccessObject, checkoutPresenter);

        // Create the controller
        checkoutController = new CheckoutController(checkoutInteractor);

        // Set up the openCart runnable to trigger checkout
        openCart = () -> {
            String currentUser = dataAccessObject.getCurrentUsername();
            if (currentUser == null || currentUser.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please log in to view your cart.", "Not logged in", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create the OrderConfirmationWindow with the presenter
            OrderConfirmationWindow orderWindow = new OrderConfirmationWindow(checkoutPresenter);

            // Execute the checkout use case - this will populate the window with data
            checkoutController.executeCheckout(currentUser);
        };

        return this;
    }
    public AppBuilder addMakeListingUseCase() {
        MakeListingPresenter presenter = new MakeListingPresenter(viewManagerModel, makeListingViewModel, homepageViewModel);
        makeListingInteractor = new MakeListingInteractor(presenter, dataAccessObject);
        makeListingController = new MakeListingController(makeListingInteractor);
        makeListingView.setController(makeListingController);
        return this;
    }

    public AppBuilder addProductUseCase() {
        final OpenProductOutputBoundary productPresenter =
                new ProductPresenter(viewManagerModel, productViewModel, homepageViewModel);
        final AddToCartOutputBoundary addToCartPresenter =
                new AddToCartPresenter(viewManagerModel, addToCartViewModel);
        openProductInteractor = new OpenProductInteractor(dataAccessObject, productPresenter);
        addToCartInteractor = new AddToCartInteractor(dataAccessObject, addToCartPresenter, dataAccessObject2);
        productController = new ProductController(openProductInteractor);
        addToCartController = new AddToCartController(addToCartInteractor);
        productView.setProductController(productController);
        productView.setAddToCartController(addToCartController);
        return this;
    }


    /* ---------- Build ---------- */

    public JFrame build() {
        JFrame frame = new JFrame("Shopping App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(cardPanel);
        viewManagerModel.setActiveViewName(homepageViewModel.getViewName());
        return frame;
    }

}
