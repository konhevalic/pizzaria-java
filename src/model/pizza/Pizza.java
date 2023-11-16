/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.pizza;

import model.forma.Forma;

/**
 *
 * @author alan
 */
public class Pizza {
    
    private String forma;
    private String novoSabor;
    private String sabor1;
    private String sabor2;
    private double preco;
    private String categoria;
    
        public Pizza(String forma, String sabor1, String sabor2) {
        this.forma = forma;
        this.sabor1 = sabor1;
        this.sabor2 = sabor2;
    }


    public Pizza(String forma, String sabor1, String sabor2, double preco) {
        this.forma = forma;
        this.sabor1 = sabor1;
        this.sabor2 = sabor2;
        this.preco = preco;
    }
    
    public Pizza(String forma, String sabor1, double preco) {
        this.forma = forma;
        this.sabor1 = sabor1;
        this.preco = preco;
    }
    
    public Pizza(String novoSabor, String categoria) {
        this.novoSabor = novoSabor;
        this.categoria = categoria;
    }
    
    public Pizza(String categoria, double preco) {
        this.categoria = categoria;
        this.preco = preco;
    }
    
    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getSabor1() {
        return sabor1;
    }

    public void setSabor1(String sabor1) {
        this.sabor1 = sabor1;
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
    
    public String getSabor2() {
        return sabor2;
    }

    public void setSabor2(String sabor2) {
        this.sabor2 = sabor2;
    }
    
    public String getNovoSabor() {
        return novoSabor;
    }

    public void setNovoSabor(String novoSabor) {
        this.novoSabor = novoSabor;
    }
    
}
