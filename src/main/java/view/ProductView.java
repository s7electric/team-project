package view;

import interface_adapter.Product.ProductController;
import interface_adapter.Product.ProductState;
import interface_adapter.Product.ProductViewModel;
import interface_adapter.add_to_cart.AddToCartViewModel;
import interface_adapter.add_to_cart.AddToCartController;
import interface_adapter.add_to_cart.AddToCartState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class ProductView extends JPanel implements PropertyChangeListener {

    private final String viewName = "Product";
    private final ProductViewModel productViewModel;
    private ProductController productController;
    private AddToCartController addToCartController;
    private final AddToCartViewModel addToCartViewModel;

    private final JLabel imageLabel = new JLabel();
    private final JLabel seller = new JLabel();
    private final JLabel productName = new JLabel();
    private final JLabel productID = new JLabel();
    private final JLabel productPrice = new JLabel();
    private final JLabel productRating = new JLabel();
    private final JLabel reviewCount = new JLabel();
    private final JLabel category = new JLabel();
    private final JLabel username = new JLabel();
    private final JTextField quantityField = new JTextField(5);
    private final JButton addButton = new JButton("Add");
    private final JButton exitButton = new JButton("Exit");

    public ProductView(ProductViewModel productViewModel,
                       AddToCartViewModel addToCartViewModel) {

        this.productViewModel = productViewModel;
        this.productViewModel.addPropertyChangeListener(this);

        this.addToCartViewModel = addToCartViewModel;
        this.addToCartViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setPreferredSize(new Dimension(400, 400));

        JLabel title = new JLabel("Product");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title);

        add(Box.createVerticalStrut(10));

        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.add(imageLabel);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(imagePanel);

        add(Box.createVerticalStrut(15));

        add(makeLabelPanel("Name:", productName));
        add(makeLabelPanel("ProductID:", productID));
        add(makeLabelPanel("Seller:", seller));
        add(makeLabelPanel("Price:", productPrice));
        add(makeLabelPanel("Rating:", productRating));
        add(makeLabelPanel("Reviews:", reviewCount));
        add(makeLabelPanel("Category:", category));

        add(Box.createVerticalStrut(10));

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        qtyPanel.add(new JLabel("Qty:"));
        qtyPanel.add(quantityField);
        add(qtyPanel);

        add(Box.createVerticalStrut(10));
        JPanel buttonRow = new JPanel(new BorderLayout());
        buttonRow.add(addButton, BorderLayout.EAST);
        buttonRow.add(username, BorderLayout.CENTER);
        buttonRow.add(exitButton, BorderLayout.WEST);
        add(buttonRow);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                productController.switchToHomePageView();
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ProductState currentState = productViewModel.getState();

                String qtyText = quantityField.getText();
                int quantity;
                try {
                    quantity = Integer.parseInt(qtyText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    return;
                }
                addToCartController.execute(
                        currentState.getProductid(),
                        quantity,
                        currentState.getUsername()
                );
            }
        });
    }


    private JPanel makeLabelPanel(String label, JLabel value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel(label));
        p.add(value);
        return p;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newState = evt.getNewValue();
        if (newState instanceof ProductState) {
            ProductState state = (ProductState) newState;
            if (state.getPrice() == null || state.getCategory() == null) {
                productController.execute(state.getProductid(), state.getUsername());
            } else {
                productName.setText(state.getName());
                seller.setText(state.getSellerName());
                productPrice.setText(state.getPrice());
                productRating.setText(state.getRating());
                reviewCount.setText(state.getReviewCount());
                category.setText(state.getCategory());
                username.setText(state.getUsername());
                try {
                    URL url = new URL(state.getImageUrl());
                    ImageIcon icon = new ImageIcon(url);
                    Image scaled = icon.getImage().getScaledInstance(
                            200, 200, Image.SCALE_SMOOTH
                    );
                    imageLabel.setIcon(new ImageIcon(scaled));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (newState instanceof AddToCartState) {
            AddToCartState state = (AddToCartState) newState;

            if (state.getQuantityError() != null) {
                JOptionPane.showMessageDialog(this, state.getQuantityError());
                return;
            }

            if (state.getMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getMessage());
                quantityField.setText("");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setProductController(ProductController controller) {
        this.productController = controller;
    }
    public void setAddToCartController(AddToCartController controller) {this.addToCartController = controller;}
    }
