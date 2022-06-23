package view;

import controller.MyListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InvoiceLineAddition extends JDialog{
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JTextField itemNameData;
    private JTextField itemCountData;
    private JTextField itemPriceData;
    private JButton addBtn;
    private JButton cancelBtn;
    
    public InvoiceLineAddition (MyListener listener) {
        itemNameLabel = new JLabel("New Item Name : ");
        itemNameData = new JTextField(20);
        itemCountLabel = new JLabel("Count of Item : ");
        itemCountData = new JTextField(20);
        itemPriceLabel = new JLabel("Item Price : ");
        itemPriceData = new JTextField(20);
        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");
        
        addBtn.setActionCommand("aNL");
        addBtn.addActionListener(listener);
        cancelBtn.setActionCommand("cancelNL");
        cancelBtn.addActionListener(listener);
        
        setLayout(new GridLayout(4, 2));
        add(itemNameLabel);
        add(itemNameData);
        add(itemCountLabel);
        add(itemCountData);
        add(itemPriceLabel);
        add(itemPriceData);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public JTextField getItemNameData() {
        return itemNameData;
    }

    public int getItemCountData() {
        int itemCount = 1;
        try {
            itemCount = Integer.parseInt(this.itemCountData.getText());
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "You Entered Wrong Item Number Formate", "Invalid Item number format", JOptionPane.ERROR_MESSAGE);
        }
        return itemCount;
    }

    public double getItemPriceData() {
        double itemPrice = 1;
        try {
            itemPrice = Double.parseDouble(this.itemPriceData.getText());
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "You Entered Wrong Item Price Formate", "Invalid Item Price format", JOptionPane.ERROR_MESSAGE);
        }
        return itemPrice;
    }

}
