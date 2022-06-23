/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MyListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * @author ASh
 */
public class InvoiceHeaderAddition extends JDialog {
    private JLabel invoiceNumberLabel;
    private JLabel invoiceNumberData;
    private JLabel invoiceDateLabel;
    private JTextField invoiceDateData;
    private JLabel customerNameLabel;
    private JTextField customerNameData;
    private JButton addBtn;
    private JButton cancelBtn;
    String newInvoiceNumberString;
    
    public InvoiceHeaderAddition(MyListener listener,int newInvoiceNumber) {
        this.newInvoiceNumberString=String.valueOf(newInvoiceNumber);
        invoiceNumberLabel= new JLabel("New Invoice No :");
        invoiceNumberData = new JLabel(newInvoiceNumberString);
        invoiceDateLabel = new JLabel("New Invoice Date :");
        invoiceDateData = new JTextField(20);
        customerNameLabel = new JLabel("New Customer Name:");
        customerNameData = new JTextField(20);
        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");
        
        addBtn.setActionCommand("aNSI"); //newInvoiceOK
        addBtn.addActionListener(listener);
        cancelBtn.setActionCommand("cNSI"); //newInvoiceCancel
        cancelBtn.addActionListener(listener);
        
        setLayout(new GridLayout(4, 2));
        add(invoiceNumberLabel);
        add(invoiceNumberData);
        add(invoiceDateLabel);
        add(invoiceDateData);
        add(customerNameLabel);
        add(customerNameData);
        add(addBtn);
        add(cancelBtn);
        pack();
    }

    public int getNewInvoiceNumberData() {
        return Integer.parseInt(newInvoiceNumberString);
    }

    public JTextField getInvoiceDateData() {
        return invoiceDateData;
    }

    public JTextField getCustomerNameData() {
        return customerNameData;
    }
    
    
}
