package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/*
 * Manages the different views in the application using a CardLayout.
 * Listens for changes in the ViewManagerModel to switch views accordingly.
 */
public class ViewManager implements PropertyChangeListener {
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;
    private final CardLayout cardLayout;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelTypeString = (String) evt.getNewValue();
            cardLayout.show(views, viewModelTypeString);
        }
    }
}