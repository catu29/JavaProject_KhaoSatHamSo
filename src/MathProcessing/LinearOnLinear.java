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
public class LinearOnLinear {
    private Polynomial nume;
    private Polynomial deno;
    private Fractor derivative;
    private Fractor asymstoreX;
    private Fractor asymstoreY;
    
    public LinearOnLinear(Polynomial poly1, Polynomial poly2)
    {
        nume = poly1;
        deno = poly2;
    }
    
    // Check on compacted polynomials
    public boolean isLinearOnLinear()
    {
        nume.checkLinear();
        if(!nume.isLinear())
           return false;
        
        deno.checkLinear();
        if(!deno.isLinear())
            return false;
        
        //Check if numerator and denominator have the same root, then return false.
        //Numerator: ax + b
        // => x = -b/a
        // nu: root of NUmerator polynomial 
        Fractor nu = new Fractor(nume.getPolynomial().get(1).getCoefficient()); // nu = a
        nu.reciprocal(); // nu = 1/a;
        nu = nu.multiply(nume.getPolynomial().get(0).getCoefficient()).multiply(new Fractor(-1)); // nu = 1/a * b * -1
        nu.simplify();
        
        //Denominator: cx + d
        // => x = -d/c
        // de: root of DEnominator polynomial
        Fractor de = new Fractor(deno.getPolynomial().get(1).getCoefficient()); // de = c
        de.reciprocal(); // de = 1/c
        de = de.multiply(deno.getPolynomial().get(0).getCoefficient()).multiply(new Fractor(-1)); // de = 1/c * d * -1
        de.simplify();
                
        if(nu.getNumerator() == de.getNumerator() && nu.getDenominator() == de.getDenominator())
            return false;
        
        // Method return true, calculate values of asymstore x and asymstore y
        // Asymstore x (vertical) value is value of denominator polynomial root. 
        asymstoreX = de;        
        
        Fractor a = nume.getPolynomial().get(1).getCoefficient();
        Fractor b = nume.getPolynomial().get(0).getCoefficient();
        Fractor c = deno.getPolynomial().get(1).getCoefficient();
        Fractor d = deno.getPolynomial().get(0).getCoefficient();
        
        // Asymstore y (horizontal) value is a/c
        asymstoreY = c;
        asymstoreY.reciprocal();
        asymstoreY = asymstoreY.multiply(a);
        asymstoreY.simplify();
        
        // Derivative coefficient value is the result of expression ad-bc
        derivative = a.multiply(d).subtract(b.multiply(c));
        derivative.simplify();
        
        return true;
    }
    
    public String getSet()
    {
        return "TXĐ: D = R\\{" + asymstoreX.toString() + "}.";
    }
    
    public String derivative()
    {
        String s = "y' = (" + derivative.toString() + ") / (" + deno.getPolynomial().toString() + ")^2";
        if(derivative.isPositive())
        {
            return s + " > 0, ∀x ∈ D.";
        }
        
        return s + " < 0, ∀x ∈ D.";
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
        return "\nĐồ thị không có cực trị.\nĐồ thị nhận I(" + asymstoreX.toString() + ", " + asymstoreY.toString() + ") làm tâm đối xứng.";
    }
}   
