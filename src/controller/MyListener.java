package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceHeader;
import model.InvoiceHeaderTableModel;
import model.InvoiceLine;
import model.InvoiceLineTableModel;
import view.InvoiceHeaderAddition;
import view.InvoiceLineAddition;
import view.SIGFrame;


public class MyListener implements ActionListener, ListSelectionListener {
    private SIGFrame frame;
    private InvoiceHeader newInvoiceRow;
    private InvoiceLine line;
    private InvoiceHeaderAddition headerAdditionDialog;
    private InvoiceLineAddition lineAdditionDialog;
    
    
    public MyListener(SIGFrame frame) {
        this.frame = frame;
    }
    
    public MyListener(SIGFrame frame,InvoiceHeader newInvoiceRow,InvoiceLine line) {
        this.frame = frame;
        ArrayList<InvoiceHeader> invoiceHeaders;
        invoiceHeaders = new ArrayList<>();
        invoiceHeaders = frame.getInvoicesArray();
        this.newInvoiceRow = newInvoiceRow;
        
    }

@Override
public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()){
        case "l" : //Load Menu Item
            loadFiles();       
            break;
            
        case "sF" : //Save Files Menu Item
            saveFiles();     
            break;
            
        case "c" : //Create new Invoice Btn
            //Get the New Invoice No
            int newInvoiceNumber = 0;
            for (InvoiceHeader invoice : frame.getInvoicesArray()) {
                if (invoice.getInvoiceNum() > newInvoiceNumber) {
                    newInvoiceNumber = invoice.getInvoiceNum();
                }
            }
            newInvoiceNumber++;
            //Show the New Invoice Entry Dialog
            createNewInvoiceDialog(newInvoiceNumber); 
            break;
            
        case "aNSI" : //Add New Sales Invoice Dialog Btn
            newInvoiceHeaderAddition();
            break;
            
        case "cNSI" : //Cancel New Sales Invoice Dialog Btn
            newInvoiceHeaderCancel();
            break;
            
        case "d" : //Delete Invoice Btn
            deleteInvoice();      
            break;
            
        case "cNL" : //create New Line Btn
            createNewLine();
            break;
            
        case "aNL" : //Add New Line Dialog Btn
            addNewLine();
            break;
            
        case "cancelNL" : //Cancel New Line Dialog Btn
            cancelNewLine();
            break;  
            
        case "dL" : //Delete Line btn
            deleteLine();
            break;
            
        case "ex" : //Exit btn
            System.exit(0);   
            break;
        
    }
}
    
private void loadFiles() {
JFileChooser fc = new JFileChooser();
try {
    JOptionPane.showMessageDialog(null,
            "Please Choose Invoices Header File",
            "Loading",
            JOptionPane.INFORMATION_MESSAGE); 
    int result = fc.showOpenDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
        File fileName = fc.getSelectedFile();
        Path filePath = Paths.get(fileName.getAbsolutePath());
        System.out.println("The Path1 : " + filePath);
        List<String> headerLines = Files.readAllLines(filePath);
        System.out.println("Lines1 : " + headerLines);
        ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();
        for (String headerLine : headerLines) {
            String[] arr = headerLine.split(",");
            int invoiceNum = Integer.parseInt(arr[0]);
            String invoiceDate = arr[1];
            String customerName = arr[2];
            InvoiceHeader newInvoicesRow = new InvoiceHeader(invoiceNum,invoiceDate,customerName);
            invoiceHeaders.add(newInvoicesRow);
        }
        frame.setInvoicesArray(invoiceHeaders);
        
        JOptionPane.showMessageDialog(null,
            "Now,Please Choose Invoices Lines File",
            "Loading",
            JOptionPane.INFORMATION_MESSAGE);
        result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File lineFile = fc.getSelectedFile();
            Path linePath = Paths.get(lineFile.getAbsolutePath());
            List<String> allLines = Files.readAllLines(linePath);
//            ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
                for (String invoiceLine : allLines) {
                    String invoiceItemsParts[] = invoiceLine.split(",");
                    int invoiceNum = Integer.parseInt(invoiceItemsParts[0]);
                    String itemName = invoiceItemsParts[1];
                    double itemPrice = Double.parseDouble(invoiceItemsParts[2]);
                    int count = Integer.parseInt(invoiceItemsParts[3]);
                    InvoiceHeader invoiceHeader = frame.getInvObject(invoiceNum);
                InvoiceLine newLine = new InvoiceLine(itemName, itemPrice, count, invoiceHeader);
                invoiceHeader.getInvoiceLines().add(newLine);
                InvoiceHeaderTableModel invoicesTableModel = new InvoiceHeaderTableModel(invoiceHeaders);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoicesHeaderTable().setModel(invoicesTableModel);
                frame.getInvoicesTableModel().fireTableDataChanged();
                }
            }
            System.out.println("Files has been readed");
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    //Moving between different Invoice Headers
    @Override
    public void valueChanged(ListSelectionEvent e) {
            int selectedInvoiceHeader = frame.getInvoicesHeaderTable().getSelectedRow();
        System.out.println("You have selected row: " + selectedInvoiceHeader);
        if (selectedInvoiceHeader != -1) {
            InvoiceHeader currentInvoice = frame.getInvoicesArray().get(selectedInvoiceHeader);
            ArrayList<InvoiceLine> lines = currentInvoice.getInvoiceLines();
            InvoiceLineTableModel invoiceLineTableModel = new InvoiceLineTableModel(lines);
            frame.setLinesArray(lines);
//            InvoiceLineTableModel linesTableModel = new InvoiceLineTableModel(currentInvoice.getInvoiceLines());
            frame.getInvoiceItemsTable().setModel(invoiceLineTableModel);
            frame.getInvoiceNumberLabelData().setText(""+currentInvoice.getInvoiceNum());
            frame.getInvoiceDateLabelData().setText(currentInvoice.getInvoiceDate());
            frame.getCustomerNameLabelData().setText(currentInvoice.getCustomerName());
            frame.getInvoiceTotalLabelData().setText(""+currentInvoice.getInvoiceTotal());
//            invoiceLineTableModel.fireTableDataChanged(); 
            }
    }
    
    //Showing createNewInvoiceHeader Dialoge
    private void createNewInvoiceDialog(int newInvoiceNumber) {
        headerAdditionDialog = new InvoiceHeaderAddition(this,newInvoiceNumber);
        headerAdditionDialog.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        headerAdditionDialog.setLocation(dim.width/2-headerAdditionDialog.getSize().width/2,
                dim.height/2-headerAdditionDialog.getSize().height/2);
    }

    private void newInvoiceHeaderAddition() {
        headerAdditionDialog.setVisible(false);
        int invoiceNumber = headerAdditionDialog.getNewInvoiceNumberData();
        String invoiceDate = headerAdditionDialog.getInvoiceDateData().getText();
        String customerName = headerAdditionDialog.getCustomerNameData().getText();
        InvoiceHeader newInvoiceHeader = new InvoiceHeader(invoiceNumber, invoiceDate, customerName);
        frame.getInvoicesArray().add(newInvoiceHeader);
        frame.getInvoicesTableModel().fireTableDataChanged();
        headerAdditionDialog.setVisible(false);
        headerAdditionDialog.dispose();
        headerAdditionDialog = null;
    }

    private void newInvoiceHeaderCancel() {
        headerAdditionDialog.setVisible(false);
        headerAdditionDialog.dispose();
        headerAdditionDialog = null;
    }    
    
    private void deleteInvoice() {
        int selectedInvoiceHeader = frame.getInvoicesHeaderTable().getSelectedRow();
        if (selectedInvoiceHeader != -1) {
            frame.getInvoicesArray().remove(selectedInvoiceHeader);
            frame.getInvoicesTableModel().fireTableDataChanged();
            frame.getCustomerNameLabelData().setText("");
            frame.getInvoiceNumberLabelData().setText("");
            frame.getInvoiceTotalLabelData().setText("");
            frame.getInvoiceDateLabelData().setText("");
        }
    }
    
    private void createNewLine() {
        lineAdditionDialog = new InvoiceLineAddition(this);
        lineAdditionDialog.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        lineAdditionDialog.setLocation(dim.width/2-lineAdditionDialog.getSize().width/2,
                dim.height/2-lineAdditionDialog.getSize().height/2);
    }
    
    private void addNewLine() {
        lineAdditionDialog.setVisible(false);
        
        String itemName = lineAdditionDialog.getItemNameData().getText();
        int itemCount = lineAdditionDialog.getItemCountData();
        double itemPrice = lineAdditionDialog.getItemPriceData();
        
        int selectedInvoiceHeader = frame.getInvoicesHeaderTable().getSelectedRow();
        if (selectedInvoiceHeader != -1) {
            InvoiceHeader invoiceHeader = frame.getInvoicesArray().get(selectedInvoiceHeader);
            InvoiceLine newLine = new InvoiceLine(itemName, itemPrice, itemCount, invoiceHeader);
            frame.getLinesArray().add(newLine);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) frame.getInvoiceItemsTable().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        frame.getInvoicesHeaderTable().setRowSelectionInterval(selectedInvoiceHeader, selectedInvoiceHeader);
        
        lineAdditionDialog.dispose();
        lineAdditionDialog = null;
    }
    
    private void cancelNewLine() {
        lineAdditionDialog.setVisible(false);
        lineAdditionDialog.dispose();
        lineAdditionDialog = null;
    }

    private void deleteLine() {
        int selectedLineIndex = frame.getInvoiceItemsTable().getSelectedRow();
        int selectedInvoiceIndex = frame.getInvoicesHeaderTable().getSelectedRow();
        if (selectedLineIndex != -1) {
            frame.getLinesArray().remove(selectedLineIndex);
            InvoiceLineTableModel lineTableModel = (InvoiceLineTableModel) frame.getInvoiceItemsTable().getModel();
            lineTableModel.fireTableDataChanged();
            frame.getInvoiceTotalLabelData().setText(""+frame.getInvoicesArray().get(selectedInvoiceIndex).getInvoiceTotal());
            frame.getInvoicesTableModel().fireTableDataChanged();
            frame.getInvoicesHeaderTable().setRowSelectionInterval(selectedInvoiceIndex, selectedInvoiceIndex);
        }
    }

    private void saveFiles() {
        ArrayList<InvoiceHeader> invoicesArray = frame.getInvoicesArray();
        JFileChooser fc = new JFileChooser();
        try{
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFileName = fc.getSelectedFile();
                FileWriter headerFileWriter = new FileWriter(headerFileName);
                String headers ="";
                String lines="";
                for (InvoiceHeader invoice : invoicesArray){
                    headers = headers + invoice.toString();
                    headers += "\n";
                    for (InvoiceLine eachLine : invoice.getInvoiceLines()){
                        lines = lines + eachLine.toString();
                        lines += "\n";
                    }
                }
                result = fc.showSaveDialog(frame);
                File lineFile = fc.getSelectedFile();
                FileWriter lineFileWriter = new FileWriter(lineFile);
                headerFileWriter.write(headers);
                lineFileWriter.write(lines);
                headerFileWriter.close();
                lineFileWriter.close();
              }  
            } catch (IOException ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
       
}
