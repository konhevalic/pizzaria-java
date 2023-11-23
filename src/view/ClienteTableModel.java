/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.table.AbstractTableModel;
import model.cliente.Cliente;

/**
 *
 * @author alan
 */
public class ClienteTableModel extends AbstractTableModel {
    
    private List<Cliente> clientes = new ArrayList<>();
    private String[] colunas = {"Nome","Sobrenome", "Telefone"};

    @Override
    public int getRowCount() {
        return clientes.size();
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
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getNome();
            case 1 -> cliente.getSobrenome();
            case 2 -> cliente.getTelefone();
            default -> null;
        };
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        Cliente cliente = clientes.get(row);
        switch (col) {
            case 0 -> cliente.setNome((String) value);
            case 1 -> cliente.setSobrenome((String) value);
            case 2 -> cliente.setTelefone((String) value);
            default -> {
            }
        }
        this.fireTableCellUpdated(row, col);
    }
    
    public void adicionaCliente(Cliente cliente) {
        this.clientes.add(cliente);
        this.fireTableRowsInserted(clientes.size()-1,clientes.size()-1);
    }

    public Cliente getCliente(int linhaSelecionadaEditarCliente) {
        return clientes.get(linhaSelecionadaEditarCliente);
    }
    
    public boolean removeCliente(Cliente c) {
        int linha = this.clientes.indexOf(c);
        boolean result = this.clientes.remove(c);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }
    
    public void removeClientes(List<Cliente> listaParaExcluir) {
        listaParaExcluir.forEach((contato) -> {
            removeCliente(contato);
        });
    }
    
    public Cliente filtraCliente(String telefone, ClienteTableModel clienteTableModel) {
        
        for(int i = 0; i<clientes.size(); i++) {
            String telefoneCadastrado = (String) clienteTableModel.getValueAt(i, 2);
            if(telefoneCadastrado.equals(telefone)) {
                return getCliente(i);
            }
        }
        
        return null;
        
    }
    
    public void limpaTabela() {
        int indice = clientes.size()-1;
        if(indice<0) {
            indice=0;
        }

        this.clientes = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }
    
    public void filtraListaCliente(String telefone, String sobrenome, String nome) {
        
        limpaTabela();
        
        List<Cliente> clientesFiltrados;  
        
        this.fireTableRowsInserted(this.clientes.size()-1, this.clientes.size()-1);//update JTable
        
    }
    
    public void setListaCliente(List<Cliente> cliente) {
        this.clientes = cliente;
        this.fireTableDataChanged();
    }
    
}
