package view;

import javax.swing.*;

/**
 * Simple helper panel that lays out a label next to a text component.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        this.add(label);
        this.add(textField);
    }
}
