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
public class Monomial {
    private Fractor coefficient;
    private int power;
    
    //=== region Constructors
    public Monomial()
    {
        coefficient = new Fractor();
        power = 0;
    }
    
    public Monomial(Fractor coeff)
    {
        coefficient = coeff;
        power = 0;
    }
    
    public Monomial(Fractor coeff, int pow)
    {
        coefficient = coeff;
        power = pow;
    }
    //end of Constructors region
    
    //=== region Getter - Setter
    public Fractor getCoefficient()
    {
        return coefficient;
    }
    
    public int getPower()
    {
        return power;
    }
    
    public void setCoefficient(Fractor coeff)
    {
        coefficient = coeff;
    }
    
    public void setPower(int pow)
    {
        power = pow;
    }
    //end of Getter - Setter region
    
    public boolean isValid()
    {
        if(power < 0)
            return false;
        
        return true;
    }
    
    //Translate negative to positive or in the opposite way
    public void translate()
    {
        coefficient.translate();
    }
    
    @Override
    public String toString()
    {
        String coef = coefficient.toString();
        int pow = power;
        
        if(coef.equals("0"))
        {
            return "";
        }
        
        if(coef.equals("1"))
        {
            if(power == 0)
                return "1";
            
            if(power == 1)
                return "x";
            
            return "x^" + power;
        }
        
        if(coef.equals("-1"))
        {
            if(power == 0)
                return "-1";
            
            if(power == 1)
                return "-x";
            
            return "-x^" + power;
        }
        
        if(power == 0)
            return coef;
        
        if(power == 1)
        {
            if(!coefficient.isInteger())
                return "(" + coef + ")" + "x";
            
            return coef + "x";
        }            
        
        if(!coefficient.isInteger())
            return "(" + coef + ")" + "x^" + power;
        
        return coef + "x^" + power;
    }
}
