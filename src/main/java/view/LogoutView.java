package view;

import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutState;
import interface_adapter.logout.LogoutViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogoutView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = LogoutViewModel.VIEW_NAME;
    private final LogoutViewModel logoutViewModel;

    private final JButton logout;
    private final JButton back;
    private final JLabel messageLabel;

    private LogoutController logoutController;
    private LogoutState logoutState;

    public LogoutView(LogoutViewModel logoutViewModel) {
        this.logoutViewModel = logoutViewModel;
        this.logoutState = logoutViewModel.getState();
        this.logoutViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JLabel title  = new JLabel("Log out");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        messageLabel = new JLabel(buildMessage(logoutState.getUsername()));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        logout = new JButton("Log out");
        buttons.add(logout);
        back = new JButton("Back");
        buttons.add(back);

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(logout) && logoutController != null) {
                    logoutController.execute();
                }
            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (logoutController != null) {
                    logoutController.switchToHomePage();
                }
            }
        });

        add(title);
        add(Box.createVerticalStrut(8));
        add(messageLabel);
        add(Box.createVerticalStrut(8));
        add(buttons);
    }

    private String buildMessage(String username) {
        String displayName = (username == null || username.isEmpty()) ? "user" : username;
        return "You are currently logged in as " + displayName + ". Do you want to log out?";
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Unused; required by interface
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"state".equals(evt.getPropertyName())) {
            return;
        }
        logoutState = (LogoutState) evt.getNewValue();
        messageLabel.setText(buildMessage(logoutState.getUsername()));
    }

    public String getViewName() {
        return viewName;
    }
}
