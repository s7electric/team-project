package view;

import entity.Product;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Collections;
import java.util.List;


/**
 * This class creates the view for the homepage use case.
 * It contains a view name, a HomepageViewModel and a HomepageController, a variable panel for the header and a product showcase panel.
 * */
public class HomepageView extends JPanel implements PropertyChangeListener {
    private final String homepageViewName = HomepageViewModel.VIEW_NAME;
    private HomepageViewModel homepageViewModel;
    private HomepageController homepageController;
    private JPanel variablePanel = new JPanel();
    private JScrollPane productShowcaseScroll = new JScrollPane();

    /**
     * Creates a HomepageView object for the homepage use case.
     * @param homepageViewModel the view model for the homepage use case
     * */
    public HomepageView(HomepageViewModel homepageViewModel){
        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);

        JPanel buttonsLayerTwoPanel = new JPanel();
        JButton dealsButton = new JButton("Daily Deals");
        JButton searchButton = new JButton("Search");
        JButton filterButton = new JButton("Apply Filters");
        buttonsLayerTwoPanel.add(dealsButton);
        buttonsLayerTwoPanel.add(searchButton);
        buttonsLayerTwoPanel.add(filterButton);

//        dealsButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                homepageController.switchToDealsView();
//            }
//        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToSearchView();
            }
        });

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToFilterView();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(variablePanel);
        mainPanel.add(buttonsLayerTwoPanel);
        mainPanel.add(productShowcaseScroll);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

    }

    /**
     * Sets the controller for the homepage use case.
     * @param homepageController the controller for homepage use case
     * */
    public void setController(HomepageController homepageController){
        this.homepageController = homepageController;
    }

    /**
     * Listens to the event of the property change to updates the view accordingly
     * @param evt the event of the property change
     * */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        HomepageState homepageState = (HomepageState) evt.getNewValue();
        if (homepageState.getUsername() != null && !homepageState.getUsername().isEmpty()){
            createLoggedInPanel(homepageState);
        } else {
            createLoggedOutPanel();
        }
        createShowcaseScrollPanel(homepageState);
    }

    // Creates product panels for each product in the filtered product list
    private void createShowcaseScrollPanel(HomepageState homepageState) {
        if (homepageState == null) {
            return;
        }
        String searchText = homepageState.getSearchText();
        JLabel searchTextLabel = new JLabel("Current Filter/Search: " + searchText);
        List<Product> products = homepageState.getProducts();
        if (products == null) {
            products = Collections.emptyList();
        }
        JPanel productShowcasePanel = new JPanel();
        productShowcasePanel.setLayout(new BoxLayout(productShowcasePanel, BoxLayout.Y_AXIS));
        productShowcasePanel.add(searchTextLabel);
        for  (Product product : products) {
            JPanel productPanel = new JPanel();
            JLabel imageLabel;
            try {
                imageLabel = new JLabel(new ImageIcon(ImageIO.read(new URL(product.getImageUrl()))));
            } catch (Exception e){
                imageLabel = new JLabel("No Image");
            }
            JLabel productLabel = new JLabel(product.getName());
            JLabel productPriceLabel = new JLabel(Double.toString(product.getPrice()));
            JButton productInfoButton = new JButton("Info");
            productPanel.add(imageLabel);
            productPanel.add(productLabel);
            productPanel.add(productPriceLabel);
            productPanel.add(productInfoButton);
            productShowcasePanel.add(productPanel);
            productInfoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    homepageController.switchToInfoView(product.getProductUUID(),homepageState.getUsername());
                }
            });
        }
        if (this.productShowcaseScroll == null) {
            this.productShowcaseScroll = new JScrollPane(productShowcasePanel);
            add(this.productShowcaseScroll, BorderLayout.SOUTH);
        } else {
            this.productShowcaseScroll.setViewportView(productShowcasePanel);
        }
        this.productShowcaseScroll.revalidate();
        this.productShowcaseScroll.repaint();
    }

    // Creates the header for when the user is not logged in or signed up
    private void createLoggedOutPanel() {
        this.variablePanel.removeAll();
        JPanel welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Hey there! Do you have an account?"));
        JPanel buttonsLayerOnePanel = new JPanel();
        JButton signUpButton = new JButton("Sign Up");
        JButton loginButton = new JButton("Login");
        buttonsLayerOnePanel.add(signUpButton);
        buttonsLayerOnePanel.add(loginButton);
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToSignUpView();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToLoginView();
            }
        });
        this.variablePanel.setLayout(new BoxLayout(this.variablePanel, BoxLayout.Y_AXIS));
        this.variablePanel.add(welcomePanel);
        this.variablePanel.add(buttonsLayerOnePanel);
        this.variablePanel.revalidate();
        this.variablePanel.repaint();
    }

    // Creates the header for when the user is logged in or signed up
    private void createLoggedInPanel(HomepageState homepageState) {
        this.variablePanel.removeAll();
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(new JLabel(homepageState.getUsername()));
        JPanel buttonsLayerOnePanel = new JPanel();
        JButton listingButton = new JButton("Create Listing");
        JButton addressButton = new JButton("Manage Addresses");
        JButton cartButton = new JButton("Cart");
        JButton fundButton = new JButton("Add Funds");
        JButton logoutButton = new JButton("Logout");
        buttonsLayerOnePanel.add(listingButton);
        buttonsLayerOnePanel.add(addressButton);
        buttonsLayerOnePanel.add(fundButton);
        buttonsLayerOnePanel.add(cartButton);
        buttonsLayerOnePanel.add(logoutButton);
        listingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToListingView();
            }
        });
        addressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToAddressView();
            }
        });
        fundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToFundView();
            }
        });
        cartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToCartView();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                homepageController.switchToLogoutView();
            }
        });
        this.variablePanel.setLayout(new BoxLayout(this.variablePanel, BoxLayout.Y_AXIS));
        this.variablePanel.add(usernamePanel);
        this.variablePanel.add(buttonsLayerOnePanel);
        this.variablePanel.revalidate();
        this.variablePanel.repaint();
    }

    public String getViewName(){
        return this.homepageViewName;
    }
}
