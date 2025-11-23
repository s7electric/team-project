package view;

import entity.Address;
import interface_adapter.manage_address.ManageAddressController;
import interface_adapter.manage_address.ManageAddressState;
import interface_adapter.manage_address.ManageAddressViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Swing JFrame View for the "Manage Addresses" use case.
 */
public class ManageAddressView extends JFrame implements PropertyChangeListener {

    private final ManageAddressController controller;
    private final ManageAddressViewModel viewModel;

    // UI components
    private final DefaultListModel<Address> addressListModel = new DefaultListModel<>();
    private final JList<Address> addressList = new JList<>(addressListModel);

    private final JTextField tfLine1 = new JTextField();
    private final JTextField tfLine2 = new JTextField();
    private final JTextField tfCity = new JTextField();
    private final JTextField tfProvince = new JTextField();
    private final JTextField tfPostal = new JTextField();
    private final JTextField tfCountry = new JTextField();

    private final JCheckBox cbDefaultShipping = new JCheckBox("Default Shipping");
    private final JCheckBox cbDefaultBilling = new JCheckBox("Default Billing");

    private final JButton btnAdd = new JButton("Add");
    private final JButton btnUpdate = new JButton("Update");
    private final JButton btnDelete = new JButton("Delete");
    private final JButton btnClear = new JButton("Clear");

    private final JLabel lblMessage = new JLabel(" ");

    // To restore original borders after we highlight errors
    private final Map<JComponent, Border> originalBorders = new HashMap<>();
    private final Border errorBorder = new LineBorder(Color.RED, 2, true);

    public ManageAddressView(ManageAddressController controller,
                             ManageAddressViewModel viewModel) {
        super("Manage Addresses");
        this.controller = controller;
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        buildUI();
        wireEvents();
        applyState(viewModel.getState());
    }

    private void buildUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Left: address list
        addressList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addressList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel();
            if (value != null) {
                label.setText(value.toString());
            }
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            label.setBackground(isSelected ? new Color(0xCCE5FF) : Color.WHITE);
            return label;
        });
        JScrollPane listScroll = new JScrollPane(addressList);

        //Right: form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        int row = 0;
        addRow(formPanel, gc, row++, "Line 1:", tfLine1);
        addRow(formPanel, gc, row++, "Line 2:", tfLine2);
        addRow(formPanel, gc, row++, "City:", tfCity);
        addRow(formPanel, gc, row++, "Province/State:", tfProvince);
        addRow(formPanel, gc, row++, "Postal Code:", tfPostal);
        addRow(formPanel, gc, row++, "Country:", tfCountry);

        JPanel flagsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        flagsPanel.add(cbDefaultShipping);
        flagsPanel.add(cbDefaultBilling);

        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 2;
        formPanel.add(flagsPanel, gc);
        row++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 2;
        formPanel.add(buttonPanel, gc);
        row++;

        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 2;
        lblMessage.setForeground(Color.DARK_GRAY);
        formPanel.add(lblMessage, gc);

        //Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroll, formPanel);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.4);

        setContentPane(splitPane);

        rememberOriginalBorders(tfLine1, tfLine2, tfCity, tfProvince, tfPostal, tfCountry);
    }

    private void addRow(JPanel panel, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridx = 0;
        gc.gridy = row;
        gc.gridwidth = 1;
        gc.weightx = 0;
        panel.add(new JLabel(label), gc);

        gc.gridx = 1;
        gc.gridy = row;
        gc.gridwidth = 1;
        gc.weightx = 1;
        panel.add(field, gc);
    }

    private void wireEvents() {
        btnAdd.addActionListener(e -> {
            clearFieldErrors();
            ManageAddressState state = viewModel.getState();
            String username = state.getUsername();

            controller.addAddress(
                    username,
                    tfLine1.getText(),
                    tfLine2.getText(),
                    tfCity.getText(),
                    tfProvince.getText(),
                    tfPostal.getText(),
                    tfCountry.getText(),
                    cbDefaultShipping.isSelected(),
                    cbDefaultBilling.isSelected()
            );
        });

        btnUpdate.addActionListener(e -> {
            clearFieldErrors();
            ManageAddressState state = viewModel.getState();
            String username = state.getUsername();

            int selectedIndex = addressList.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(this, "Please select an address to update.",
                        "No selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String addressId = String.valueOf(selectedIndex);

            controller.editAddress(
                    username,
                    addressId,
                    tfLine1.getText(),
                    tfLine2.getText(),
                    tfCity.getText(),
                    tfProvince.getText(),
                    tfPostal.getText(),
                    tfCountry.getText(),
                    cbDefaultShipping.isSelected(),
                    cbDefaultBilling.isSelected()
            );
        });

        btnDelete.addActionListener(e -> {
            clearFieldErrors();
            ManageAddressState state = viewModel.getState();
            String username = state.getUsername();

            int selectedIndex = addressList.getSelectedIndex();
            if (selectedIndex < 0) {
                JOptionPane.showMessageDialog(this, "Please select an address to delete.",
                        "No selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String addressId = String.valueOf(selectedIndex);

            int choice = JOptionPane.showConfirmDialog(this,
                    "Delete the selected address?", "Confirm delete",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                controller.deleteAddress(username, addressId);
            }
        });

        btnClear.addActionListener(e -> clearForm());

        addressList.addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                Address selected = addressList.getSelectedValue();
                if (selected != null) {
                    populateFormFromAddress(selected);
                }
            }
        });
    }

    private void populateFormFromAddress(Address a) {
        tfLine1.setText(a.getLine1());
        tfLine2.setText(a.getLine2());
        tfCity.setText(a.getCity());
        tfProvince.setText(a.getProvinceOrState());
        tfPostal.setText(a.getPostalCode());
        tfCountry.setText(a.getCountry());
        cbDefaultShipping.setSelected(a.isDefaultShipping());
        cbDefaultBilling.setSelected(a.isDefaultBilling());
        clearFieldErrors();
    }

    private void clearForm() {
        addressList.clearSelection();
        tfLine1.setText("");
        tfLine2.setText("");
        tfCity.setText("");
        tfProvince.setText("");
        tfPostal.setText("");
        tfCountry.setText("");
        cbDefaultShipping.setSelected(false);
        cbDefaultBilling.setSelected(false);
        clearFieldErrors();
        lblMessage.setText(" ");
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            ManageAddressState newState = (ManageAddressState) evt.getNewValue();
            applyState(newState);
        }
    }


    private void applyState(ManageAddressState state) {
        List<Address> addresses = state.getAddresses();
        addressListModel.clear();
        if (addresses != null) {
            for (Address a : addresses) {
                addressListModel.addElement(a);
            }
        }

        if (state.getMessage() != null) {
            lblMessage.setText(state.getMessage());
        } else {
            lblMessage.setText(" ");
        }

        clearFieldErrors();
        Map<String, String> errors = state.getFieldErrors();
        if (errors != null && !errors.isEmpty()) {
            applyFieldErrors(errors);
        }
    }

    private void rememberOriginalBorders(JComponent... fields) {
        for (JComponent c : fields) {
            originalBorders.put(c, c.getBorder());
        }
    }

    private void clearFieldErrors() {
        for (Map.Entry<JComponent, Border> entry : originalBorders.entrySet()) {
            JComponent c = entry.getKey();
            c.setBorder(entry.getValue());
            c.setToolTipText(null);
        }
    }

    private void applyFieldErrors(Map<String, String> errors) {
        applyErrorToField(errors, "line1", tfLine1);
        applyErrorToField(errors, "line2", tfLine2);
        applyErrorToField(errors, "city", tfCity);
        applyErrorToField(errors, "provinceOrState", tfProvince);
        applyErrorToField(errors, "postalCode", tfPostal);
        applyErrorToField(errors, "country", tfCountry);
    }

    private void applyErrorToField(Map<String, String> errors, String key, JComponent field) {
        if (errors.containsKey(key)) {
            field.setBorder(errorBorder);
            field.setToolTipText(errors.get(key));
        }
    }
}