/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.FormularioPizza;

import model.pizza.Pizza;

/**
 *
 * @author alan
 */
public class FormularioPizza  {
    
    private Pizza pizza;
    private boolean opcaoArea;
    private boolean opcaoFormato;
    private double areaPizzaInformada;
    private double medidaInformada;
    private boolean umSabor;
    private boolean doisSabores;
    
    public FormularioPizza(
            Pizza pizza, 
            boolean opcaoArea, 
            boolean opcaoFormato, 
            double areaPizzaInformada, 
            double medidaInformada, 
            boolean umSabor, 
            boolean doisSabores) {
        
        this.pizza = pizza;
        this.opcaoArea = opcaoArea;
        this.opcaoFormato = opcaoFormato;
        this.areaPizzaInformada = areaPizzaInformada;
        this.medidaInformada = medidaInformada;
        this.umSabor = umSabor;
        this.doisSabores = doisSabores;
        
    }

    public boolean isOpcaoArea() {
        return opcaoArea;
    }

    public void setOpcaoArea(boolean opcaoArea) {
        this.opcaoArea = opcaoArea;
    }

    public boolean isOpcaoFormato() {
        return opcaoFormato;
    }

    public void setOpcaoFormato(boolean opcaoFormato) {
        this.opcaoFormato = opcaoFormato;
    }

    public double getAreaPizzaInformada() {
        return areaPizzaInformada;
    }

    public void setAreaPizzaInformada(double areaPizzaInformada) {
        this.areaPizzaInformada = areaPizzaInformada;
    }

    public double getMedidaInformada() {
        return medidaInformada;
    }

    public void setMedidaInformada(double medidaInformada) {
        this.medidaInformada = medidaInformada;
    }

    public boolean isUmSabor() {
        return umSabor;
    }

    public void setUmSabor(boolean umSabor) {
        this.umSabor = umSabor;
    }

    public boolean isDoisSabores() {
        return doisSabores;
    }

    public void setDoisSabores(boolean doisSabores) {
        this.doisSabores = doisSabores;
    }
    
    public Pizza getPizza() {
        return pizza;
    }    
    
}
