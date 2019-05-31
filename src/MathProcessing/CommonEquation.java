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
public abstract class CommonEquation {
    protected Polynomial expression;    
    protected String set = "TXĐ: D = R.";
    
    public CommonEquation(Polynomial poly)
    {
        expression = poly;
    }
    
    public String getSet()
    {
        return set;
    }
    
    public Polynomial getExpression()
    {
        return expression;
    }
    
    abstract public String variant();    
    abstract public String value();
    abstract public String comment();    
    abstract public String limitation();

    public double calculate(double x)
    {
        double sum = 0;
        
        for(int i = 0; i < expression.getPolynomial().size(); i++)
        {
            Fractor f = expression.getPolynomial().get(i).getCoefficient();
            double co = f.getNumerator()*1.0/f.getDenominator();
            double val = Math.pow(x, expression.getPolynomial().get(i).getPower());
            
            sum += co*val;
        }
        
        return sum;
    }        
    
    public Fractor calculate(Fractor x)
    {
        Fractor sum = new Fractor();
        
        for(int i = 0; i < expression.getPolynomial().size(); i++)
        {
            Fractor f = expression.getPolynomial().get(i).getCoefficient();
            int num = (int)Math.pow(x.getNumerator(), expression.getPolynomial().get(i).getPower());
            int den = (int)Math.pow(x.getDenominator(), expression.getPolynomial().get(i).getPower());
            
            sum = sum.add(f.multiply(x));
        }
        
        return sum;
    }  
}
