package view;

import interface_adapter.apply_promotion.ApplyPromotionController;
import interface_adapter.checkout.CheckoutViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ApplyPromotionWindow extends JFrame {

    private final JFrame parent;
    private final ApplyPromotionController controller;
    private final CheckoutViewModel checkoutViewModel;

    private final JTextField promoField = new JTextField(15);
    private final JLabel infoLabel  = new JLabel();
    private final JLabel errorLabel = new JLabel();

    public ApplyPromotionWindow(JFrame parent,
                                ApplyPromotionController controller,
                                CheckoutViewModel checkoutViewModel) {
        this.parent = parent;
        this.controller = controller;
        this.checkoutViewModel = checkoutViewModel;

        setTitle("Apply Promotion");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 220);
        setLocationRelativeTo(parent);

        buildUI();
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        infoLabel.setText(String.format(
                "<html>Current total: <b>%s</b> (%s items)</html>",
                checkoutViewModel.getFormattedSubtotal(),
                checkoutViewModel.getFormattedTotalItems()
        ));

        mainPanel.add(infoLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0;
        center.add(new JLabel("Promo code:"), gc);

        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1.0;
        center.add(promoField, gc);

        errorLabel.setForeground(Color.RED);
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 2;
        center.add(errorLabel, gc);

        mainPanel.add(center, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton applyButton = new JButton("Apply");
        JButton closeButton = new JButton("Close");

        applyButton.addActionListener(this::onApplyPromotion);
        closeButton.addActionListener(e -> dispose());

        buttons.add(closeButton);
        buttons.add(applyButton);

        mainPanel.add(buttons, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void onApplyPromotion(ActionEvent e) {
        String code = promoField.getText().trim();
        if (code.isEmpty()) {
            errorLabel.setText("Please enter a promotion code.");
            return;
        }

        try {
            controller.applyPromotion(checkoutViewModel.getUsername(), code);

            JOptionPane.showMessageDialog(this,
                    "Promotion applied.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } catch (IllegalArgumentException ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            errorLabel.setText("Failed to apply promotion.");
        }
    }
}