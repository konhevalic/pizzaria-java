/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.pedido;

import controller.sabor.SaborController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.FormularioPizza.FormularioPizza;
import model.cliente.Cliente;
import model.pedido.Pedido;
import model.pedido.dao.PedidosClienteDao;
import model.pedido.dao.TodosPedidosDao;
import model.pizza.Pizza;
import model.preco.dao.PrecoDao;
import model.sabor.dao.SaborDao;
import view.Pizzaria;

/**
 *
 * @author alan
 */
public class PedidosClienteController {
    
        
    private final Pizzaria view;
    private final PedidosClienteDao pedidosClienteDao;
    private final SaborDao modelSaborDao;
    private final PrecoDao modelPrecoDao;
    private final TodosPedidosDao modelTodosPedidosDao;
    
    public PedidosClienteController(Pizzaria view, PedidosClienteDao pedidosClienteDao, SaborDao modelSaborDao, PrecoDao modelPrecoDao, TodosPedidosDao modelTodosPedidosDao) {
        this.view = view;
        this.pedidosClienteDao = pedidosClienteDao;
        this.modelSaborDao = modelSaborDao;
        this.modelPrecoDao = modelPrecoDao;
        this.modelTodosPedidosDao = modelTodosPedidosDao;
        initController();
    }
    
    private void initController(){
        this.view.setPedidosClienteController(this);
        this.view.initView();
    }
    
    public void selecionaCliente() throws SQLException {
        
        Cliente cliente = view.getClientePedido();
        
        try {
            List<Pedido> pedidosAbertos = modelTodosPedidosDao.getPedidosCliente(cliente);
            
            if(pedidosAbertos.isEmpty()) {
                view.limpaTabelaPedidoCliente();
            } else {
                view.inserirPedidoTabela(pedidosAbertos);
            }

            view.setClientePedido();
        } catch (Exception ex) {
            view.mostraMensagemClienteNaoEncontrado();
        }
        
    }
    
    public void adicionaPizzaPedido() {
        
        try{

            FormularioPizza formularioPizza = view.getFormularioPizza();
            List<Pizza> pizzas = new ArrayList();
            
            Pizza pizzaSelecionada = formularioPizza.getPizza();
            
            String categoria1 = modelSaborDao.getCategoria(pizzaSelecionada.getSabor1());
            String categoria2 = "";
            
            String sabor1 = pizzaSelecionada.getSabor1();
            String sabor2 = pizzaSelecionada.getSabor2();
            double areaInformada = formularioPizza.getAreaPizzaInformada();
            String formato = pizzaSelecionada.getForma();
            
            double valorUmSabor = modelPrecoDao.getPreco(categoria1) * areaInformada;
            double valorDoisSabores = 0.0;
            
            if(formularioPizza.isDoisSabores()) {
                categoria2 = modelSaborDao.getCategoria(pizzaSelecionada.getSabor2());
                valorDoisSabores = modelPrecoDao.getPreco(categoria2) * areaInformada;
            }
            
            double mediaValor = valorUmSabor != valorDoisSabores ? (valorUmSabor + valorDoisSabores) / 2 : valorUmSabor;
            
            Pizza pizza;
            
            if(formularioPizza.isUmSabor()) {
                pizza = new Pizza(formato, sabor1, valorUmSabor);
            } else {
                pizza = new Pizza(formato, sabor1, sabor2, mediaValor);
            }
            
            view.inserirPizzaPedidoTabela(pizza);
            
            double valorTotal = view.getValorTotalPedido();
            
            view.setValorTotalPedido(valorTotal);
            
            if(valorTotal > 0) {
                view.setRealizarPedidoEnabled(true);
            } else {
                view.setRealizarPedidoEnabled(false);
            }
        

        } catch(Exception ex){
        }
        
    }
    
    public void criaPedido() {
        
        List<Pedido> pizzasPedido = view.getPizzasPedido();
        
        Cliente cliente = view.getClientePedido();
        pedidosClienteDao.inserir(pizzasPedido, cliente);
        modelTodosPedidosDao.inserir(pizzasPedido);
        view.inserirPedidoTabela(pizzasPedido);
        view.limpaTabelaPizzasPedido();
        
    }
    
}
