package interface_adapter.apply_promotion;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the Apply Promotion screen.
 */
public class ApplyPromotionViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ApplyPromotionState state = new ApplyPromotionState();

    public ApplyPromotionState getState() {
        return state;
    }

    public void setState(ApplyPromotionState state) {
        ApplyPromotionState old = this.state;
        this.state = state;
        support.firePropertyChange("state", old, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}