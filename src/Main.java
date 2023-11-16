
import controller.cliente.ClienteController;
import controller.pedido.PedidosClienteController;
import controller.pedido.TodosPedidosController;
import controller.preco.PrecoController;
import controller.sabor.SaborController;
import java.sql.SQLException;
import model.cliente.dao.ClienteDao;
import database.ConnectionFactory;
import model.pedido.dao.PedidosClienteDao;
import model.pedido.dao.TodosPedidosDao;
import model.preco.dao.PrecoDao;
import model.sabor.dao.SaborDao;
import view.Pizzaria;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alan
 */
public class Main {
    
    public static void main(String[] args) throws SQLException{
        Pizzaria view = new Pizzaria();
        
        ClienteDao clienteDao = new ClienteDao(new ConnectionFactory());
        ClienteController clienteController = new ClienteController(view,clienteDao);
        
        SaborDao saborDao = new SaborDao(new ConnectionFactory());
        SaborController saborController = new SaborController(view, saborDao);
        
        PrecoDao precoDao = new PrecoDao(new ConnectionFactory());
        PrecoController precoController = new PrecoController(view, precoDao);
        
         
        
        PedidosClienteDao pedidosClienteDao = new PedidosClienteDao(new ConnectionFactory());
        TodosPedidosDao modelTodosPedidosDao = new TodosPedidosDao(new ConnectionFactory());
        PedidosClienteController pedidosClienteController = new PedidosClienteController(view, pedidosClienteDao, saborDao, precoDao, modelTodosPedidosDao);
        TodosPedidosController todosPedidosController = new TodosPedidosController(view, modelTodosPedidosDao);
            
    }
    
}
