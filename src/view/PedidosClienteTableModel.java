/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.table.AbstractTableModel;
import model.pedido.Pedido;
import model.pedido.StatusPedido;
import model.pizza.Pizza;
import repositorio.RepositorioDados;

/**
 *
 * @author alan
 */
public class PedidosClienteTableModel extends AbstractTableModel {
    
    private List<Pedido> pedidos = new ArrayList<>();
    private List<Pedido> pedidosAbertos = new ArrayList<>();
    private String[] colunas = {"Numero Pedido", "Sabor 1","Sabor 2", "Formato", "Valor Pizza", "Status"};


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
        if(indice<0)
            indice=0;
        this.pedidos = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }
    
    public void adicionaPedido(List<Pedido> pedido) {
        this.pedidos.addAll(pedido);
        System.out.println(pedido + "pedidochegou");
        this.fireTableDataChanged();
        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable 
    }
    
    public void setPedidosAbertos(List<Pedido> pedidosCliente) {
        this.pedidosAbertos = pedidosCliente;
    }
    
    public List<Pedido> getPedidosAbertos() {
        return this.pedidosAbertos;
    }
    
    public void filtraPedidos(String telefone, StatusPedido status) {
        
        limpaTabela();
        
        List<Pedido> pedidosCliente = RepositorioDados.listaPedidos.stream()
                .filter(pedido -> pedido.getCliente().getTelefone().equals(telefone) && pedido.getStatus().equals(status)).toList();

        setPedidosAbertos(pedidosCliente);
        this.pedidos.addAll(pedidosCliente);
        this.fireTableRowsInserted(this.pedidos.size()-1, this.pedidos.size()-1);//update JTable
        
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pedido pedido = pedidos.get(rowIndex);       

        return switch (columnIndex) {
            case 0 -> pedido.getNumeroPedido();
            case 1 -> pedido.getSabor1();
            case 2 -> pedido.getSabor2();
            case 3 -> pedido.getForma();
            case 4 -> pedido.getPreco();
            case 5 -> pedido.getStatus();
            default -> null;
        };
    }

}
