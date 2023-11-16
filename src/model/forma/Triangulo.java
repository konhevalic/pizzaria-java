/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.forma;

/**
 *
 * @author alan
 */
public class Triangulo extends Forma{
    

    public Triangulo(double lado) {
        setMedida(lado);
    }

    @Override
    public double area() {
        double sp = (getMedida() + getMedida() + getMedida())/2;
        double aux = sp * (sp-getMedida()) * (sp-getMedida()) * (sp-getMedida());
        
        return Math.sqrt(aux);
    }
    
    @Override
    public String formato() {
        return "Triangulo";
    }
    
    @Override
    public double ladoCalculado() {
        double lado = Math.sqrt(4 * getMedida() / Math.sqrt(3));
        
        return lado;
    }
    
    
}
