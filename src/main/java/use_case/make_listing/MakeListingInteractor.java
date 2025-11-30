package use_case.make_listing;

import entity.Product;
import java.util.UUID;
import java.util.Base64;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MakeListingInteractor implements MakeListingInputBoundary {
    
    private final MakeListingOutputBoundary presenter;
    private final MakeListingDataAccessInterface dataAccessInterface;
    
    public MakeListingInteractor(MakeListingOutputBoundary presenter,
                                 MakeListingDataAccessInterface dataAccessInterface) {
        this.presenter = presenter;
        this.dataAccessInterface = dataAccessInterface;
    }

    /**
     * Execute implementation
     */
    public void execute(MakeListingInputData inputData) {
        Product product;
        try {
            product = new Product(
                inputData.getProductName(),
                inputData.getPrice(),
                UUID.randomUUID().toString(),
                Base64.getEncoder().encodeToString(
                    Files.readAllBytes(Paths.get(inputData.getFilePath()))
                ),
                dataAccessInterface.getUser(inputData.getSellerName()),
                inputData.getCategory()
            );
        } catch (IOException e) {
            presenter.prepareFailView(new MakeListingOutputData(
                "Failed to create listing; \n" + e.getMessage() + "\n Please try again.", 
                inputData.getSellerName()
            ));
            return;
        }

        dataAccessInterface.postListing(product);
        presenter.prepareSuccessView(new MakeListingOutputData(
            product.getName() + " listed successfully!", 
            inputData.getSellerName()));
    }

    @Override
    public void switchToHomePageView() {
        presenter.switchToHomePageView();
    }
}