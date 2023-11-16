/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.pedido;

import java.util.List;
import model.cliente.Cliente;
import model.pizza.Pizza;

/**
 *
 * @author alan
 */
public class Pedido {
    
    private double precoTotal;
    private Pizza pizza;
    private Cliente cliente;
    private int id;
    private StatusPedido status;
    private String telefoneCliente;
    private int numeroPedido;

    
//    public Pedido(Pizza pizza, Cliente cliente, int id, StatusPedido status, double precoTotal) {
//        this.pizza = pizza;
//        this.cliente = cliente;
//        this.id = id;
//        this.status = status;
//        this.precoTotal = precoTotal;
//    }
    
    public Pedido(Pizza pizza, String telefoneCliente, int id, StatusPedido status, double precoTotal, int numeroPedido) {
        this.pizza = pizza;
        this.telefoneCliente = telefoneCliente;
        this.id = id;
        this.status = status;
        this.precoTotal = precoTotal;
        this.numeroPedido = numeroPedido;
    }
    
    
    public Pedido(int numeroPedido, StatusPedido status) {
        this.numeroPedido = numeroPedido;
        this.status = status;
    }
    
    public String getTelefoneCliente() {
        return telefoneCliente;
    }
    
    public int getNumeroPedido() {
        return numeroPedido;
    }
    
    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
    public String getSabor1() {
        return pizza.getSabor1();
    }
    
    public String getSabor2() {
        return pizza.getSabor2();
    }
    
    public double getPreco() {
        return pizza.getPreco();
    }
    
    public String getForma() {
        return pizza.getForma();
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }


    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }
    
    
    
}
