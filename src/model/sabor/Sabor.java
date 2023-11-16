/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.sabor;

/**
 *
 * @author alan
 */
public class Sabor {
    
    private String sabor;
    private String tipo;

    public Sabor(String sabor, String tipo) {
        this.sabor = sabor;
        this.tipo = tipo;
    }
    
    public Sabor(String sabor) {
        this.sabor = sabor;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
