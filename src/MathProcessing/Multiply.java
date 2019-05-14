/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathProcessing;

import java.util.ArrayList;

/**
 *
 * @author TranCamTu
 */
public class Multiply {
    public Monomial multiply(Monomial operand1, Monomial operand2)
    {
        Fractor co1 = new Fractor(operand1.getCoefficient());
        if(!co1.isValid())
            return null;
        
        Fractor co2 = new Fractor(operand2.getCoefficient());
        if(!co2.isValid())
            return null;
        
        Monomial mono = new Monomial(co1.multiply(co2), operand1.getPower()+operand2.getPower());
        if(!mono.isValid())
            return null;
        
        return mono;
    }
    
    public ArrayList<Monomial> multiply(ArrayList<Monomial> operand1, ArrayList<Monomial> operand2)
    {
        ArrayList<Monomial> result = new ArrayList<Monomial>();
        
        for(int i = 0; i < operand1.size(); i++)
        { 
            Monomial op1 = operand1.get(i);   
            for(int j = 0; j < operand2.size(); j++)
            {                
                Monomial op2 = operand2.get(j);
                Monomial mono = multiply(op1, op2);
                
                if(mono == null)
                    return null;
                
                result.add(mono);
            }
        }
        
        return MathStaticMethod.compact(result);
    }
    
    public Polynomial multiply(Polynomial poly1, Polynomial poly2)
    {
        ArrayList<Monomial> list1 = poly1.getPolynomial();
        ArrayList<Monomial> list2 = poly2.getPolynomial();
        
        return new Polynomial(multiply(list1, list2));
    }
}
