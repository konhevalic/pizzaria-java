/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.preco;

import model.pizza.Pizza;

/**
 *
 * @author alan
 */
public class Preco {
    
    private double preco;
    private String categoria;
    
    public Preco(double preco, String categoria) {
        this.preco = preco;
        this.categoria = categoria;
    }
    
    public Preco(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }   
    
}
