package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entity.CartItemDisplay;
import use_case.checkout.*;

import java.awt.*;

/**
 * Order confirmation window that now also provides a button
 * to open an "Apply Promotion" window.
 */
public class OrderConfirmationWindow extends JFrame {

    private final CheckoutOutputData checkoutData;

    public OrderConfirmationWindow(CheckoutOutputData outputData) {
        this.checkoutData = outputData;

        setTitle("Order Confirmation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createUserInfoPanel(), BorderLayout.NORTH);
        mainPanel.add(createOrderDetailsPanel(), BorderLayout.CENTER);
        mainPanel.add(createTotalPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createUserInfoPanel() {
        JPanel userPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        userPanel.setBorder(new TitledBorder("Customer Information"));

        JLabel nameLabel = new JLabel("Name: " + checkoutData.getUsername());
        JLabel emailLabel = new JLabel("Email: " + checkoutData.getEmail());
        JLabel addressLabel = new JLabel("Billing Address:");

        JTextArea addressText = new JTextArea(checkoutData.getBillingAddress());
        addressText.setEditable(false);
        addressText.setBackground(getBackground());
        addressText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addressText.setLineWrap(true);
        addressText.setWrapStyleWord(true);

        userPanel.add(nameLabel);
        userPanel.add(emailLabel);
        userPanel.add(addressLabel);
        userPanel.add(addressText);

        return userPanel;
    }

    private JComponent createOrderDetailsPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(new TitledBorder("Order Details"));

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
        orderTable.setRowHeight(25);

        orderTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    /**
     * Bottom panel now shows the total AND an "Apply Promotion" button.
     */
    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel totalLabel = new JLabel(
                String.format("Total (%d items): $%.2f",
                        checkoutData.getTotalItems(),
                        checkoutData.getTotalPrice())
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
}