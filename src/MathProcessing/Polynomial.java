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
    private boolean isValid = true;
    
    public ArrayList<Monomial> getPolynomial()
    {
        return polynomial;
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
    
    //Using DFS algorithm
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
}
