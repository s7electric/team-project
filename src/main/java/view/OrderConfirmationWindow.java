package view;

import use_case.checkout.CheckoutOutputData;
import entity.CartItemDisplay;
import entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderConfirmationWindow extends JFrame {
    private final CheckoutOutputData outputData;
    private final User user;

    public OrderConfirmationWindow(CheckoutOutputData outputData, User user) {
        this.outputData = outputData;
        this.user = user;

        setTitle("Order Confirmation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600); // Slightly reduced height
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createUserInfoPanel(outputData), BorderLayout.NORTH);
        mainPanel.add(createOrderDetailsPanel(outputData), BorderLayout.CENTER);

        // Create a panel for both total and the payment button
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(createTotalPanel(outputData), BorderLayout.NORTH);
        southPanel.add(createPaymentButtonPanel(), BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createUserInfoPanel() {
        JPanel userPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        userPanel.setBorder(new TitledBorder("Customer Information"));
        userPanel.setPreferredSize(new Dimension(0, 240)); // Increased to ensure address visibility

        JLabel nameLabel = new JLabel("Name: " + checkoutData.getUsername());
        JLabel emailLabel = new JLabel("Email: " + checkoutData.getEmail());
        JLabel addressLabel = new JLabel("Billing Address:");

        JTextArea addressText = new JTextArea(checkoutData.getBillingAddress());
        addressText.setEditable(false);
        addressText.setBackground(getBackground());
        addressText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addressText.setLineWrap(true);
        addressText.setWrapStyleWord(true);
        addressText.setRows(3); // Ensure 3 rows for address

        userPanel.add(nameLabel);
        userPanel.add(emailLabel);
        userPanel.add(addressLabel);
        userPanel.add(addressText);

        return userPanel;
    }

    private JComponent createOrderDetailsPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(new TitledBorder("Order Details"));
        orderPanel.setPreferredSize(new Dimension(0, 280)); // Reduced from 350 to 280

        String[] columnNames = {"Product", "Price", "Quantity", "Subtotal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (CartItemDisplay item : checkoutData.getCartItems()) {
            Object[] rowData = {
                    item.getProductName(),
                    String.format("$%.2f", item.getPrice()),
                    item.getQuantity(),
                    String.format("$%.2f", item.getSubtotal())
            };
            tableModel.addRow(rowData);
        }

        JTable orderTable = new JTable(tableModel);
        orderTable.setFillsViewportHeight(true);
        orderTable.setRowHeight(25); // Slightly reduced row height
        orderTable.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Set column widths
        orderTable.getColumnModel().getColumn(0).setPreferredWidth(280); // Slightly reduced
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(110);

        // Make the table header more visible
        orderTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        orderTable.getTableHeader().setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(0, 250)); // Reduced scroll pane height
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    /**
     * Bottom panel now shows the total AND an "Apply Promotion" button.
     */
    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel totalLabel = new JLabel(
                String.format("Total (%d items): $%.2f", outputData.getTotalItems(), outputData.getSubtotal())
        );
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLUE);

        JButton applyPromoButton = new JButton("Apply Promotion...");
        applyPromoButton.addActionListener(e -> {
            ApplyPromotionWindow promoWindow = new ApplyPromotionWindow(checkoutData);
            promoWindow.setVisible(true);
        });

        totalPanel.add(applyPromoButton);
        totalPanel.add(totalLabel);

        return totalPanel;
    }

    private JPanel createPaymentButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Reduced padding

        JButton paymentButton = new JButton("Proceed to Payment");
        paymentButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        paymentButton.setBackground(new Color(70, 130, 180));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setFocusPainted(false);
        paymentButton.setPreferredSize(new Dimension(180, 35));

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close this window and open payment window
                dispose();
                PaymentWindow paymentWindow = new PaymentWindow(outputData, user);
                paymentWindow.setVisible(true);
            }
        });

        buttonPanel.add(paymentButton);
        return buttonPanel;
    }
}