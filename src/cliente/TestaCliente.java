/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.cliente.Cliente;
import model.cliente.dao.ClienteDao;
import database.ConnectionFactory;

/**
 *
 * @author alan
 */
public class TestaCliente {
    
     public static void main(String args[]) throws SQLException{
        List<Cliente> clientes = new ArrayList();
        ClienteDao dao = new ClienteDao(new ConnectionFactory());

        for(Cliente c:clientes){
        System.out.println("sucesso");
    }
        
        //ATUALIZAR DADO DA TABLE
        Cliente c = new Cliente("Novo Nome", "Patrick", "11111111111");
        String novoNome = "333";
        //dao.atualizar(c, novoNome);
        
        dao.excluir(c);
    }
    
}
