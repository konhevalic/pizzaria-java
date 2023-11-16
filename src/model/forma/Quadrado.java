/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.forma;

/**
 *
 * @author alan
 */
public class Quadrado extends Forma {
    

    public Quadrado(double lado) {
        setMedida(lado);
    }


    @Override
    public double area() {
        return getMedida() * getMedida();
    }
    
    @Override
    public String formato() {
        return "Quadrado";
    }
    
    @Override
    public double ladoCalculado() {
        double lado = Math.sqrt(getMedida());
        
        return lado;
    }
    
    
    
}
