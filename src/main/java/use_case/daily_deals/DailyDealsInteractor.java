package use_case.daily_deals;

import entity.Product;

import java.util.ArrayList;
import java.util.List;

public class DailyDealsInteractor implements DailyDealsInputBoundary {
    private final DailyDealsProductDataAccessInterface productDataAccessInterface;
    private final DailyDealsDataAccessInterface dailyDealsDataAccessInterface;
    private final DailyDealsOutputBoundary userPresenter;

    public DailyDealsInteractor(DailyDealsProductDataAccessInterface productDataAccessInterface, DailyDealsDataAccessInterface dailyDealsDataAccess, DailyDealsOutputBoundary userPresenter) {
        this.productDataAccessInterface  = productDataAccessInterface;
        this.dailyDealsDataAccessInterface = dailyDealsDataAccess;
        this.userPresenter = userPresenter;
    }
    @Override
    public void execute() {
        final List<DealInfo> discountedProducts = dailyDealsDataAccessInterface.getDailyDeals();
        ArrayList<DailyDealsOutputData.DealItem> dealItems = new ArrayList<>();
        for (DealInfo dealInfo : discountedProducts) {
            Product product = productDataAccessInterface.getProductByID(dealInfo.getProductid());
            DailyDealsOutputData.DealItem dealItem = new DailyDealsOutputData.DealItem(product.getProductid(),product.getName(),product.getImageUrl(),product.getPrice(),dealInfo.getDiscountPercentage());
            dealItems.add(dealItem);
        }
        DailyDealsOutputData dailyDealsOutputData = new DailyDealsOutputData(dealItems);
        userPresenter.prepareSuccessView(dailyDealsOutputData);
    }
}