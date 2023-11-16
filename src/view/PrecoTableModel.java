/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.pizza.Pizza;
import model.preco.Preco;
import model.sabor.Sabor;
import repositorio.RepositorioDados;

/**
 *
 * @author alan
 */
public class PrecoTableModel extends AbstractTableModel {
    
    private List<Preco> precos = new ArrayList<>();
    private String[] colunas = {"Tipo","Preco"};

    @Override
    public int getRowCount() {
        return precos.size();
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
        Preco preco = precos.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> preco.getCategoria();
                case 1 -> preco.getPreco();
                default -> null;
            };
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        Preco preco = precos.get(row);
        switch (col) {
            case 0 -> preco.setCategoria((String) value); 
            case 1 -> preco.setPreco((double) value); 
            default -> {
            }
        }
        this.fireTableCellUpdated(row, col);
    }
    
    public void atualizarPreco() {
        
//        for (Preco preco : RepositorioDados.listaPrecos) {
//            if (preco.getCategoria() == null ? categoria == null : preco.getCategoria().equals(categoria)) {
//                preco.setPreco(novoPreco);
//                
//            }
//        }

        this.fireTableRowsInserted(this.precos.size()-1, this.precos.size()-1);
        this.fireTableDataChanged();
    }
    
    public double getPrecoByTipo(String tipo) {

        for(int i = 0; i<precos.size(); i++) {
            String tipoCadastrado = precos.get(i).getCategoria();
            if(tipoCadastrado.equals(tipo)) {
                return precos.get(i).getPreco();
            }
        }

        return 0.0;
    }
    
//    public void populaTabela() {
//        
//        this.precos.addAll(RepositorioDados.listaPrecos);
//
//        this.fireTableRowsInserted(this.precos.size()-1, this.precos.size()-1);//update JTable 
//    
//    }
    
    public void setListaPreco(List<Preco> precos) {
        this.precos = precos;
        this.fireTableDataChanged();
    }
    
}
