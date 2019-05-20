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
public class Polynomial {
    private ArrayList<Monomial> polynomial;
    private ArrayList<Monomial> derivative;
    private boolean isValid = true;    
    private boolean isLinear = false;
    private boolean isQuadratic = false;
    private boolean isCubic = false;
    private boolean isQuartic = false;
    
    public ArrayList<Monomial> getPolynomial()
    {
        return polynomial;
    }
    
    public ArrayList<Monomial> getDerivative()
    {
        return derivative;
    }
    
    public void setPolynomial(ArrayList<Monomial> poly)
    {
        if(polynomial != null)
            polynomial.clear();
        polynomial.addAll(poly);
    }
    
    public Polynomial()
    {
        if(polynomial == null)
            polynomial = new ArrayList<Monomial>();
    }
    
    public Polynomial(Monomial mono)
    {
        if(polynomial == null)
            polynomial = new ArrayList<Monomial>();
        
        if(polynomial != null)
            polynomial.clear();        
        
        polynomial.add(mono);
    }
    
    public Polynomial(ArrayList<Monomial> poly)
    {        
        if(polynomial == null)
            polynomial = new ArrayList<Monomial>();
        
        if(polynomial != null)
            polynomial.clear();
        
        polynomial.addAll(poly);
    }
    
    public Polynomial(Polynomial poly)
    {
        if(polynomial == null)
            polynomial = new ArrayList<Monomial>();
        
        if(polynomial != null)
            polynomial.clear();
        
        polynomial.addAll(poly.getPolynomial());
    }
    
    public boolean isValid()
    {
        return isValid;
    }
    
    public void setValid(boolean b)
    {
        isValid = b;
    }
    
    //Using DFS algorithm to create an array of monomial (polynomial) from existing expression tree
    public void create(BinaryTreeNode node)
    {
        ArrayList<BinaryTreeNode> beTraveled = new ArrayList<BinaryTreeNode>();
        ArrayList<BinaryTreeNode> colored = new ArrayList<BinaryTreeNode>();
        ArrayList<Polynomial> operand = new ArrayList<Polynomial>();
                    
        BinaryTreeNode traveler = node;
        
        do
        {            
            if(traveler.leftChild == null && traveler.rightChild == null)
            {
                colored.add(traveler);
                
                if(MathStaticMethod.isOperand(traveler.value))
                {
                    Fractor f = new Fractor(Integer.parseInt(traveler.value));
                    if(!f.isValid())
                    {
                        isValid = false;
                        return;
                    }
                    else
                    {
                        Monomial mono = new Monomial(f, 0);
                        operand.add(new Polynomial(mono));
                    }                    
                }
                else if(MathStaticMethod.isVariable(traveler.value))
                {
                    operand.add(new Polynomial(new Monomial(new Fractor(1), 1)));
                }
                
                traveler = beTraveled.get(beTraveled.size() - 1);
            }
            else if(colored.contains(traveler.leftChild) && colored.contains(traveler.rightChild))
            {
                colored.add(traveler);                           
                                
                if(MathStaticMethod.isOperator(traveler.value))
                {
                    Polynomial poly1 = operand.get(operand.size()-2);
                    Polynomial poly2 = operand.get(operand.size()-1);
                    Polynomial poly = new Polynomial();
                    
                    if(traveler.value.equals("+"))
                    {
                        poly = (new Plus()).plus(poly1, poly2);
                    }
                    else if(traveler.value.equals("-"))
                    {
                        poly = (new Minus()).minus(poly1, poly2);
                    }
                    else if(traveler.value.equals("*"))
                    {
                        poly = (new Multiply()).multiply(poly1, poly2);
                    }
                    else if(traveler.value.equals("/"))
                    {
                        if(poly2.getPolynomial().size() > 1 || poly2.getPolynomial().get(0).getCoefficient().getNumerator() == 0)
                            {
                                isValid = false;
                                return;
                            }
                            else
                            {
                                poly = (new Divide()).divide(poly1, poly2);
                                
                                if(poly == null)
                                {
                                    isValid = false;
                                    return;
                                }
                            }
                    }                    
                    
                    if(poly == null)
                    {
                        isValid = false;
                        return;
                    }
                    
                    operand.remove(operand.size()-2);
                    operand.remove(operand.size()-1);
                    operand.add(poly);
                }
                
                beTraveled.remove(beTraveled.size() - 1);
                
                if(!beTraveled.isEmpty())
                {
                    traveler = beTraveled.get(beTraveled.size() - 1);
                }
            }
            else if(!colored.contains(traveler.leftChild) && !colored.contains(traveler.rightChild))
            {                
                beTraveled.add(traveler);
                traveler = traveler.leftChild;
            }
            else if(colored.contains(traveler.leftChild) && !colored.contains(traveler.rightChild))
            {
                traveler = traveler.rightChild;
            }       
        } while(!beTraveled.isEmpty() && isValid);        
        
        polynomial = (MathStaticMethod.compact(operand.get(0).getPolynomial()));
    }
    
    // Create a derivative from this polynomial
    public void createDerivative()
    {
        if(polynomial.isEmpty())
            return;
        
        if(derivative == null)
        {
            derivative = new ArrayList<Monomial>();
        }
        
        for(int i = 0; i < polynomial.size(); i++)
        {
            Monomial mono = polynomial.get(i);
            Fractor coeff = new Fractor(mono.getCoefficient().multiply(new Fractor(mono.getPower())));
            int pow = 0;
            if(mono.getPower()>0)
            {
                pow = mono.getPower()-1;
            }
            
            derivative.add(new Monomial(coeff, pow));
            derivative = MathStaticMethod.compact(derivative);
        }
    }   
    
    // Functions for classifying equation
    public void classify()
    {
        checkQuartic();
        checkCubic();
        checkQuadratic();
        checkLinear();
    }    
    
    private void checkQuartic()
    { 
        if(polynomial.size() > 3)
        {
            isQuartic = false;
            return;
        }
        
        if(polynomial.get(polynomial.size()-1).getPower() != 4)
        {
            isQuartic = false;
            return;
        }
        
        if(polynomial.size() > 1)
        {
            for(int i = 1; i < polynomial.size(); i++)
            {
                if(polynomial.get(i).getPower() > 4 || polynomial.get(i).getPower() == 3 || polynomial.get(i).getPower() == 1)
                {
                    isQuartic = false;
                    return;
                }
            }
        }
        
        isQuartic = true;
    }
    
    public boolean isQuartic()
    {
        return isQuartic;
    }
    
    //====
    
    private void checkCubic()
    {
        if(polynomial.size() > 4)
        {
            isCubic = false;
            return;
        }
        
        if(polynomial.get(polynomial.size()-1).getPower() != 3)
        {
            isCubic = false;
            return;
        }
        
        if(polynomial.size() > 1)
        {
            for(int i = 1; i < polynomial.size(); i++)
            {
                if(polynomial.get(i).getPower() > 3)
                {
                    isCubic = false;
                    return;
                }
            }
        }
        
        isCubic = true;
    }
    
    public boolean isCubic()
    {
        return isCubic;
    }
    
    //===
    
    private void checkQuadratic()
    {
        if(polynomial.size() > 3)
        {
            isQuadratic = false;
            return;
        }
        
        if(polynomial.get(polynomial.size()-1).getPower() != 2)
        {
            isQuadratic = false;
            return;
        }
        
        if(polynomial.size() > 1)
        {
            for(int i = 1; i < polynomial.size(); i++)
            {
                if(polynomial.get(i).getPower() > 2)
                {
                    isQuadratic = false;
                    return;
                }
            }
        }
        
        isQuadratic = true;
    }
    
    public boolean isQuadratic()
    {
        return isQuadratic;
    }
    
    //===
    
    private void checkLinear()
    {
        if(polynomial.size() > 2)
        {
            isLinear = false;
            return;
        }
        
        if(polynomial.get(polynomial.size()-1).getPower() != 1)
        {
            isLinear = false;
            return;
        }
        
        if(polynomial.size() > 1)
        {
            for(int i = 1; i < polynomial.size(); i++)
            {
                if(polynomial.get(i).getPower() > 3)
                {
                    isLinear = false;
                    return;
                }
            }
        }
        
        isLinear = true;
    }
    
    public boolean isLinear()
    {
        return isLinear;
    }
    
    @Override
    public String toString()
    {
        StringBuffer s = new StringBuffer(polynomial.get(polynomial.size() - 1).toString());
        
        if(polynomial.size() > 1)
        {
            for(int i = polynomial.size() - 2; i > -1; i--)
            {
                if(polynomial.get(i).getCoefficient().isPositive())
                {
                    s.append("+").append(polynomial.get(i).toString());
                }
                else
                {
                    s.append(polynomial.get(i).toString());
                }
            }
        }
        
        return s.toString();
    }
    
    public String derivativeString()
    {
        StringBuffer s = new StringBuffer(derivative.get(derivative.size() - 1).toString());
        
        if(derivative.size() > 1)
        {
            for(int i = derivative.size() - 2; i > -1; i--)
            {
                if(derivative.get(i).getCoefficient().isPositive())
                {
                    s.append("+").append(derivative.get(i).toString());
                }
                else
                {
                    s.append(derivative.get(i).toString());
                }
            }
        }
        
        return s.toString();
    }
}
