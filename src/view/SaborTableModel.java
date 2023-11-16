/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.sabor.Sabor;
import model.sabor.Sabor;
import model.sabor.Sabor;
import model.sabor.Sabor;

/**
 *
 * @author alan
 */
public class SaborTableModel extends AbstractTableModel {
    
    private List<Sabor> sabores = new ArrayList<>();
    private String[] colunas = {"Sabor","Tipo"};

    @Override
    public int getRowCount() {
        return sabores.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sabor sabor = sabores.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> sabor.getSabor();
                case 1 -> sabor.getTipo();
                default -> null;
            };
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        Sabor sabor = sabores.get(row);
        switch (col) {
            case 0 -> sabor.setSabor((String) value);
            case 1 -> sabor.setTipo((String) value);
            default -> {
            }
        }
        this.fireTableCellUpdated(row, col);
    }
    
    public void adicionaSabor(Sabor sabor) {
        
        this.sabores.add(sabor);
        this.fireTableRowsInserted(sabores.size()-1, sabores.size()-1);//update JTable
    }
    
    public String getTipoBySabor(String sabor) {
        
        for(int i = 0; i<sabores.size(); i++) {
            String saborCadastrado = sabores.get(i).getSabor(); // clienteTableModel.getValueAt(i, 2);
            if(saborCadastrado.equals(sabor)) {
                return sabores.get(i).getTipo();
            }
        }
        
        return "Simples";
    }
    
    
    public void setListaSabor(List<Sabor> sabores) {
        this.sabores = sabores;
        this.fireTableDataChanged();
    }
    
}
