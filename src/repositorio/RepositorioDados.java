/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;

import java.util.ArrayList;
import java.util.List;
import model.cliente.Cliente;
import view.ClienteTableModel;
import model.pedido.Pedido;
import model.pizza.Pizza;
import model.preco.Preco;
import model.sabor.Sabor;

/**
 *
 * @author alan
 */
public class RepositorioDados {
    
    public static List<Cliente> listaClientes = new ArrayList<>();
    
    public static List<Pizza> listaPrecos = new ArrayList<>();
    
    public static List<Pizza> pizzasPedido = new ArrayList<>();
    
    public static List<Sabor> listaSabores = new ArrayList<>();
    
    public static List<Pedido> listaPedidos = new ArrayList<>();
    
}
