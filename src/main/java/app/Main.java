package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addSignUpView()
                .addHomepageView()
                .addLogoutView()
                .addProductView()
                .addMakeListingView()
                .addManageAddressWindow()
                .addFilterView()
                .addSearchView()
                // Wire use cases
                .addLoginUseCase()
                .addSignUpUseCase()
                .addManageAddressUseCase()
                .addSearchUseCase()
                .addFilterUseCase()
                //.addCheckoutUseCase()
                .addHomepageUseCase()
                .addLogoutUseCase()
                .addMakeListingUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
