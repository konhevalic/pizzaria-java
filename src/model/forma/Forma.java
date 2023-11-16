/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.forma;

/**
 *
 * @author alan
 */
public abstract class Forma {
    
    private double medida;

    protected void setMedida(double medida) {
            this.medida = medida;
    }
    
    public double getMedida() {
         return medida;
    }
    
    public abstract double area();
    public abstract String formato();
    public abstract double ladoCalculado();
    
}
