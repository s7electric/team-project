package interface_adapter.manage_address;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Manage Address screen.
 * Notifies the UI when state changes.
 */
public class ManageAddressViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ManageAddressState state = new ManageAddressState();

    public ManageAddressState getState() { return state; }

    public void setState(ManageAddressState state) {
        ManageAddressState old = this.state;
        this.state = state;
        support.firePropertyChange("state", old, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}