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
public class QuadraticEquation extends CommonEquation {
    private Fractor Ix;
    private Fractor Iy;
    
    public QuadraticEquation(Polynomial poly)
    {
        super(poly);
    }
    
    public Fractor getIX()
    {
        return Ix;
    }
    
    @Override
    public String limitation()
    {
        return "Hàm số có giới hạn trong khoảng (-∞; +∞)";
    }
    
    @Override
    public String variant() {
        Fractor a = expression.getPolynomial().get(expression.getPolynomial().size() - 1).getCoefficient();
        Fractor b = new Fractor();
        Fractor c = new Fractor();
        
        if(expression.getPolynomial().size() == 3)
        {
            b = expression.getPolynomial().get(1).getCoefficient();
            c = expression.getPolynomial().get(0).getCoefficient();
        }
        else if(expression.getPolynomial().size() == 2)
        {
            if(expression.getPolynomial().get(0).getPower() == 1)
            {
                b = expression.getPolynomial().get(0).getCoefficient();
            }
            else
            {
                c = expression.getPolynomial().get(0).getCoefficient();
            }
        }
                
        //delta = b^2 - 4ac
        Fractor delta = b.multiply(b); //delta = b^2
        delta = delta.subtract(a.multiply(c).multiply(new Fractor(4))); //delta = delta - 4ac
        
        //Ix = -b/2a
        Fractor x = a.multiply(new Fractor(2)); // 2a
        x.reciprocal(); // 1/2a
        Ix = b.multiply(new Fractor(-1)).multiply(x); // -b/2a
        
        //Iy = -delta/4a
        x = x.multiply(new Fractor(1, 2)); // 1/4a
        Iy = delta.multiply(x).multiply(new Fractor(-1));
        
        StringBuffer str = new StringBuffer("Toạ độ đỉnh I(");
        str.append(Ix.toString()).append(", ").append(Iy.toString()).append(").\n");
        str.append("Trục đối xứng x = ").append(Ix.toString()).append(".");
        
        if(a.isPositive())
        {
            str.append("\nHàm số nghịch biến trên (-∞; ").append(Ix.toString()).append(").\n");
            str.append("Hàm số đồng biến trên (").append(Ix.toString()).append("; +∞).");
        }
        else
        {
            str.append("\nHàm số đồng biến trên (-∞; ").append(Ix.toString()).append(").\n");
            str.append("Hàm số nghịch biến trên (").append(Ix.toString()).append("; +∞).");
        }
        
        return str.toString();
    }


    @Override
    public String value() {
        Fractor x1 = Ix.subtract(new Fractor(1));
        Fractor x2 = Ix.add(new Fractor(1));
                
        Fractor y = calculate(x1);
        Fractor y1 = calculate(x2);
        
        String str = "Với x = " + x1 + ", y = " + y + ".\n";
        str += "Với x = " + x2 + ", y = " + y1 + ".\n";
        return str;
    }
    
    @Override
    public String comment()
    {
        StringBuffer s = new StringBuffer("Đồ thị hàm số ");
        s.append(expression.toString());
        s.append(" là một được Parabol (P) có:\n");
        s.append("+ Đỉnh I(");
        s.append(Ix.toString()).append(", ").append(Iy.toString()).append(").\n");
        s.append("+ Trục đối xứng x = ").append(Ix.toString()).append(".");
        
        if(expression.getPolynomial().get(expression.getPolynomial().size() - 1).getCoefficient().isPositive()) // a>0
        {
            s.append("\n+ Parabol (P) quay bề lõm lên trên.");
        }
        else
        {
            s.append("\n+ Parabol (P) quay bề lõm xuống dưới.");
        }
        
        return s.toString();
    }
}
