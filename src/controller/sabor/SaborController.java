/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.sabor;

import model.sabor.Sabor;
import model.sabor.dao.SaborDao;
import view.Pizzaria;

/**
 *
 * @author alan
 */
public class SaborController {
    
    private final Pizzaria view;
    private final SaborDao modelSaborDao;
    
    public SaborController(Pizzaria view, SaborDao modelSaborDao) {
        this.view = view;
        this.modelSaborDao = modelSaborDao;
        initController();
    }
    
    private void initController(){
        this.view.setSaborController(this);
        this.view.initView();
    }
    
    public void criaSabor() {
        try{
            Sabor sabor = view.getSaborFormulario();
          
            modelSaborDao.inserir(sabor);
            view.inserirSaborTabela(sabor);
            
        } catch(Exception ex){
        }
    }

    
}
