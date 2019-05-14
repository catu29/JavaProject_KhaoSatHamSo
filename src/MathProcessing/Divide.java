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
public class Divide {
    public Fractor divide(int operand1, int operand2)
    {
        Fractor f = new Fractor(operand1, operand2);
        if(!f.isValid())
            return null;
        
        f.simplify();
        return f;
    }
    
    public Monomial divide(Monomial operand1, Monomial operand2)
    {    
        if(operand2.getCoefficient().canReverse())
        {
            operand2.getCoefficient().reverse();
            Fractor coeff = new Fractor(operand1.getCoefficient().multiply(operand2.getCoefficient()));
            
            Monomial mono = new Monomial(coeff, operand1.getPower() - operand2.getPower());
            if(!mono.isValid())
                return null;
            
            return mono;       
        }
        else
        {            
            return null;
        }
    }
        
    public ArrayList<Monomial> divide(ArrayList<Monomial> list1, Monomial list2)
    {
        ArrayList<Monomial> result = new ArrayList<Monomial>();
     
        for(int i = 0; i < list1.size(); i++)
        {
            Monomial re = divide(list1.get(i), list2);
            
            if(re == null)
                return null;
            
            result.add(re);
        }
        
        return MathStaticMethod.compact(result);
    }
    
    public Polynomial divide(Polynomial poly1, Polynomial poly2)
    {
        ArrayList re = divide(poly1.getPolynomial(), poly2.getPolynomial().get(0));
        
        if(re == null)
            return null;
        
        return new Polynomial(re);
    }
}
