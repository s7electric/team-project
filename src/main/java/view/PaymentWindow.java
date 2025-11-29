package view;

import interface_adapter.checkout.PaymentView;
import interface_adapter.checkout.CheckoutViewModel;
import interface_adapter.checkout.CheckoutPresenter;
import interface_adapter.checkout.CheckoutState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentWindow extends JFrame implements PaymentView {
    private CheckoutPresenter presenter;
    private CheckoutViewModel currentViewModel;

    public PaymentWindow(CheckoutPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setPaymentView(this); // Register with specific interface

        setTitle("Payment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);

        // Auto-initialize with current state if available
        if (presenter.getCurrentState() != null) {
            CheckoutViewModel paymentViewModel = createPaymentViewModel(presenter.getCurrentState());
            showPaymentScreen(paymentViewModel);
        }

        setVisible(true);
    }

    @Override
    public void showPaymentScreen(CheckoutViewModel viewModel) {
        this.currentViewModel = viewModel;
        initializeUI();
    }

    @Override
    public void showPaymentResult(boolean success, String message) {
        if (success) {
            JOptionPane.showMessageDialog(this, message, "Payment Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, message, "Payment Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Add this missing helper method
    private CheckoutViewModel createPaymentViewModel(CheckoutState state) {
        String statusMessage = state.hasSufficientFunds() ?
                "Ready to complete payment" : "Insufficient funds";
        String statusColor = state.hasSufficientFunds() ? "BLUE" : "RED";

        return new CheckoutViewModel(
                state.getUsername(),
                state.getEmail(),
                state.getBillingAddress(),
                state.getCartItems(),
                String.format("$%.2f", state.getSubtotal()),
                String.valueOf(state.getTotalItems()),
                String.format("-$%.2f", state.getPointsDiscount()),
                String.format("$%.2f", state.getTotalAfterDiscount()),
                String.format("$%.2f", state.getUserBalance()),
                String.valueOf(state.getUserPoints()),
                String.format("$%.2f", state.getAmountFromBalance()),
                String.format("$%.2f", state.getBalanceAfterPayment()),
                state.hasSufficientFunds(),
                statusMessage,
                statusColor
        );
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane orderScrollPane = new JScrollPane(createOrderSummaryPanel());
        orderScrollPane.setPreferredSize(new Dimension(0, 250));
        orderScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(orderScrollPane, BorderLayout.NORTH);
        mainPanel.add(createPaymentDetailsPanel(), BorderLayout.CENTER);
        mainPanel.add(createActionButtonsPanel(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();
        repaint();
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

        for (var item : currentViewModel.getCartItems()) {
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
        paymentPanel.setPreferredSize(new Dimension(0, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridy = 0;
        JLabel subtotalLabel = new JLabel("Subtotal: " + currentViewModel.getFormattedSubtotal());
        subtotalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(subtotalLabel, gbc);

        gbc.gridy = 1;
        JLabel pointsLabel = new JLabel("Your Points: " + currentViewModel.getFormattedUserPoints() + " points");
        pointsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(pointsLabel, gbc);

        gbc.gridy = 2;
        JLabel discountLabel = new JLabel("Points Discount: " + currentViewModel.getFormattedPointsDiscount());
        discountLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        discountLabel.setForeground(new Color(13,186,59));
        paymentPanel.add(discountLabel, gbc);

        gbc.gridy = 3;
        paymentPanel.add(createSeparator(), gbc);

        gbc.gridy = 4;
        JLabel totalLabel = new JLabel("Total After Discount: " + currentViewModel.getFormattedTotalAfterDiscount());
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        paymentPanel.add(totalLabel, gbc);

        gbc.gridy = 5;
        paymentPanel.add(createSeparator(), gbc);

        gbc.gridy = 6;
        JLabel balanceLabel = new JLabel("Current Balance: " + currentViewModel.getFormattedUserBalance());
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(balanceLabel, gbc);

        gbc.gridy = 7;
        JLabel paymentLabel = new JLabel("Amount from Balance: " + currentViewModel.getFormattedAmountFromBalance());
        paymentLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        paymentPanel.add(paymentLabel, gbc);

        gbc.gridy = 8;
        JLabel finalBalanceLabel = new JLabel();
        if (currentViewModel.hasSufficientFunds()) {
            finalBalanceLabel.setText("Balance After Payment: " + currentViewModel.getFormattedBalanceAfterPayment());
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
            }
        });

        JButton payButton = new JButton("Complete Payment");
        payButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        payButton.setBackground(new Color(34, 139, 34));
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setPreferredSize(new Dimension(180, 35));

        if (!currentViewModel.hasSufficientFunds()) {
            payButton.setEnabled(false);
            payButton.setToolTipText("Insufficient funds to complete payment");
            payButton.setBackground(Color.GRAY);
        }

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.presentPaymentResult(true, "Payment processed successfully!");
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(payButton);

        return buttonPanel;
    }
}