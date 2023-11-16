/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.pedido;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.pedido.Pedido;
import model.pedido.dao.PedidosClienteDao;
import model.pedido.dao.TodosPedidosDao;
import model.preco.dao.PrecoDao;
import model.sabor.dao.SaborDao;
import view.Pizzaria;

/**
 *
 * @author alan
 */
public class TodosPedidosController {
    
    private final Pizzaria view;
//    private final PedidosClienteDao pedidosClienteDao;
//    private final SaborDao modelSaborDao;
//    private final PrecoDao modelPrecoDao;
    private final TodosPedidosDao modelTodosPedidosDao;
    
        public TodosPedidosController(Pizzaria view, TodosPedidosDao modelTodosPedidosDao) {
        this.view = view;
//        this.pedidosClienteDao = pedidosClienteDao;
//        this.modelSaborDao = modelSaborDao;
//        this.modelPrecoDao = modelPrecoDao;
        this.modelTodosPedidosDao = modelTodosPedidosDao;
        initController();
    }
        
    private void initController(){
        this.view.setTodosPedidosController(this);
        this.view.initView();
    }
    
    public void filtraPedidos() {
        
        int numeroPedido = view.filtraPedido();
        List<Pedido> pedidosFiltrados;
        try {
            pedidosFiltrados = this.modelTodosPedidosDao.filtraPorNumeroPedido(numeroPedido);
            view.mostraListaPedidosFiltrado(pedidosFiltrados);
        } catch (SQLException ex) {
            Logger.getLogger(TodosPedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void atualizaPedido() throws SQLException {
        
        Pedido pedido = view.getPedidoParaAtualizar();
        
        modelTodosPedidosDao.atualizarStatusPedido(pedido.getNumeroPedido(), pedido.getStatus().name());
        view.atualizarTodosPedidosTabela(modelTodosPedidosDao.getListaPedidos());
        
        
    }
    
}
