/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathProcessing;

/**
 *
 * @author TranCamTu
 */
public class PolyOnPoly {    
    protected Polynomial nume;
    protected Polynomial deno;
    
    public PolyOnPoly(Polynomial poly1, Polynomial poly2)
    {
        nume = poly1;
        deno = poly2;
    }
    
    // Check on compacted polynomials
    public boolean isLinearOnLinear()
    {
        nume.checkLinear();
        if(!nume.isLinear())
           return false;
        
        deno.checkLinear();
        if(!deno.isLinear())
            return false;
        
        return true;
    }
}
