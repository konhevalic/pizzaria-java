/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.pizza.Pizza;
/**
 *
 * @author alan
 */
public class PizzasPedidoTableModel extends AbstractTableModel {
    
    private List<Pizza> pizzas = new ArrayList<>();
    private String[] colunas = {"Sabor 1","Sabor 2", "Formato", "Valor"};

    @Override
    public int getRowCount() {
        return pizzas.size();
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
    public void setValueAt(Object value, int row, int col) {
        Pizza pedido = pizzas.get(row);
        switch (col) {
            case 0 -> pedido.setSabor1((String) value);  
            case 1 -> pedido.setSabor2((String) value); 
            case 2 -> pedido.setForma((String) value); 
            case 3 -> pedido.setPreco((double) value);
            default -> {
            }
        }
        this.fireTableCellUpdated(row, col); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void adicionaPizza(Pizza pizza) {
        
        this.pizzas.add(pizza);
        this.fireTableRowsInserted(this.pizzas.size()-1, this.pizzas.size()-1);//update JTable
    }
    
    public Pizza getPizza(int linhaSelecionada) {
        return pizzas.get(linhaSelecionada);
    }
    
    public boolean removerPizza(Pizza pizza) {
        int linha = this.pizzas.indexOf(pizza);
        boolean result = this.pizzas.remove(pizza);
        
        this.fireTableRowsDeleted(linha, linha);
                
        return result;
    }
    
    public void removerPizzas(List<Pizza> listaParaExcluir) {
        listaParaExcluir.forEach((pizza) -> {
            removerPizza(pizza);
        });
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pizza pizza = pizzas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> pizza.getSabor1();  
            case 1 -> pizza.getSabor2(); 
            case 2 -> pizza.getForma(); 
            case 3 -> pizza.getPreco(); 
            default -> null;
        };
    }
    
    public void limpaTabela() {
        int indice = pizzas.size()-1;
        if(indice<0)
            indice=0;
        this.pizzas = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    
}
