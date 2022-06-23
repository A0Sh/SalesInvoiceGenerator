package model;

import java.util.ArrayList;


public class InvoiceHeader {
    //Declearation
    private int invoiceNum; //num
    private String customerName;//customer
    private String invoiceDate; //invDate
    private ArrayList<InvoiceLine> invoiceLines;
    
    public InvoiceHeader() {
    }
    
    public InvoiceHeader(int invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }
    
    
    public int getInvoiceNum() {
        return invoiceNum;
    }
    
    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
    
    public ArrayList<InvoiceLine> getInvoiceLines() {
        if (invoiceLines == null) {
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }
    
    public double getInvoiceTotal() {
        double total = 0.0;
        ArrayList<InvoiceLine> numberOfLines=this.getInvoiceLines();   
        if(numberOfLines != null){
            for (int i = 0; i < invoiceLines.size(); i++) {
                total += invoiceLines.get(i).getLineTotal();
                }
        }
        return total;
    }
    
    public void display(){
        System.out.println(invoiceNum+" "+customerName+" "+invoiceDate);
    }
    
    @Override
    public String toString() {
        return invoiceNum + "," + invoiceDate + "," + customerName;
    }
}