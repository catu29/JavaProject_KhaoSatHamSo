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
public class MathStaticMethod {
    //Check if and element in expression string is an operator
    public static boolean isOperator(String c)
    {
        if(!c.equals("+") && !c.equals("-") && !c.equals("*") && !c.equals("/") && !c.equals("^"))
            return false;
        
        return true;
    }
    
    //Check if and element in expression string is an operand
    public static boolean isOperand(String operand) 
    {          
        try
        {
            int check = 0;
            check = Integer.parseInt(operand);
            return true; 
        }
        catch(Exception e)
        {
            return false;
        }            
    }
    
    //Check if and element in expression string is a variable
    public static boolean isVariable(String c)
    {
        if(c.equals("x"))
            return true;
        
        return false;
    }  
    
    //Find GCD - Greatest Common Division of 'a' and 'b'
    public static int findGCD(int a, int b)
    {
        if (a == 0 || b == 0)
        {
            return a + b;
        }
        
        while (a != b)
        {
            if (a > b)
            {
                a -= b;
            }
            else
            {
                b -= a;
            }
        }
        return a;
    }
    
    public static ArrayList<Monomial> compact(ArrayList<Monomial> operands)
    {
        if(operands.size() == 1)
            return operands;
        
        int minPower = 0;
        int maxPower = 0;
        
        //Finding min and max power of list operands
        for(int findPow = 0; findPow < operands.size(); findPow++)
        {
            if(operands.get(findPow).getPower() < minPower)
                minPower = operands.get(findPow).getPower();
            
            if(operands.get(findPow).getPower() > maxPower)
                maxPower = operands.get(findPow).getPower();
        }
        
        //Create a compact list to store sum of monomial having each power
        ArrayList<Monomial> compactList = new ArrayList<Monomial>();
        for(int pow = minPower; pow <= maxPower; pow++)
        {            
            compactList.add(new Monomial(new Fractor(), pow));
        }
        
        int diff = minPower; //real index of the monomial having particular power in compact list
        for(int getPow = 0; getPow < operands.size(); getPow++)
        {
            Monomial monoAdding = operands.get(getPow);
            Monomial monoCompact = compactList.get(monoAdding.getPower() - diff);
            
            if(monoAdding.getCoefficient().getNumerator() != 0)
            {                
                monoCompact.setCoefficient(monoCompact.getCoefficient().add(monoAdding.getCoefficient()));
                compactList.remove(monoAdding.getPower() - diff);
                compactList.add(monoAdding.getPower() - diff, monoCompact);}
        }
        
        
        for(int i = 0; i < compactList.size(); i++)
        {
            if(compactList.get(i).getCoefficient().getNumerator() == 0)
            {
                compactList.remove(i);
                i--;
            }
        }
        
        return compactList;
    }
    
    //Tree-travel: Left Right Node type
    public static void LRN(BinaryTreeNode node)
    {
        if(node != null)
        {
            LRN(node.leftChild);
            LRN(node.rightChild);
            
            System.out.print(node.value);
        }         
    }
}
