package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class representing a View Model in the application.
 * It provides property change support for its subclasses.
 * @param <T> The type of state the ViewModel handles.
 */
public abstract class ViewModel<StateType> {

    private StateType state;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    private String viewName;

    protected ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return this.viewName;
    }

    public StateType getState() {
        return this.state;
    }

    public void setState(StateType state) {
        // StateType old = this.state;
        this.state = state;
        // support.firePropertyChange("state", old, state); ?
    }

    // public void setViewName(String viewName) {
    //     String old = this.viewName;
    //     this.viewName = viewName;
    //     support.firePropertyChange("viewName", old, viewName);
    // }

    protected void firePropertyChange(String property, Object oldValue, Object newValue) {
        support.firePropertyChange(property, oldValue, newValue);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}