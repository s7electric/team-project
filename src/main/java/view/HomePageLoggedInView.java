package view;

import interface_adapter.HomePagLoggedIN.HomePageLoggedInViewModel;
import interface_adapter.HomePagLoggedIN.HomePageLoggedInState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Simple view shown after a successful login.
 */
public class HomePageLoggedInView extends JPanel implements PropertyChangeListener {
    private final HomePageLoggedInViewModel viewModel;
    private final JLabel welcomeLabel = new JLabel();

    public HomePageLoggedInView(HomePageLoggedInViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setName(HomePageLoggedInViewModel.VIEW_NAME);
        setLayout(new GridBagLayout());
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(Font.BOLD, 18f));
        add(welcomeLabel);

        updateFromState(viewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }
        updateFromState((HomePageLoggedInState) evt.getNewValue());
    }

    private void updateFromState(HomePageLoggedInState state) {
        if (state == null) {
            return;
        }
        String username = state.getUsername() == null || state.getUsername().isEmpty()
                ? "user" : state.getUsername();
        welcomeLabel.setText("Welcome, " + username + "!");
    }

    public String getViewName() {
        return HomePageLoggedInViewModel.VIEW_NAME;
    }
}
