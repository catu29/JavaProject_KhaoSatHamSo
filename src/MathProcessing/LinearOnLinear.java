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
public class LinearOnLinear extends PolyOnPoly{
    private Fractor derivative;
    private Fractor asymstoreX;
    private Fractor asymstoreY;
    
    private Fractor a;
    private Fractor b;
    private Fractor c;
    private Fractor d;
    
    public LinearOnLinear(Polynomial poly1, Polynomial poly2)
    {
        super(poly1, poly2);
        
        // f(x) = (ax + b)/(cx + d)
        
        a = new Fractor(nume.getPolynomial().get(nume.getPolynomial().size()-1).getCoefficient());
        b = new Fractor();
        if(nume.getPolynomial().size() > 1)
        {
            b = new Fractor(nume.getPolynomial().get(0).getCoefficient());
        }
        
        c = new Fractor(deno.getPolynomial().get(deno.getPolynomial().size()-1).getCoefficient());
        d = new Fractor();
        if(deno.getPolynomial().size() > 1)
        {
            d = new Fractor(deno.getPolynomial().get(0).getCoefficient());
        }
        
        // Asymstore x (vertical) value is value of denominator polynomial root.         
        //Denominator: cx + d
        // => x = -d/c
        // de: root of DEnominator polynomial
        
        Fractor de = new Fractor();
        de = new Fractor(c); // de = c
        de.reciprocal(); // de = 1/c
        de = de.multiply(d).multiply(new Fractor(-1));
        de.simplify(); // de = 1/c * d * -1 = -d/c
        
        asymstoreX = de; 
        
        // Asymstore y (horizontal) value is a/c
        asymstoreY = new Fractor(c);
        asymstoreY.reciprocal();
        asymstoreY = asymstoreY.multiply(a);
        asymstoreY.simplify();
        
        // Derivative coefficient value is the result of expression ad-bc
        derivative = a.multiply(d).subtract(b.multiply(c));
        derivative.simplify();
    }   
    
    public Fractor getDerivative()
    {
        return derivative;
    }
    
    public Fractor getAsymstoreX()
    {
        return asymstoreX;
    }
    
    public Fractor getAsymstoreY()
    {
        return asymstoreY;
    }
    
    public String getSet()
    {
        return "TXĐ: D = R\\{" + asymstoreX.toString() + "}.";
    }
    
    public String derivative()
    {
//        String s = "y' = (" + derivative.toString() + ") / (" + deno.getPolynomial().toString() + ")^2";
        if(derivative.isPositive())
        {
            return " > 0, ∀x ∈ D.";
        }
        
        return " < 0, ∀x ∈ D.";
    }
        
    public String variant()
    {
        StringBuilder str = new StringBuilder("Chiều biến thiên:\nHàm số luôn ");
        
        if(derivative.isPositive())
        {
            str.append(" tăng trên D.");
            str.append("\nGiới hạn:\nlim(x->-∞) y = lim(x->+∞) y = ").append(asymstoreY.toString());
            str.append("\nlim(x->").append(asymstoreX.toString()).append("-) = -∞");
            str.append("\nlim(x->").append(asymstoreX.toString()).append("+) = +∞");
        }
        else
        {
            str.append(" giảm trên D.");
            str.append("\nGiới hạn:\nlim(x->-∞) y = lim(x->+∞) y = ").append(asymstoreY.toString());
            str.append("\nlim(x->").append(asymstoreX.toString()).append("-) = +∞");
            str.append("\nlim(x->").append(asymstoreX.toString()).append("+) = -∞");
        }
        
        str.append("\nVậy, tiệm cận đứng: x = ").append(asymstoreX.toString());
        str.append("; tiệm cận ngang: y = ").append(asymstoreY.toString());
        
        return str.toString();
    }
    
    public String comment()
    {
        return "Đồ thị không có cực trị.\nĐồ thị nhận I(" + asymstoreX.toString() + ", " + asymstoreY.toString() + ") làm tâm đối xứng.";
    }
    
    public Fractor calculate(Fractor x)
    {
        CommonEquation n = new AnotherEquation(nume);
        Fractor sumN = n.calculate(x);
        
        CommonEquation d = new AnotherEquation(deno);
        Fractor sumD = d.calculate(x);
        sumD.reciprocal();
        
        return sumN.multiply(sumD);
    }
    
    public double calculate(double x)
    {
        CommonEquation n = new AnotherEquation(nume);
        double sumN = n.calculate(x);
        
        CommonEquation d = new AnotherEquation(deno);
        double sumD = d.calculate(x);
        
        return sumN/sumD;
    }
    
    public boolean isSimplified()
    {
        //Check if numerator and denominator have the same root, then return true.
        //Numerator: ax + b
        // => x = -b/a
        // nu: root of NUmerator polynomial 
        Fractor nu = new Fractor(a); // nu = a
        nu.reciprocal(); // nu = 1/a;
        nu = nu.multiply(b).multiply(new Fractor(-1)); // nu = 1/a * b * -1 = -b/a
        nu.simplify();
        
        //Denominator: cx + d
        // => x = -d/c
        // de: root of DEnominator polynomial
        Fractor de = new Fractor(c); // de = c
        de.reciprocal(); // de = 1/c
        de = de.multiply(d).multiply(new Fractor(-1)); // de = 1/c * d * -1 = -d/c
        de.simplify();
                
        if(nu.getNumerator() == de.getNumerator() && nu.getDenominator() == de.getDenominator())
            return true;
        
        return false;
    }
}   
