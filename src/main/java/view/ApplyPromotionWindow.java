package view;

import use_case.checkout.CheckoutOutputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A simple window for applying a promotion or gift card.
 * Currently it uses a very simple rule:
 *   - "SAVE10" -> 10% off
 *   - "SAVE20" -> 20% off
 *
 */
public class ApplyPromotionWindow extends JFrame {

    private final CheckoutOutputData checkoutData;

    private final JTextField promoCodeField = new JTextField();
    private final JLabel originalTotalLabel = new JLabel();
    private final JLabel discountedTotalLabel = new JLabel("Discounted Total: -");
    private final JLabel messageLabel = new JLabel(" ");

    public ApplyPromotionWindow(CheckoutOutputData checkoutData) {
        this.checkoutData = checkoutData;

        setTitle("Apply Promotion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createInfoPanel(), BorderLayout.NORTH);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        originalTotalLabel.setText(String.format(
                "Original Total (%d items): $%.2f",
                checkoutData.getTotalItems(),
                checkoutData.getSubtotal()
        ));
        originalTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        discountedTotalLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        discountedTotalLabel.setForeground(new Color(0, 102, 0));

        panel.add(originalTotalLabel);
        panel.add(discountedTotalLabel);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        JLabel codeLabel = new JLabel("Promo code or gift card number:");
        panel.add(codeLabel, BorderLayout.NORTH);

        promoCodeField.setColumns(20);
        panel.add(promoCodeField, BorderLayout.CENTER);

        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton applyButton = new JButton("Apply");
        JButton closeButton = new JButton("Close");

        applyButton.addActionListener(e -> applyPromotion());
        closeButton.addActionListener(e -> dispose());

        panel.add(applyButton);
        panel.add(closeButton);

        return panel;
    }


    private void applyPromotion() {
        String code = promoCodeField.getText().trim();
        if (code.isEmpty()) {
            messageLabel.setText("Please enter a code.");
            discountedTotalLabel.setText("Discounted Total: -");
            return;
        }

        double originalTotal = checkoutData.getSubtotal();
        double discountRate;

        if (code.equalsIgnoreCase("SAVE10")) {
            discountRate = 0.10;
        } else if (code.equalsIgnoreCase("SAVE20")) {
            discountRate = 0.20;
        } else {
            discountRate = 0.0;
        }

        if (discountRate <= 0.0) {
            messageLabel.setText("Invalid or expired code. Totals unchanged.");
            discountedTotalLabel.setText("Discounted Total: -");
        } else {
            double discounted = originalTotal * (1.0 - discountRate);
            discountedTotalLabel.setText(
                    String.format("Discounted Total: $%.2f (%.0f%% off)", discounted, discountRate * 100)
            );
            messageLabel.setForeground(new Color(0, 102, 0));
            messageLabel.setText("Promotion applied successfully.");
        }
    }
}