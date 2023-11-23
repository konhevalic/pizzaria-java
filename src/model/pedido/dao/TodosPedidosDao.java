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
public class TodosPedidosDao {
    
    private ConnectionFactory connectionFactory;
    private final String select = "select * from todos_pedidos";
    private final String insert = "insert into todos_pedidos (id, telefone_cliente, sabor1, sabor2, formato, valor_pizza, valor_pedido, status, numero_pedido) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
    private final String filter = "SELECT * FROM todos_pedidos WHERE numero_pedido = ?";
    private final String updateStatus = "UPDATE todos_pedidos SET status = ? WHERE numero_pedido = ?";
    private final String filtraPedidosCliente = "SELECT * FROM todos_pedidos WHERE status = 'ABERTO' AND telefone_cliente = ?";


    
    public TodosPedidosDao(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }
    
    public void inserir(List<Pedido> pedidos) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtAdiciona = connection.prepareStatement(insert)
        ) {
            for (Pedido pedido : pedidos) {
                stmtAdiciona.setInt(1, pedido.getId());
                stmtAdiciona.setString(2, pedido.getTelefoneCliente());
                stmtAdiciona.setString(3, pedido.getSabor1());
                stmtAdiciona.setString(4, pedido.getSabor2());
                stmtAdiciona.setString(5, pedido.getForma());
                stmtAdiciona.setDouble(6, pedido.getPreco());
                stmtAdiciona.setDouble(7, pedido.getPrecoTotal());
                stmtAdiciona.setString(8, pedido.getStatus().name());
                stmtAdiciona.setInt(9, pedido.getNumeroPedido());

                int affectedRows = stmtAdiciona.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("A inserção falhou, nenhum registro foi criado.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Pedido> filtraPorNumeroPedido(int numeroPedido) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(filter)) {

            stmtLista.setInt(1, numeroPedido);

            ResultSet rs = stmtLista.executeQuery();
            List<Pedido> pedidos = new ArrayList();

            while (rs.next()) {
                int id = rs.getInt("id");
                String sabor1 = rs.getString("sabor1");
                String sabor2 = rs.getString("sabor2");
                String formato = rs.getString("formato");
                double valorPizza = rs.getDouble("valor_pizza");
                double valorPedido = rs.getDouble("valor_pedido");
                String status = rs.getString("status");
                String nomeCliente = rs.getString("telefone_cliente");
                int numeroPedidoFiltrado = rs.getInt("numero_pedido");

                Pizza pizza = new Pizza(sabor1, sabor2, formato, valorPizza);
                Pedido pedido = new Pedido(pizza, nomeCliente, id, StatusPedido.valueOf(status), valorPedido, numeroPedidoFiltrado);
                pedido.setId(id);
                pedidos.add(pedido);
            }

            return pedidos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Pedido> getPedidosCliente(Cliente cliente) throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(filtraPedidosCliente)) {

            stmtLista.setString(1, cliente.getTelefone()); 
            try (ResultSet rs = stmtLista.executeQuery()) {
                List<Pedido> pedidos = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String sabor1 = rs.getString("sabor1");
                    String sabor2 = rs.getString("sabor2");
                    String formato = rs.getString("formato");
                    double preco = rs.getDouble("valor_pizza");
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
    
    public List<Pedido> getListaPedidos() throws SQLException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtLista = connection.prepareStatement(select);
             ResultSet rs = stmtLista.executeQuery();
        ) {
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String sabor1 = rs.getString("sabor1");
                String sabor2 = rs.getString("sabor2");
                String formato = rs.getString("formato");
                double valorPizza = rs.getDouble("valor_pizza");
                double valorPedido = rs.getDouble("valor_pedido");
                String status = rs.getString("status");
                String telefoneCliente = rs.getString("telefone_cliente");
                int numeroPedido = rs.getInt("numero_pedido");

                Pizza pizza = new Pizza(sabor1, sabor2, formato, valorPizza);
                Pedido pedido = new Pedido(pizza, telefoneCliente, id, StatusPedido.valueOf(status), valorPedido, numeroPedido);

                pedidos.add(pedido);
            }

            return pedidos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void atualizarStatusPedido(int numeroPedido, String novoStatus) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement stmtAtualizaStatus = connection.prepareStatement(updateStatus)) {

            stmtAtualizaStatus.setString(1, novoStatus);
            stmtAtualizaStatus.setInt(2, numeroPedido);
            
            System.out.println(novoStatus + "noovoovsisistus");

            int linhasAfetadas = stmtAtualizaStatus.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Status do pedido atualizado com sucesso.");
            } else {
                System.out.println("Nenhum pedido foi atualizado. Verifique o número do pedido.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
