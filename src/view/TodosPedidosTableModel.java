/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.pedido.Pedido;
import model.pedido.StatusPedido;
import model.pizza.Pizza;

/**
 *
 * @author alan
 */
public class TodosPedidosTableModel extends AbstractTableModel {
    
    private List<Pedido> pedidos = new ArrayList<>();
    private String[] colunas = {"Numero Pedido", "Cliente", "Sabor 1","Sabor 2", "Formato", "Valor Pizza", "Valor Pedido", "Status"};

    @Override
    public int getRowCount() {
        return pedidos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }
    
    public void limpaTabela() {
        int indice = pedidos.size()-1;
        if(indice<0) {
            indice=0;
        }

        this.pedidos = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }
    
    public void excluiPedidos(String telefoneCliente) {
        
        limpaTabela();
        
        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable
    }
    
    
    public void populaTabela() {
        
        limpaTabela();

        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable 
    
    }
    
    public void adicionaPedido(List<Pedido> pedido) {
        this.pedidos.addAll(pedido);
        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable 
    }
    
    public Pedido getPedido(int linhaSelecionadaEditarCliente) {
        return pedidos.get(linhaSelecionadaEditarCliente);
    }
    
    public void filtraPedidos(int numeroPedido) {
        
        limpaTabela();

        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable
        
    }
    
    public void atualizarStatus(int numeroPedido, StatusPedido status) {
        
        this.fireTableDataChanged();
    }
    
    public void setListaTodosPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
        this.fireTableDataChanged();
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        
        Pedido pedido = pedidos.get(rowIndex);       

        return switch (columnIndex) {
            case 0 -> pedido.getNumeroPedido();
            case 1 -> pedido.getTelefoneCliente();
            case 2 -> pedido.getForma();
            case 3 -> pedido.getSabor1();
            case 4 -> pedido.getSabor2();
            case 5 -> pedido.getPreco();
            case 6 -> pedido.getPrecoTotal();
            case 7 -> pedido.getStatus();
            default -> null;
        };
    }
    
}
