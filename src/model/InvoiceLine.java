package model;

public class InvoiceLine {
    //Decleration
    private String itemName;//item
    private double itemPrice;//price
    private int itemCount;//count
    private InvoiceHeader invHeader;//header
    //Constructors
    public InvoiceLine() {
    }
    
    public InvoiceLine( String itemName, double itemPrice, int count) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = count;
    }

    public InvoiceLine(String itemName, double itemPrice, int itemCount, InvoiceHeader invHeader) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.invHeader = invHeader;
    }
    //Set & Get
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
    
    public int getItemCount() {
        return itemCount;
    }

    public void setInvHeader(InvoiceHeader invHeader) {
        this.invHeader = invHeader;
    }
    
    public InvoiceHeader getInvHeader() {
        return invHeader;
    }

    public double getLineTotal() {
        return itemPrice * itemCount;
    }
    
    @Override
    public String toString() {
        return invHeader.getInvoiceNum() + "," + itemName + "," + String.valueOf(itemPrice) + "," + itemCount ;
    }
    
}
