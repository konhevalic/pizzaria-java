/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.preco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.preco.Preco;
import model.preco.dao.PrecoDao;
import view.Pizzaria;

/**
 *
 * @author alan
 */
public class PrecoController {
    
    private final Pizzaria view;
    private final PrecoDao modelPrecoDao;
    
    public PrecoController(Pizzaria view, PrecoDao modelPrecoDao) {
        this.view = view;
        this.modelPrecoDao = modelPrecoDao;
        initController();
    }
    
    private void initController(){
        this.view.setPrecoController(this);
        this.view.initView();
    }
    
    public void atualizarPreco() {

        try {
            
            Preco preco = view.getPreco();
            modelPrecoDao.atualizar(preco);
            
            view.atualizarPrecoTabela(modelPrecoDao.getPrecos());
            
        } catch(Exception ex) {
            
        }
        
        
    }
    
    
}
