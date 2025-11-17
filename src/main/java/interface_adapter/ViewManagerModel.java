package interface_adapter;

public class ViewManagerModel extends ViewModel {
    private String activeViewName;

    public ViewManagerModel() {
        super("viewManager");
    }

    public String getActiveViewName() {
        return this.activeViewName;
    }

    // public void setActiveViewName(String viewName) {
    //     String old = this.activeViewName;
    //     this.activeViewName = viewName;
    //     firePropertyChange("activeView", old, viewName);
    // }
}
