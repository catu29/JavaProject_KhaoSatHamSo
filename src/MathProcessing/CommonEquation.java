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
    
    abstract public String variant();    
    abstract public String value();
    abstract public String comment();    
    abstract public String limitation();

    public double calculate(ArrayList<Monomial> arr, double x)
    {
        double sum = 0;
        
        for(int i = 0; i < arr.size(); i++)
        {
            Fractor f = arr.get(i).getCoefficient();
            double co = f.getNumerator()*1.0/f.getDenominator();
            double val = Math.pow(x, arr.get(i).getPower());
            
            sum += co*val;
        }
        
        return sum;
    }        
    
    public Fractor calculate(ArrayList<Monomial> arr, Fractor x)
    {
        Fractor sum = new Fractor();
        
        for(int i = 0; i < arr.size(); i++)
        {
            Fractor f = arr.get(i).getCoefficient();
            int num = (int)Math.pow(f.getNumerator(), arr.get(i).getPower());
            int den = (int)Math.pow(f.getDenominator(), arr.get(i).getPower());
            
            sum.add(f);
        }
        
        return sum;
    }  
}
