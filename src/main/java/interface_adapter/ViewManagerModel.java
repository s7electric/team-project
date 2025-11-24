package interface_adapter;

public class ViewManagerModel extends ViewModel<String> {
    private String activeViewName;

    public ViewManagerModel() {
        super("viewManager");
    }

    public String getActiveViewName() {
        return this.activeViewName;
    }

    public void setActiveViewName (String viewName) {
        this.activeViewName = viewName;
        setState(viewName);
    }
}
