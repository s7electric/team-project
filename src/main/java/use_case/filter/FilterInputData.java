package use_case.filter;

/**
 * This class represents the input data of the filter interactor
 * */
public class FilterInputData {
    private final String filter;

    /**
     * Creates FilterInputData object to wrap the filter that is specified by the user
     * */
    public FilterInputData(String filter){
        this.filter = filter;
    }

    public String getFilter(){
        return filter;
    }
}
