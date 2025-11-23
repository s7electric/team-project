package view;

import entity.User;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class creates the view for the search use case.
 * It contains a view name, a SearchViewModel and a SearchController and a string error.
 * */
public class SearchView extends JPanel implements PropertyChangeListener {
    private final String searchViewName = "Search";
    private SearchViewModel searchViewModel;
    private SearchController searchController;
    private JLabel errorLabel = new JLabel("");

    /**
     * Creates a SearchView object for the search use case.
     * @param searchViewModel the view model for the search use case
     * */
    public SearchView(SearchViewModel searchViewModel){
        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);

        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Search:");
        JTextField searchTextField = new JTextField(50);
        searchPanel.add(searchLabel);
        searchPanel.add(searchTextField);

        JPanel buttonsPanel = new JPanel();
        JButton enterButton = new JButton("Enter");
        JButton backButton = new JButton("Back");
        buttonsPanel.add(enterButton);
        buttonsPanel.add(backButton);

        JPanel errorPanel = new JPanel();
        this.errorLabel.setForeground(new Color(255,0,0));
        errorPanel.add(this.errorLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(searchPanel);
        mainPanel.add(errorPanel);
        mainPanel.add(buttonsPanel);

        enterButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String searchText = searchTextField.getText();
                        searchController.execute(searchText);
                    }
                }
        );

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchController.switchToHomepageView();
            }
        });

    }

    /**
     * Sets the controller for the search use case.
     * @param searchController the controller for search use case
     * */
    public void setController(SearchController searchController){
        this.searchController = searchController;
    }

    /**
     * Listens to the event of the property change to see if the process was successful or not
     * and updates the view accordingly
     * @param evt the event of the property change
     * */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState searchState = (SearchState) evt.getNewValue();
        if (searchState.getSearchTextSuccess() != null){
            this.searchController.switchToHomepageView();
        }
        if (searchState.getErrorFailure() != null){
            this.errorLabel.setText(searchState.getErrorFailure());
        } else {
            this.errorLabel.setText("");
        }
    }

    public String getViewName(){
        return this.searchViewName;
    }
}
