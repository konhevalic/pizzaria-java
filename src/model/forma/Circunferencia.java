/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.forma;

/**
 *
 * @author alan
 */
public class Circunferencia extends Forma {

    public Circunferencia(double raio) {
        setMedida(raio);
    }

    @Override
    public double area() {
        return Math.PI * Math.pow(getMedida(), 2);
    }

    @Override
    public String formato() {
        return "Circunferencia";
    }
    
    @Override
    public double ladoCalculado() {
        double raio = Math.sqrt(getMedida() / Math.PI);
        
        return raio;
    }
    
    
    
    
}
