package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class ViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String viewName;

    protected ViewModel(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        String old = this.viewName;
        this.viewName = viewName;
        support.firePropertyChange("viewName", old, viewName);
    }

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