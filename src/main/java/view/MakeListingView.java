package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.io.File;

import interface_adapter.make_listing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MakeListingView extends JPanel implements PropertyChangeListener {

    private MakeListingViewModel makeListingViewModel;
    private MakeListingController makeListingController;
    private String selectedFilePath;
    
    public MakeListingView(MakeListingViewModel makeListingViewModel) {
        this.makeListingViewModel = makeListingViewModel;
        this.makeListingViewModel.addPropertyChangeListener(this);

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Product Name:");
        namePanel.add(nameLabel);
        JTextField nameField = new JTextField(25);
        namePanel.add(nameField);

        JPanel pricePanel = new JPanel();
        JLabel priceLabel = new JLabel("Price:");
        pricePanel.add(priceLabel);
        JTextField priceTextField = new JTextField(25);
        pricePanel.add(priceTextField);

        JPanel buttonsPanel = new JPanel();
        JButton cancelButton = new JButton("Cancel & Return");
        JButton uploadImageButton = new JButton("Select Image");
        JButton submitButton = new JButton("Create Listing");
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(uploadImageButton);
        buttonsPanel.add(submitButton);

        JPanel filterSelectionPanel = new JPanel();
        String[] filterOptions = {"Food", "Kitchen", "Outside", "House", "Electric Appliances", "Bedroom", "Bathroom", "Office", "Education", "Sports", "Health"};
        JComboBox<String>  filterComboBox = new JComboBox<>(filterOptions);
        filterSelectionPanel.add(filterComboBox);

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = nameField.getText();
                if (productName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Product name cannot be empty.");
                    return;
                }
                double price;
                try {price = Double.parseDouble(priceTextField.getText());}
                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price. Please enter a valid number.");
                    return;
                }
                String filePath = selectedFilePath;
                if (filePath == null || filePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select an image file.");
                    return;
                }
                String category = filterComboBox.getSelectedItem().toString();
                if (category == null || category.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select a category.");
                    return;
                }
                String sellerName = makeListingViewModel.getState().getUsername();
                makeListingController.execute(productName, price, filePath, category, sellerName);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeListingController.switchToHomePageView();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(namePanel);
        this.add(pricePanel);
        this.add(filterSelectionPanel);
        this.add(buttonsPanel);
    }

    public void setController(MakeListingController makeListingController) {
        this.makeListingController = makeListingController;
    }



    public String getViewName() {
        return makeListingViewModel.getViewName();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        MakeListingState makeListingState = (MakeListingState) evt.getNewValue();
    }

    public static void main(String[] args) {
        // This main method is just for testing the MakeListingView independently.
        MakeListingViewModel viewModel = new MakeListingViewModel();
        MakeListingView view = new MakeListingView(viewModel);
        javax.swing.JFrame frame = new javax.swing.JFrame("Make Listing View Test");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}