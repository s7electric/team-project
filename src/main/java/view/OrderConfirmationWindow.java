package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;


import entity.CartItemDisplay;
import use_case.checkout.*;

import java.awt.*;


public class OrderConfirmationWindow extends JFrame {
    public OrderConfirmationWindow(CheckoutOutputData outputData) {
        setTitle("Order Confirmation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createUserInfoPanel(outputData), BorderLayout.NORTH);
        mainPanel.add(createOrderDetailsPanel(outputData), BorderLayout.CENTER);
        mainPanel.add(createTotalPanel(outputData), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createUserInfoPanel(CheckoutOutputData outputData) {
        JPanel userPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        userPanel.setBorder(new TitledBorder("Customer Information"));

        JLabel nameLabel = new JLabel("Name: " + outputData.getUsername());
        JLabel emailLabel = new JLabel("Email: " + outputData.getEmail());
        JLabel addressLabel = new JLabel("Billing Address:");

        JTextArea addressText = new JTextArea(outputData.getBillingAddress());
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

    private JComponent createOrderDetailsPanel(CheckoutOutputData outputData) {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(new TitledBorder("Order Details"));

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

        orderTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        orderTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        orderTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        orderTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    private JPanel createTotalPanel(CheckoutOutputData outputData) {
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel totalLabel = new JLabel(
                String.format("Total (%d items): $%.2f", outputData.getTotalItems(), outputData.getTotalPrice())
        );
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLUE);

        totalPanel.add(totalLabel);

        return totalPanel;
    }
}

