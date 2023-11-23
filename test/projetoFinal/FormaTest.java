/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package projetoFinal;

import model.forma.Circunferencia;
import model.forma.Forma;
import model.forma.Quadrado;
import model.forma.Triangulo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author alan
 */
public class FormaTest {

    public FormaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of area method, of class Quadrado.
     */
    @Test
    public void testRetangulo() {
        Forma retangulo = new Quadrado(5);
        double expResult = 25.0;
        double result = retangulo.area();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    @Test
    public void testaTriangulo() {
        Forma triangulo = new Triangulo(5);
        double expResult = 10.825;
        double result = triangulo.area();
        assertEquals(expResult, result, 0.03);
    }
    
    @Test
    public void testaCirculo() {
        Forma circulo = new Circunferencia(5);
        double expResult = 78.539;
        double result = circulo.area();
        System.out.println(result);
        assertEquals(expResult, result, 0.03);
        
    }
    
}
