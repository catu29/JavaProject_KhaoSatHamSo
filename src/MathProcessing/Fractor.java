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
public class Fractor {
    private int numerator;
    private int denominator;
    
    //=== region Constructors
    public Fractor()
    {
        numerator = 0;
        denominator = 1;
    }
    
    public Fractor(int num)
    {
        numerator = num;
        denominator = 1;
    }
    
    public Fractor(Fractor f)
    {
        numerator = f.getNumerator();
        denominator = f.getDenominator();
    }
    
    public Fractor(int num, int deno)
    {
        numerator = num;
        denominator = deno;
    }
    //end of Constructors region
    
    
    //=== region Getter - Setter
    public int getNumerator()
    {
        return this.numerator;
    }
    
    public int getDenominator()
    {
        return this.denominator;
    }
    
    public void setNumerator(int num)
    {
        numerator = num;
    }
    
    public void setDenominator(int deno)
    {
        denominator = deno;
    }
    //end of Getter - Setter region
    
    //=== region of Fractor Operations
        
    public boolean isValid()
    {
        if (denominator == 0)
            return false;
        
        return true;
    }
      
    public boolean isPositive()
    {
        if(numerator * denominator > 0)
            return true;
        
        return false;
    }
    
    public boolean isInteger()
    {
        if(denominator == 1 || denominator == -1)
            return true;
        
        return false;
    }
    
    public void simplify()
    {
        int num = Math.abs(numerator);
        int deno = Math.abs(denominator);
        
        int gcd = MathStaticMethod.findGCD(num, deno);
        
        numerator /= gcd;
        denominator /= gcd;
        
        if(numerator*denominator < 0)
        {
            numerator = -Math.abs(numerator);
            denominator = Math.abs(denominator);
        }
    }
    
    public Fractor add(Fractor f)
    {
        Fractor sum = new Fractor();
        
        sum.setDenominator(denominator * f.getDenominator());
        sum.setNumerator(denominator * f.getNumerator() + numerator * f.getDenominator());
        
        sum.simplify();
        
        return sum;
    }
    
    public Fractor subtract(Fractor f)
    {
        Fractor sub = new Fractor();
        
        sub.setDenominator(denominator * f.getDenominator());
        sub.setNumerator(numerator * f.getDenominator() - denominator * f.getNumerator());
        
        sub.simplify();
        
        return sub;
    }
    
    public Fractor multiply(Fractor f)
    {
        Fractor mul = new Fractor(numerator * f.getNumerator(), denominator * f.getDenominator());
        
        mul.simplify();
        return mul;
    }
    
    public boolean canReciprocal()
    {
        if(numerator == 0)
            return false;
        
        return true;
    }
    
    public void reciprocal()
    {
        int temp = numerator;
        numerator = denominator;
        denominator = temp;
    }
    
    public void translate()
    {
        numerator = -numerator;
    }
    
    @Override
    public String toString()
    {
        String s = "";
        
        if(numerator == 0)
            return "0";
        
        if(denominator == 1)
            return s + numerator;
        
        if(denominator == -1)
            return s + (-numerator);
        
        if(!isPositive())
            return -Math.abs(numerator) + "/" + Math.abs(denominator);
                        
        return numerator + "/" + denominator;        
    }
    //end of Fractor Operations region
}
