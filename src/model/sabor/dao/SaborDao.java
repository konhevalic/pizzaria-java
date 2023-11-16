/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sabor.dao;

import database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.sabor.Sabor;
/**
 *
 * @author alan
 */
public class SaborDao {
    
        private ConnectionFactory connectionFactory;
        
        private final String insert = "insert into sabores (sabor,categoria) values (?,?)";
        private final String select = "select * from sabores";
        private final String filter = "select categoria from sabores where sabor = ?";
        
        public SaborDao(ConnectionFactory conFactory) {
            this.connectionFactory = conFactory;
        }
        
        public void inserir(Sabor sabor) {
            try (Connection connection=connectionFactory.getConnection();
                 PreparedStatement stmtAdiciona = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
                )
            {
                // seta os valores
                stmtAdiciona.setString(1, sabor.getSabor());
                stmtAdiciona.setString(2, sabor.getTipo());
                // executa
                stmtAdiciona.execute();
                //Seta o id do contato
                ResultSet rs = stmtAdiciona.getGeneratedKeys();
                rs.next();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } 
        }
        
        public List<Sabor> getSabores() throws SQLException{
            try (Connection connection=connectionFactory.getConnection();
                 PreparedStatement stmtLista = connection.prepareStatement(select);
                 ResultSet rs = stmtLista.executeQuery();   
                ){
                List<Sabor> sabores = new ArrayList();
                while (rs.next()) {
                    String sabor= rs.getString("sabor");
                    String categoria = rs.getString("categoria");

                    // adicionando o objeto à lista
                    sabores.add(new Sabor(sabor, categoria));
                }

                return sabores;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } 
        }
        
        public String getCategoria(String saborSelecionado) throws SQLException {
            try (Connection connection = connectionFactory.getConnection();
                 PreparedStatement stmtLista = connection.prepareStatement(filter)) {

                stmtLista.setString(1, saborSelecionado);

                ResultSet rs = stmtLista.executeQuery();
                String categoria = "";

                while (rs.next()) {
                    categoria = rs.getString("categoria");
                }

                return categoria;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    
}
