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
public class CubicEquation extends CommonEquation {
    
    //y = ax^3 + bx^2 + cx + d
    private Fractor a;
    private Fractor b;
    private Fractor c;
    private Fractor d;
    
    private Fractor inflectionPoint; // root of y''
    private Fractor delta; // for finding root(s) of y'
    private double x1;
    private double x2; // roots of y'
    
    public CubicEquation(Polynomial poly) {
        super(poly);
           
        a = new Fractor();
        b = new Fractor();
        c = new Fractor();
        d = new Fractor();
        if(expression.getPolynomial().size() > 1)
        {
            for(int i = 0; i < expression.getPolynomial().size(); i++)
            {
                Monomial mono = expression.getPolynomial().get(i);
                if(mono.getPower() == 3)
                {
                    a = mono.getCoefficient();
                }
                else if(mono.getPower() == 2)
                {
                    b = mono.getCoefficient();
                }
                else if(mono.getPower() == 1)
                {
                    c = mono.getCoefficient();
                }
                else
                {
                    d = mono.getCoefficient();
                }
            }
        }
        
        //y' = 3ax^2 + 2bx + c
        // => delta = (2b)^2 - 4*3a*c = 4b^2 - 12ac
        delta = (b.multiply(b)).multiply(new Fractor(4)); // b^2
        delta = delta.subtract((a.multiply(c)).multiply(new Fractor(12)));        
        
        if(delta.isPositive())
        {
            double b1 = (-2.0*b.getNumerator())/b.getDenominator();
            double d = Math.sqrt((1.0*delta.getNumerator())/delta.getDenominator());
            double a1 = (6.0*a.getNumerator())/a.getDenominator();
            x1 = (b1+d)/a1;
            x2 = (b1-d)/a1;
        }
        //Inflection point is the root of y''
        //y' = 3ax^2 + 2bx + c
        //y'' = 6ax + 2b
        // => y''=0 <=> x = -b/3a     
        Fractor a1 = a;
        a1.reciprocal();
        
        inflectionPoint = b.multiply(new Fractor(-1)).multiply(a1).multiply(new Fractor(1,3));
        
    }
    
    public String limitation()
    {
        if(a.isPositive())
        {
            return "lim(x->–∞) y = -∞. lim(x->+∞) y = +∞";
        }
        
        return "lim(x->–∞) y = +∞. lim(x->+∞) y = -∞";
    }
           
    @Override
    public String variant() {
        expression.createDerivative();
        StringBuffer str = new StringBuffer("");
        str.append("Ta có:\n").append("y'=").append(expression.derivativeString());
        str.append("\ny'=0 <=> ").append(expression.derivativeString()).append("=0");
        if(a.isPositive())
        {
            if(delta.isPositive())
            {
                str.append("Δ = ").append(delta.toString()).append(" > 0.\n");
                str.append("=> x1=").append(x1).append(", x2=").append(x2).append("\n");
                if(x1 < x2)                {
                    
                    str.append("Hàm số đồng biết trên (–∞;").append(x1).append("), (").append(x2).append(";+∞).\n");
                    str.append("Hàm số nghịch biến trên (").append(x1).append(";").append(x2).append(").");
                }
                else
                {
                    str.append("Hàm số đồng biết trên (–∞;").append(x2).append("), (").append(x1).append(";+∞).\n");
                    str.append("Hàm số nghịch biến trên (").append(x2).append(";").append(x1).append(").");
                }
            }
            else 
            {
                if(delta.getNumerator()*delta.getDenominator() == 0)
                {
                    str.append("Δ = ").append(delta.toString()).append(" = 0.\n");
                    str.append("=> y' có nghiệm kép x0=").append(inflectionPoint.toString());
                }
                else
                {
                    str.append("Δ = ").append(delta.toString()).append(" < 0, a=").append(a.toString()).append(" > 0");
                    str.append("=> y' > 0, ");
                }
            
                str.append("∀x ∈ R: Hàm số luôn tăng trên R.");
            }            
        }
        else
        {
            if(delta.isPositive())
            {
                str.append("\nΔ = ").append(delta.toString()).append(" > 0.\n");
                str.append("=> x1=").append(x1).append(", x2=").append(x2).append("\n");
                if(x1 < x2)                
                {                    
                    str.append("Hàm số nghịch biết trên (–∞;").append(x1).append("), (").append(x2).append(";+∞).\n");
                    str.append("Hàm số đồng biến trên (").append(x1).append(";").append(x2).append(").");
                }
                else
                {
                    str.append("Hàm số nghịch biết trên (–∞;").append(x2).append("), (").append(x1).append(";+∞).\n");
                    str.append("Hàm số đồng biến trên (").append(x2).append(";").append(x1).append(").");
                }
            }
            else 
            {
                if(delta.getNumerator()*delta.getDenominator() == 0)
                {
                    str.append("\nΔ = ").append(delta.toString()).append(" = 0.\n");
                    str.append("=> y' có nghiệm kép x0=").append(inflectionPoint.toString());
                }
                else
                {
                    str.append("Δ = ").append(delta.toString()).append(" < 0, a=").append(a.toString()).append(" > 0");
                    str.append("=> y' < 0, ");
                }
            
                str.append("∀x ∈ R: Hàm số luôn giảm trên R.");
            }
        }
         
        return str.toString();
    }

    @Override
    public String value() {
        Fractor y = calculate(expression.getPolynomial(), inflectionPoint);
        double y1 = calculate(expression.getPolynomial(), x1);
        double y2 = calculate(expression.getPolynomial(), x2);
        
        int distance = (int)Math.abs(inflectionPoint.getNumerator()/inflectionPoint.getDenominator() - x1) + 1; // every point in graph is symmetry by inflection point
        Fractor yL = calculate(expression.getPolynomial(), new Fractor(-distance)); // Point to the left of inflection point
        Fractor yR = calculate(expression.getPolynomial(), new Fractor(distance)); // Point to the right of inflection point
        
        StringBuffer str = new StringBuffer("Tại x = ");
        str.append(inflectionPoint.toString()).append(", y = ").append(y.toString());
        str.append("\nTại x = ").append(x1).append(", y = ").append(y1);
        str.append("\nTại x = ").append(x2).append(", y = ").append(y2);
        str.append("\nTại x = ").append(new Fractor(-distance).toString()).append(", y = ").append(yL);
        str.append("\nTại x = ").append(new Fractor(distance).toString()).append(", y = ").append(yR);
        
        return str.toString();
    }

    @Override
    public String comment() {        
        Polynomial poly = new Polynomial(expression.getDerivative());
        poly.createDerivative();
        
        StringBuffer str = new StringBuffer("Ta có: y''=");
        str.append(poly.derivativeString()).append("\n");
        str.append("y''=0 <=> ").append(poly.derivativeString()).append("=0\n");
        str.append("<=> x=").append(inflectionPoint.toString()).append("\n");
        
        Fractor y = calculate(expression.getPolynomial(), inflectionPoint);
        str.append("Đồ thị nhận điểm I(").append(inflectionPoint.toString()).append(",").append(y.toString()).append(") làm điểm uốn.\n");
        
        if(delta.isPositive())
        {
            double y1 = calculate(expression.getPolynomial(), x1);
            double y2 = calculate(expression.getPolynomial(), x2);
            
            if(a.isPositive())
            {
                if(x1 < x2) // x1 is max and x2 is min
                {
                    str.append("Hàm số đạt cực đại tại (").append(x1).append(",").append(y1).append(").\n");
                    str.append("Hàm số đạt cực tiểu tại (").append(x2).append(",").append(y2).append(").\n");
                }
                else // x2 is max and x1 is min
                {
                    str.append("Hàm số đạt cực đại tại (").append(x2).append(",").append(y2).append(").\n");
                    str.append("Hàm số đạt cực tiểu tại (").append(x1).append(",").append(y1).append(").\n");
                }
            }
            else
            {
                if(x1 < x2) // x1 is min and x2 is max
                {
                    str.append("Hàm số đạt cực đại tại (").append(x2).append(",").append(y2).append(").\n");
                    str.append("Hàm số đạt cực tiểu tại (").append(x1).append(",").append(y1).append(").\n");
                }
                else // x2 is min and x1 is max
                {
                    str.append("Hàm số đạt cực đại tại (").append(x1).append(",").append(y1).append(").\n");
                    str.append("Hàm số đạt cực tiểu tại (").append(x2).append(",").append(y2).append(").\n");
                }
            }
        }
        else
        {
            str.append("Đồ thị hàm số không có cực trị.");
        }
        
        return str.toString();
    }
    
}

