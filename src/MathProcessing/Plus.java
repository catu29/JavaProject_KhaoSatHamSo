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
public class Plus {  
    public ArrayList<Monomial> plus(Monomial operand1, Monomial operand2)
    {
        ArrayList<Monomial> result = new ArrayList<Monomial>();
        
        if(operand1.getPower() == operand2.getPower())
        {
            result.add(new Monomial(operand1.getCoefficient().add(operand2.getCoefficient()), operand1.getPower()));
        }        
        else
        {
            result.add(operand1);
            result.add(operand2);
        }
        
        return result;
    }
    
    public ArrayList<Monomial> plus(ArrayList<Monomial> operand1, ArrayList<Monomial> operand2)
    {
        ArrayList<Monomial> operands = operand1;
        operands.addAll(operand2);     
                        
        return MathStaticMethod.compact(operands);   
    }
    
    public Polynomial plus(Polynomial poly1, Polynomial poly2)
    {
        ArrayList<Monomial> list1 = poly1.getPolynomial();
        ArrayList<Monomial> list2 = poly2.getPolynomial();
        
        if(list1.size() == 1 && list2.size() == 1)
        {
            return new Polynomial(plus(list1.get(0), list2.get(0)));
        }
        else
        {
            return new Polynomial(plus(list1, list2));
        }
    }
}
