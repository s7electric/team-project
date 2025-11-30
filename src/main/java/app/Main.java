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
                .addManageAddressWindow()
                // Wire use cases
                .addLoginUseCase()
                .addSignUpUseCase()
                .addManageAddressUseCase()
                //.addCheckoutUseCase()
                .addHomepageUseCase()
                .addLogoutUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
