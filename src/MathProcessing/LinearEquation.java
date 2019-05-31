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
public class LinearEquation extends CommonEquation{
    public LinearEquation(Polynomial poly)
    {
        super(poly);
    }
    
    @Override
    public String limitation()
    {
        return "Hàm số có giới hạn trong khoảng (-∞; +∞)";
    }
    
    @Override
    public String variant()
    {
        if(expression.getPolynomial().get(0).getCoefficient().isPositive())
        {
            return "Hàm số đồng biến trên R.";
        }
        
        return "Hàm số nghịch biến trên R.";
    }
    
    
    @Override
    public String value()
    {
        double y = calculate(0);
        double y1 = calculate(1);
        
        String str = "Với x = 0, y = " + y + "\nVới x = 1, y = " + y1;
        return str;
    }
    
    @Override
    public String comment()
    {
        return "";
    }
}

