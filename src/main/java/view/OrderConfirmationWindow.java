package view;

import interface_adapter.checkout.CheckoutPresenter;
import interface_adapter.checkout.CheckoutViewModel;
import interface_adapter.checkout.OrderConfirmationView;
import interface_adapter.apply_promotion.ApplyPromotionController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OrderConfirmationWindow extends JFrame implements OrderConfirmationView {

    private final CheckoutPresenter presenter;
    private final ApplyPromotionController applyPromotionController;

    private CheckoutViewModel currentViewModel;
    private CheckoutOutputData checkoutData;

    public OrderConfirmationWindow(CheckoutPresenter presenter,
                                   ApplyPromotionController applyPromotionController) {
        this.presenter = presenter;
        this.applyPromotionController = applyPromotionController;

        this.presenter.setOrderConfirmationView(this);

        setTitle("Order Confirmation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
    }

    @Override
    public void showOrderConfirmation(CheckoutViewModel viewModel) {
        this.currentViewModel = viewModel;
        initializeUI();
        setVisible(true);
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        mainPanel.add(createUserInfoPanel(), BorderLayout.NORTH);
        mainPanel.add(createOrderDetailsPanel(), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(createTotalPanel(checkoutData), BorderLayout.NORTH);
        southPanel.add(createPaymentButtonPanel(), BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    private JPanel createUserInfoPanel() {
        JPanel userPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        userPanel.setBorder(new TitledBorder("Customer Information"));
        userPanel.setPreferredSize(new Dimension(0, 150));

        JLabel nameLabel = new JLabel("Name: " + currentViewModel.getUsername());
        JLabel emailLabel = new JLabel("Email: " + currentViewModel.getEmail());
        JLabel addressLabel = new JLabel("Billing Address:");

        JTextArea addressText = new JTextArea(currentViewModel.getBillingAddress());
        addressText.setEditable(false);
        addressText.setBackground(getBackground());
        addressText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        addressText.setLineWrap(true);
        addressText.setWrapStyleWord(true);
        addressText.setRows(3);

        userPanel.add(nameLabel);
        userPanel.add(emailLabel);
        userPanel.add(addressLabel);
        userPanel.add(addressText);

        return userPanel;
    }

    private JComponent createOrderDetailsPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(new TitledBorder("Order Details"));
        orderPanel.setPreferredSize(new Dimension(0, 280));

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

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setPreferredSize(new Dimension(0, 250));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    private JPanel createTotalPanel() {
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel totalLabel = new JLabel(
                String.format("Total (%s items): %s",
                        currentViewModel.getFormattedTotalItems(),
                        currentViewModel.getFormattedSubtotal())
        );
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLUE);

        JButton applyPromoButton = new JButton("Apply Promotion...");
        applyPromoButton.addActionListener(e -> {
            // Open the ApplyPromotionWindow with the current ViewModel
            ApplyPromotionWindow promoWindow =
                    new ApplyPromotionWindow(this, applyPromotionController, currentViewModel);
            promoWindow.setVisible(true);
        });

        totalPanel.add(applyPromoButton);
        totalPanel.add(totalLabel);

        return totalPanel;
    }

    private JPanel createPaymentButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JButton paymentButton = new JButton("Proceed to Payment");
        paymentButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        paymentButton.setBackground(new Color(70, 130, 180));
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setFocusPainted(false);
        paymentButton.setPreferredSize(new Dimension(180, 35));

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PaymentWindow paymentWindow = new PaymentWindow(presenter);
                paymentWindow.setVisible(true);

                dispose();
            }
        });

        buttonPanel.add(paymentButton);
        return buttonPanel;
    }
}
