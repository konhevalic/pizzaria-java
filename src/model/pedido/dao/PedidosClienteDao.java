/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.pedido.dao;

import database.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.cliente.Cliente;
import model.pedido.Pedido;
import model.pedido.StatusPedido;
import model.pizza.Pizza;

/**
 *
 * @author alan
 */
public class PedidosClienteDao {
    
    private ConnectionFactory connectionFactory;
    private final String insert = "insert into pedidos_cliente (sabor1, sabor2, formato, valor, status, telefone_cliente, numero_pedido) values (?,?,?,?,?,?,?) returning id";
    private final String select = "SELECT * FROM pedidos_cliente WHERE status = 'ABERTO' AND telefone_cliente = ?";
    
    public PedidosClienteDao(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }
    
    public void inserir(List<Pedido> pedidos, Cliente cliente) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtAdiciona = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)
        ) {
            for (Pedido pedido : pedidos) {
                stmtAdiciona.setString(1, pedido.getSabor1());
                stmtAdiciona.setString(2, pedido.getSabor2());
                stmtAdiciona.setString(3, pedido.getForma());
                stmtAdiciona.setDouble(4, pedido.getPreco());
                stmtAdiciona.setString(5, pedido.getStatus().name());
                stmtAdiciona.setString(6, cliente.getTelefone());
                stmtAdiciona.setInt(7, pedido.getNumeroPedido());

                int affectedRows = stmtAdiciona.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("A inserção falhou, nenhum registro foi criado.");
                }

                try (ResultSet generatedKeys = stmtAdiciona.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1); // Obtenha o valor da coluna 'id' gerado automaticamente
                        pedido.setId(idGerado); // Atribua o ID gerado ao objeto Pedido
                    } else {
                        throw new SQLException("A inserção falhou, nenhum ID foi gerado.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Pedido> getLista(Cliente cliente) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(select)) {

            stmtLista.setString(1, cliente.getTelefone());

            try (ResultSet rs = stmtLista.executeQuery()) {
                List<Pedido> pedidos = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String sabor1 = rs.getString("sabor1");
                    String sabor2 = rs.getString("sabor2");
                    String formato = rs.getString("formato");
                    double preco = rs.getDouble("valor");
                    String status = rs.getString("status");
                    int numeroPedido = rs.getInt("numero_pedido");

                    Pizza pizza = new Pizza(formato, sabor1, sabor2, preco);
                    String nomeCliente = cliente.getTelefone();

                    pedidos.add(new Pedido(pizza, nomeCliente, id, StatusPedido.valueOf(status), preco, numeroPedido));
                }
                

                return pedidos;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
