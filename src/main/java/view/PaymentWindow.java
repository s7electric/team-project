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

public class PaymentWindow extends JFrame {
    private final CheckoutOutputData outputData;
    private final User user;

    public PaymentWindow(CheckoutOutputData outputData, User user) {
        this.outputData = outputData;
        this.user = user;

        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700); // Increased window size to make Payment Details always visible.
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Order Summary with scrolling
        JScrollPane orderScrollPane = new JScrollPane(createOrderSummaryPanel());
        orderScrollPane.setPreferredSize(new Dimension(0, 250)); // Fixed height for order summary
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(orderScrollPane, BorderLayout.NORTH);
        mainPanel.add(createPaymentDetailsPanel(), BorderLayout.CENTER); // Payment details get remaining space, because
        mainPanel.add(createActionButtonsPanel(), BorderLayout.SOUTH); // I always want Payment Details to be visible.

        add(mainPanel);
    }

    private JPanel createOrderSummaryPanel() {
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(new TitledBorder("Order Summary"));

        String[] columnNames = {"Product", "Price", "Quantity", "Subtotal"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (CartItemDisplay item : outputData.getCartItems()) {
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
        orderTable.setRowHeight(25);
        orderTable.setFont(new Font("SansSerif", Font.PLAIN, 12));

        orderTable.getColumnModel().getColumn(0).setPreferredWidth(280);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(110);

        orderTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        orderTable.getTableHeader().setBackground(new Color(240, 240, 240));

        JScrollPane tableScrollPane = new JScrollPane(orderTable);
        summaryPanel.add(tableScrollPane, BorderLayout.CENTER);

        return summaryPanel;
    }

    private JPanel createPaymentDetailsPanel() {
        JPanel paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(new TitledBorder("Payment Details"));
        paymentPanel.setPreferredSize(new Dimension(0, 300)); // Larger fixed height

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10); // Padding

        // All  my calculations are already done in the use case,
        // so here I just display the pre-calculated values.

        gbc.gridy = 0;
        JLabel subtotalLabel = new JLabel(String.format("Subtotal: $%.2f", outputData.getSubtotal()));
        subtotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(subtotalLabel, gbc);

        gbc.gridy = 1;
        JLabel pointsLabel = new JLabel(String.format("Your Points: %d points", outputData.getUserPoints()));
        pointsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(pointsLabel, gbc);

        gbc.gridy = 2;
        JLabel discountLabel = new JLabel(String.format("Points Discount: -$%.2f", outputData.getPointsDiscount()));
        discountLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        discountLabel.setForeground(new Color(33, 195, 94));
        paymentPanel.add(discountLabel, gbc);

        gbc.gridy = 3;
        paymentPanel.add(createSeparator(), gbc);

        gbc.gridy = 4;
        JLabel totalLabel = new JLabel(String.format("Total After Discount: $%.2f", outputData.getTotalAfterDiscount()));
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        paymentPanel.add(totalLabel, gbc);

        gbc.gridy = 5;
        paymentPanel.add(createSeparator(), gbc);

        gbc.gridy = 6;
        JLabel balanceLabel = new JLabel(String.format("Current Balance: $%.2f", outputData.getUserBalance()));
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(balanceLabel, gbc);

        gbc.gridy = 7;
        JLabel paymentLabel = new JLabel(String.format("Amount to Pay From Balance: $%.2f", outputData.getAmountFromBalance()));
        paymentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(paymentLabel, gbc);

        gbc.gridy = 8;
        JLabel finalBalanceLabel = new JLabel();
        if (outputData.hasSufficientFunds()) {
            finalBalanceLabel.setText(String.format("Balance After Payment: $%.2f", outputData.getBalanceAfterPayment()));
            finalBalanceLabel.setForeground(Color.BLUE);
        } else {
            finalBalanceLabel.setText("Insufficient Funds - Balance will be negative!");
            finalBalanceLabel.setForeground(Color.RED);
        }
        finalBalanceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        paymentPanel.add(finalBalanceLabel, gbc);

        return paymentPanel;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.GRAY);
        return separator;
    }

    private JPanel createActionButtonsPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Go back to order confirmation
                OrderConfirmationWindow orderWindow = new OrderConfirmationWindow(outputData, user);
                orderWindow.setVisible(true);
            }
        });

        JButton payButton = new JButton("Complete Payment");
        payButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        payButton.setBackground(new Color(34, 139, 34)); // Forest green
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setPreferredSize(new Dimension(180, 35));

        // Use pre-calculated value from outputData
        if (!outputData.hasSufficientFunds()) {
            payButton.setEnabled(false);
            payButton.setToolTipText("Insufficient funds to complete payment");
            payButton.setBackground(Color.GRAY);
        }

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(payButton);

        return buttonPanel;
    }

    // NEXT PR I WILL MOVE THIS
    // I just haven't decided which file I want to move this to. (User? Checkout use case? etc)
    private void processPayment() {
        if (outputData.hasSufficientFunds()) {
            // Process the payment using the pre-calculated values
            double amountPaid = outputData.getTotalAfterDiscount();
            int pointsUsed = (int) (outputData.getPointsDiscount() * 10);

            // Update user balance and points
            user.removeBalance(amountPaid);
            user.removePointsBalance(pointsUsed);

            JOptionPane.showMessageDialog(this,
                    String.format("Payment successful!\n\n" +
                                    "Amount paid: $%.2f\n" +
                                    "Points used: %d\n" +
                                    "New balance: $%.2f\n" +
                                    "Remaining points: %d",
                            amountPaid, pointsUsed, user.getBalance(), user.getPointsBalance()),
                    "Payment Complete",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Insufficient funds to complete payment!",
                    "Payment Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}