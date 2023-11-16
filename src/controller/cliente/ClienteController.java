/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.cliente;

import java.util.List;
import model.cliente.Cliente;
import model.cliente.dao.ClienteDao;
import view.Pizzaria;

/**
 *
 * @author alan
 */
public class ClienteController {
    
    private final Pizzaria view;
    private final ClienteDao modelClienteDao;

    public ClienteController(Pizzaria view, ClienteDao modelClienteDao) {
        this.view = view;
        this.modelClienteDao = modelClienteDao;
        initController();
    }
    
    private void initController(){
        this.view.setClienteController(this);
        this.view.initView();
    }
    
    public void cadastrarCliente() {
        try{
            Cliente cliente = view.getClienteFormulario();

            if(cliente != null) {
                modelClienteDao.inserir(cliente);
                view.inserirClienteTabela(cliente);
            }
            
        } catch(Exception ex){
        }
    }
    
    public void atualizarCliente() {
        
        try{
            Cliente cliente = view.getClienteParaAtualizar();
            
            modelClienteDao.atualizar(cliente);
            view.atualizarClienteTabela();

        }catch(Exception ex){
            
        }
    }
    
    public void excluirCliente() {
        try{
            List<Cliente> listaParaExcluir = view.getClientesParaExcluir();
            modelClienteDao.exluirLista(listaParaExcluir);
            view.excluirCliente();
        }catch(Exception ex){
            
        }
    }

    public void listarTodosClientes() {
        try{
            view.limparClienteAtualizar();
            List<Cliente> lista = this.modelClienteDao.getLista();
            
            view.mostraListaClientes(lista);
        }catch(Exception ex){
        }
    }
    
    public void filtraCliente() {
        try {
            
            Cliente cliente = view.filtrarCliente();
            List<Cliente> lista = this.modelClienteDao.filtra(cliente);
            view.mostraListaClientes(lista);
            
        }catch(Exception ex){
        }
    }
    
}
