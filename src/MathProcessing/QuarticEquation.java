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
public class QuarticEquation extends CommonEquation {
    private Fractor a;
    private Fractor b;
    private Fractor c;
    
    private double x1;
    private double x2; // roots of y'
    
    private double inflectionLeft;
    private double inflectionRight; // roots of y"
    
    private Fractor root;

    public QuarticEquation(Polynomial poly) {
        super(poly);
        
        a = new Fractor();
        b = new Fractor();
        c = new Fractor();
        
        //y = ax^4 + bx^2 + c
        for(int i = 0; i < expression.getPolynomial().size(); i++)
        {
            if(expression.getPolynomial().get(i).getPower() == 4)
            {
                a = expression.getPolynomial().get(i).getCoefficient();
            }
            else if(expression.getPolynomial().get(i).getPower() == 2)
            {
                b = expression.getPolynomial().get(i).getCoefficient();
            }
            else
            {
                c = expression.getPolynomial().get(i).getCoefficient();
            }
        }
        
        root = a;
        root.reciprocal();
        root = b.multiply(new Fractor(-1)).multiply(new Fractor(1,2)).multiply(root); //root = -b/2a
    }
    
    @Override
    public String limitation()
    {
        if(a.isPositive())
        {
            return "lim(x->–∞) y = +∞. lim(x->+∞) y = +∞";
        }
        
        return "lim(x->–∞) y = -∞. lim(x->+∞) y = -∞";
    }

    @Override
    public String variant() {
        expression.createDerivative();
        StringBuffer str = new StringBuffer("Ta có:");
        str.append("\ny' = ").append(expression.derivativeString());
        str.append("\ny' = 0 <=> ").append(expression.derivativeString()).append(" = 0");       
        
        str.append("\n<=> x = 0 hoặc x^2 = ").append(root.toString());
        
        if(root.isPositive())
        {
            x1 = Math.sqrt(1.0*root.getNumerator()/root.getDenominator());
            x2 = -Math.sqrt(1.0*root.getNumerator()/root.getDenominator());
            str.append("\n<=> x = 0, hoặc x = ").append(x1).append(", hoặc x = ").append(x2);
            
            if(a.isPositive())
            {
                if(x1 < x2)
                {
                    str.append("\nHàm số nghịch biến trên (-∞; ").append(x1).append(") và (0; ").append(x2).append(").");
                    str.append("\nHàm số đồng biến trên (").append(x1).append("; 0) và (").append(x2).append("; +∞).");
                }
                else
                {
                    str.append("\nHàm số nghịch biến trên (-∞; ").append(x2).append(") và (0; ").append(x1).append(").");
                    str.append("\nHàm số đồng biến trên (").append(x2).append("; 0) và (").append(x1).append("; +∞).");
                }
            }
            else
            {
                if(x1 < x2)
                {
                    str.append("\nHàm số đồng biến trên (-∞; ").append(x1).append(") và (0; ").append(x2).append(").");
                    str.append("\nHàm số nghịch biến trên (").append(x1).append("; 0) và (").append(x2).append("; +∞).");
                }
                else
                {
                    str.append("\nHàm số đồng biến trên (-∞; ").append(x2).append(") và (0; ").append(x1).append(").");
                    str.append("\nHàm số nghịch biến trên (").append(x2).append("; 0) và (").append(x1).append("; +∞).");
                }
            }
        }
        else
        {
            if(a.isPositive())
            {
                str.append("\nHàm số nghịch biến trên (-∞; 0) và đồng biến trên (0; +∞)");
            }
            else
            {
                str.append("\nHàm số đồng biến trên (-∞; 0) và nghịch biến trên (0; +∞)");
            }
        }
        
        return str.toString();
    }

    @Override
    public String value() {
        Fractor y = calculate(root);        
        double distance = Math.abs(x2 - x1);        
        double yL = calculate(expression.getPolynomial(), -distance);
        
        StringBuffer str = new StringBuffer("Tại x = 0, y = ");
        str.append(c.toString());
        str.append("\nTại x = ").append(x1).append(", y = ").append(y);
        str.append("\nTại x = ").append(x2).append(", y = ").append(y);
        str.append("\nTại x = ").append(-distance).append(", y = ").append(yL);
        str.append("\nTại x = ").append(distance).append(", y = ").append(yL);
        
        return str.toString();
    }

    @Override
    public String comment() {
        Polynomial poly = new Polynomial(expression.getDerivative());
        poly.createDerivative();
        
        StringBuffer str = new StringBuffer("\nTa có:\ny\" = ");
        str.append(poly.derivativeString());
        str.append("\ny\" = 0 <=> ").append(poly.derivativeString()).append(" = 0");
        
        Fractor f = root.multiply(new Fractor(1, 3));        
        str.append("\n<=> x^2 = ").append(f.toString());
        
        if(f.isPositive())
        {
            inflectionLeft = -Math.abs(1.0*f.getNumerator()/f.getDenominator());
            inflectionRight = Math.abs(1.0*f.getNumerator()/f.getDenominator());
            double yL = calculate(poly.getPolynomial(), inflectionLeft);
            
            str.append("\nĐồ thị hàm số có hai điểm uốn: ");
            str.append("I1(").append(inflectionLeft).append(", ").append(yL).append("), ");
            str.append("I2(").append(inflectionRight).append(", ").append(yL).append(").");
        }
        else
        {
            str.append("\nĐồ thị hàm số không có điểm uốn.");
        }
        
        if(a.isPositive())
        {
            if(root.isPositive())
            {
                double y = calculate(expression.getPolynomial(), x1);
                
                str.append("\nHàm số đạt cực tiểu tại hai điểm: ");
                str.append("(").append(x1).append(", ").append(y).append("), ");
                str.append("(").append(x2).append(", ").append(y).append(").");
                str.append("\nHàm số đạt cực đại tại điểm: (0, ").append(c.toString()).append(").");                
            }
            else
            {
                str.append("\nHàm số đạt cực tiểu tại điểm: (0, ").append(c.toString()).append(").");
            }
        }
        else
        {
            if(root.isPositive())
            {
                double y = calculate(expression.getPolynomial(), x1);
                
                str.append("\nHàm số đạt cực đại tại hai điểm: ");
                str.append("(").append(x1).append(", ").append(y).append("), ");
                str.append("(").append(x2).append(", ").append(y).append(").");
                str.append("\nHàm số đạt cực tiểu tại điểm: (0, ").append(c.toString()).append(").");                
            }
            else
            {
                str.append("\nHàm số đạt cực đại tại điểm: (0, ").append(c.toString()).append(").");
            }
        }
        
        return str.toString();
    } 
    
    public Fractor calculate(Fractor f)
    {
        Fractor result = new Fractor();
        for(int i = 0; i < expression.getPolynomial().size(); i++)
        {
            Monomial mono = expression.getPolynomial().get(i);
            if(mono.getPower() == 4)
            {
                result = result.add(mono.getCoefficient().multiply(f).multiply(f));
            }
            else if(mono.getPower() == 2)
            {
                result = result.add(mono.getCoefficient().multiply(f));
            }
            else
            {
                result = result.add(mono.getCoefficient());
            }
        }
        
        return result;
    }
}
