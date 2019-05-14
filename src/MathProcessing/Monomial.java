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
        int num = coefficient.getNumerator();
        String numS = String.valueOf(num);
        
        int den = coefficient.getDenominator();
        String denS = String.valueOf(den);
        
        int pow = power;
        String powS = String.valueOf(pow);
        
        if(num == 0)
        {            
            if(pow == 0)
                return "";
            else
                return "x^" + pow;
        }
        else if (pow == 0)
        {
            if(num == 0)
                return "";
            else
                return numS;
        }
        
        return numS + "x^" + powS;
    }
}
