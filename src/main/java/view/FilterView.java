package view;

import entity.User;
import interface_adapter.filter.FilterController;
import interface_adapter.filter.FilterViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class creates the view for the filter use case.
 * It contains a view name, a FilterViewModel and a FilterController.
 * */
public class FilterView extends JPanel implements PropertyChangeListener {
    private final String filterViewName = "Filter";
    private FilterViewModel filterViewModel;
    private FilterController filterController;

    /**
     * Creates a FilterView object for the filter use case.
     * @param filterViewModel the view model for the filter use case
     * */
    public FilterView(FilterViewModel filterViewModel){
        this.filterViewModel = filterViewModel;
        this.filterViewModel.addPropertyChangeListener(this);

        JPanel filterPanel = new JPanel();
        JLabel filterLabel = new JLabel("Filter:");
        filterPanel.add(filterLabel);

        JPanel filterSelectionPanel = new JPanel();
        String[] filterOptions = {"All", "Most Popular", "Most Expensive", "Least Expensive", "Food", "Kitchen", "Outside", "House", "Electric Appliances", "Bedroom", "Bathroom", "Office", "Education", "Sports", "Health"};
        JComboBox<String>  filterComboBox = new JComboBox<>(filterOptions);
        filterSelectionPanel.add(filterComboBox);

        JPanel buttonsPanel = new JPanel();
        JButton selectButton = new JButton("Select");
        JButton backButton = new JButton("Back");
        buttonsPanel.add(selectButton);
        buttonsPanel.add(backButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(filterPanel);
        mainPanel.add(filterSelectionPanel);
        mainPanel.add(buttonsPanel);

        filterComboBox.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selectedFilter = (String) filterComboBox.getSelectedItem();
                        filterController.execute(selectedFilter);
                    }
                }
        );

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterController.switchToHomepageView();
            }
        });

    }

    /**
     * Sets the controller for the filter use case.
     * @param filterController the controller for filter use case
     * */
    public void setController(FilterController filterController){
        this.filterController = filterController;
    }

    /**
     * Listens to the event of the property change to see if the view has to change
     * @param evt the event of the property change
     * */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        filterController.switchToHomepageView();
    }

    public String getViewName(){
        return this.filterViewName;
    }
}
