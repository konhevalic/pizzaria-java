/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.cliente.dao;

import database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.cliente.Cliente;

/**
 *
 * @author alan
 */
public class ClienteDao {
    
    private ConnectionFactory connectionFactory;
    private final String insert = "insert into clientes (nome,sobrenome,telefone) values (?,?,?)";
    private final String select = "select * from clientes";
    private final String update = "update clientes set nome=?, sobrenome=?, telefone=? WHERE nome=?";
    private final String delete = "delete from clientes WHERE nome=?";
    private final String filter = "SELECT * FROM clientes WHERE nome LIKE ? AND sobrenome LIKE ? AND telefone LIKE ?";
    
     public ClienteDao(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }
     
    public void inserir(Cliente cliente) {
        //https://pt.stackoverflow.com/questions/172909/como-funciona-o-try-with-resources
        try (Connection connection=connectionFactory.getConnection();
             PreparedStatement stmtAdiciona = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            )
        {
            // seta os valores
            stmtAdiciona.setString(1, cliente.getNome());
            stmtAdiciona.setString(2, cliente.getSobrenome());
            stmtAdiciona.setString(3, cliente.getTelefone());
            // executa
            stmtAdiciona.execute();
            //Seta o id do contato
            ResultSet rs = stmtAdiciona.getGeneratedKeys();
            rs.next();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
    
    public void atualizar(Cliente cliente) throws SQLException{
        try(Connection connection=connectionFactory.getConnection();
            PreparedStatement stmtAtualiza = connection.prepareStatement(update);
            ){

            stmtAtualiza.setString(1, cliente.getNome());
            stmtAtualiza.setString(2, cliente.getSobrenome());
            stmtAtualiza.setString(3, cliente.getTelefone());
            stmtAtualiza.setString(4, cliente.getNome());
            
            int linhasAfetadas = stmtAtualiza.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cliente atualizado com sucesso.");
            } else {
                System.out.println("Nenhum cliente foi atualizado.");
            }
        } 
    }
        
    public List<Cliente> getLista() throws SQLException{
        try (Connection connection=connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(select);
             ResultSet rs = stmtLista.executeQuery();   
            ){
            List<Cliente> clientes = new ArrayList();
            while (rs.next()) {
                String nome= rs.getString("nome");
                String sobrenome = rs.getString("sobrenome");
                String telefone = rs.getString("telefone");
                
                // adicionando o objeto à lista
                clientes.add(new Cliente(nome,sobrenome,telefone));
            }
            
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
    
    public List<Cliente> filtra(Cliente cliente) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement stmtLista = connection.prepareStatement(filter)) {
            
            String nome = "%" + cliente.getNome() + "%";
            String sobrenome = "%" + cliente.getSobrenome() + "%";
            String telefone = "%" + cliente.getTelefone() + "%";
            
            stmtLista.setString(1, nome);
            stmtLista.setString(2, sobrenome);
            stmtLista.setString(3, telefone);
            
            ResultSet rs = stmtLista.executeQuery();  
            List<Cliente> clientes = new ArrayList();
            
            while (rs.next()) {
                String nomeFiltrado= rs.getString("nome");
                String sobrenomeFiltrado = rs.getString("sobrenome");
                String telefoneFiltrado = rs.getString("telefone");
                // adicionando o objeto à lista
                clientes.add(new Cliente(nomeFiltrado,sobrenomeFiltrado,telefoneFiltrado));
            }
            
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void excluir(Cliente cliente) throws SQLException {
        
        try (Connection connection=connectionFactory.getConnection();
             PreparedStatement stmtExcluir = connection.prepareStatement(delete);
            ){
            stmtExcluir.setString(1, cliente.getNome());
            stmtExcluir.executeUpdate();
        }
    }
    
    public void exluirLista(List<Cliente> clientes) throws SQLException {
        for(Cliente contato:clientes){
            excluir(contato);
        }
    }
    
}
