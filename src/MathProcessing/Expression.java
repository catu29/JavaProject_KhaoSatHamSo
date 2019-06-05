/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MathProcessing;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TranCamTu
 */
public class Expression {
    private String expression;
    private boolean isTrigonometric = false;
    
    //=== region Constructors
    public Expression()
    {
        expression = new String();
    }
    
    public Expression(String poly)
    {
        expression = new String(poly);
    }
    //end of Constructors region
    
    public String getExpression()
    {
        return expression;
    }
    
    public boolean isTrigonometric()
    {
        if(expression.indexOf("sin") != -1)
            return true;
                    
        if(expression.indexOf("cos") != -1)
            return true;
        
        if(expression.indexOf("tan") != -1)
            return true;
        
        if(expression.indexOf("cot") != -1)
            return true;
        
        if(expression.indexOf("tg") != -1)
            return true;
        
        if(expression.indexOf("cotg") != -1)
            return true;
            
        return false;
    }
    
    public boolean isValid()
    {
        expression = expression.trim();
        expression = expression.replaceAll("\\s+", "");
        expression = expression.trim();
        expression = expression.toLowerCase();
        
        int countIn = 0;
        int countOut = 0;
        int countOp = 0;
        
        if(expression.isEmpty())
            return false;
        
        if(expression.length() == 1 && !MathStaticMethod.isOperand(Character.toString(expression.charAt(0)))
          && !MathStaticMethod.isVariable(Character.toString(expression.charAt(0))))           
            return false;
        
        if(expression.charAt(0) == '*' || expression.charAt(0) == '/' || expression.charAt(0) == ')')
            return false;
        
        if(expression.charAt(expression.length()-1) != ')' 
                && !MathStaticMethod.isVariable(Character.toString(expression.charAt(expression.length()-1)))
                && !MathStaticMethod.isOperand(Character.toString(expression.charAt(expression.length()-1))))
            return false;
                
        for(int i = 0; i < expression.length(); i++)
        {
            if(expression.charAt(i) == '(')
                countIn++;
            
            if(expression.charAt(i) == ')')
                countOut++;
            
            if(!MathStaticMethod.isOperand(Character.toString(expression.charAt(i))) 
                && !MathStaticMethod.isVariable(Character.toString(expression.charAt(i))))
                countOp++;
            
            if(!MathStaticMethod.isOperator(Character.toString(expression.charAt(i))) 
                    && !MathStaticMethod.isOperand(Character.toString(expression.charAt(i))) 
                    && !MathStaticMethod.isVariable(Character.toString(expression.charAt(i)))
                    && !Character.toString(expression.charAt(i)).equals("(")
                    && !Character.toString(expression.charAt(i)).equals(")"))
                return false;
            
            if(expression.charAt(i) == '^' 
                    && (!MathStaticMethod.isOperand(Character.toString(expression.charAt(i+1)))))
                return false;
            
            if((expression.charAt(i) == '*' || expression.charAt(i) == '/' || expression.charAt(i) == '(') 
                    && (expression.charAt(i+1) == '*' || expression.charAt(i+1) == '/'))
                return false;
            
            if((expression.charAt(i) == '+' || expression.charAt(i) == '-') 
                    && (expression.charAt(i+1) == '*' || expression.charAt(i+1) == '/'))
                return false;
            
            if(expression.charAt(i) == '/')
            {
                if(expression.charAt(i+1) == 'x')
                    return false;
                
                if(expression.charAt(i+1) == '0' && !MathStaticMethod.isOperand(Character.toString(expression.charAt(i+2))))
                    return false;
                
                if(expression.charAt(i+1) == '(')
                {                    
                    for(int j = i+2; expression.charAt(j) != ')'; j++)
                    {
                        if(MathStaticMethod.isVariable(Character.toString(expression.charAt(j))))
                            return false;
                    }
                }
            }
        }
        
        if(countIn != countOut)
            return false;
        
        if(countOp == expression.length())
            return false;
        
        return true;
    }
    
    public void standardize()
    {
        StringBuilder str = new StringBuilder(expression);
        
        if(str.charAt(0) == '-' && (str.charAt(1) == 'x' || str.charAt(1) == '('))
        {
            str.insert(1, "1*");
        }

        for(int i = 1; i < str.length(); i++)
        {            
            if((str.charAt(i) == 'x' || str.charAt(i) == '(')
                    && ((str.charAt(i-1) == ')' || str.charAt(i-1) == 'x'|| MathStaticMethod.isOperand(Character.toString(expression.charAt(i-1))))))
            {
                str.insert(i, '*');
            }
           
            if(str.charAt(i-1) == 'x' || str.charAt(i-1) == ')')
            {
                if(MathStaticMethod.isOperand(Character.toString(expression.charAt(i))))
                    str.insert(i, '*');
            }
            if(str.charAt(i) == '^')
            {
                String in = "";
                for(int j = i+1; j < str.length(); j++)
                {
                    if(MathStaticMethod.isOperand(Character.toString(str.charAt(j))))
                    {
                        in += Character.toString(str.charAt(j));
                        str.deleteCharAt(j);
                        j--;
                    }
                    else
                    {
                        break;
                    }
                }
                
                str.deleteCharAt(i);
                int inte = Integer.parseInt(in);
                
                while(inte != 1)
                {
                    str.insert(i, "*x");
                    inte--;
                }
            }
            
            if(str.charAt(i) == '0' && MathStaticMethod.isOperator(Character.toString(str.charAt(i-1))))
            {
                if(MathStaticMethod.isOperand(Character.toString(str.charAt(i+1))))
                {
                    str.deleteCharAt(i);
                }
            }
            
            if(str.charAt(i) == '-' && str.charAt(i-1) == '+')
                str.deleteCharAt(i-1);
            
            if(str.charAt(i) == '+' && str.charAt(i-1) == '-')
                str.deleteCharAt(i);
            
            if(str.charAt(i) == '-' && str.charAt(i-1) == '-')
            {
                str.replace(i-1, i+1, "+");                
            }
            
            expression = str.toString();
        }
    }
    
    //RPN: Reverse Polish Notation
    public ArrayList<String> createRPN()
    {
        ArrayList<String> rpn = new ArrayList<String>();
        ArrayList<String> stack = new ArrayList<String>();
        
        String integer = "";
                
        for(int i = 0; i < expression.length(); i++)
        {
            String c = Character.toString(expression.charAt(i));

            if(c.equals("("))
            {
                stack.add(c);
            }
            else if(MathStaticMethod.isVariable(c))
            {
                rpn.add(c);
            }
            else if(MathStaticMethod.isOperand(c))
            {
                integer += c;

                if(i != (expression.length()-1))
                {
                    if(!MathStaticMethod.isOperand(Character.toString(expression.charAt(i+1))))
                    {
                        rpn.add(integer);
                        integer = "";                   
                    }
                }
                else
                {
                    rpn.add(integer);
                }
            }
            else if(c.equals(")"))
            {
                while(!stack.isEmpty())
                {
                    String ch = stack.get(stack.size()-1);

                    if(ch.equals("("))
                    {
                        stack.remove(stack.size()-1);
                        break;
                    }
                    else
                    {                        
                        rpn.add(ch);
                        stack.remove(stack.size()-1);
                    }                   
                }
            }
            else if(MathStaticMethod.isOperator(c))
            {
                if(c.equals("-") && (i == 0 || expression.charAt(i-1) == '('))
                {
                    for(int j = i+1; MathStaticMethod.isOperand(Character.toString(expression.charAt(j))) == true; j++)
                    {
                        i = j;
                        integer += Character.toString(expression.charAt(j));
                    }
                    
                    rpn.add("-" + integer);
                    integer = "";
                }
                else
                {
                    while(!stack.isEmpty())
                    {
                        String ch = stack.get(stack.size()-1);
                        if(MathStaticMethod.isOperator(ch))
                        {
                            if(isBiggerOperator(ch, c))
                            {
                                rpn.add(ch);
                                stack.remove(stack.size()-1);
                            }
                            else
                            {
                                break;
                            }
                        }
                        else
                        {
                            break;
                        }
                    }

                    stack.add(c);
                }
            }
        }
        
        while(!stack.isEmpty())
        {
            rpn.add(stack.get(stack.size()-1));
            stack.remove(stack.size()-1);
        }
        
        return rpn;
    }
    
    public BinaryTreeNode createExpressionTree()
    {
        ArrayList<String> postfix = createRPN();
        ArrayList<BinaryTreeNode> stack = new ArrayList<BinaryTreeNode>();
        BinaryTreeNode root;      
        
        for(int i = 0; i < postfix.size(); i++)
        {
            root = new BinaryTreeNode(postfix.get(i));
                
            if(MathStaticMethod.isOperator(postfix.get(i)))
            {
                root.rightChild = stack.get(stack.size()-1);
                stack.remove(stack.size()-1);
                
                root.leftChild = stack.get(stack.size()-1);
                stack.remove(stack.size()-1);
            }
                
            stack.add(root);
        }
        
        root = stack.get(0);
        return root;
    }
    
    private boolean isBiggerOperator(String opNeedCompare, String opForCompare)
    {
        if(!MathStaticMethod.isOperator(opNeedCompare) || !MathStaticMethod.isOperator(opForCompare))
            return false;
        
        if((opNeedCompare.equals("+") || opNeedCompare.equals("-"))
                && (opForCompare.equals("*") || opForCompare.equals("/")))
            return false;
        
        return true;
    }    
}
