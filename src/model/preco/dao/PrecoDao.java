/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.preco.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import database.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import model.preco.Preco;

/**
 *
 * @author alan
 */
public class PrecoDao {
    
    private ConnectionFactory connectionFactory;
    private final String select = "select * from valores";
    private final String update = "update valores set preco=? WHERE categoria=?";
    private final String filter = "select preco from valores where categoria = ?";
    
    public PrecoDao(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }

    public List<Preco> getPrecos() throws SQLException{
        try (Connection connection=connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(select);
             ResultSet rs = stmtLista.executeQuery();   
            ){
            List<Preco> precos = new ArrayList();
            while (rs.next()) {
                double preco= rs.getDouble("preco");
                String categoria = rs.getString("categoria");

                // adicionando o objeto à lista
                precos.add(new Preco(preco, categoria));
            }

            return precos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
    
    public void atualizar(Preco preco) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtAtualiza = connection.prepareStatement(update)) {
            stmtAtualiza.setDouble(1, preco.getPreco());
            stmtAtualiza.setString(2, preco.getCategoria()); // Define o valor do parâmetro 'tipo'.

            int linhasAfetadas = stmtAtualiza.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Preço atualizado com sucesso.");
            } else {
                System.out.println("Nenhum preço foi atualizado.");
            }
        }
    }
    
    public double getPreco(String categoriaSelecionada) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(filter)) {

            stmtLista.setString(1, categoriaSelecionada);

            ResultSet rs = stmtLista.executeQuery();
            double preco = 0.0;

            while (rs.next()) {
                preco = rs.getDouble("preco");
            }

            return preco;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
