/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Aya
 */
public class InvoiceLineTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceLine> lines;
    private String[] columns = {"Invoice No.", "Item Name", "Item Price", "Item Count", "Item Total"};

    public InvoiceLineTableModel(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    
    @Override
    public int getRowCount() {
        return lines == null ? 0 : lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnNo) {
        return columns[columnNo];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (lines == null) {
            return "";
        } else {
            InvoiceLine line = lines.get(rowIndex);
            switch(columnIndex) {
                case 0: return line.getInvHeader().getInvoiceNum();
                case 1: return line.getItemName();
                case 2: return line.getItemPrice();
                case 3: return line.getItemCount();
                case 4: return line.getLineTotal();
                default : return "";
            }
        }
    }
    
}
