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
public class Minus {
    public ArrayList<Monomial> minus(Monomial mono1, Monomial mono2)
    {        
        if(!mono1.isValid() || !mono2.isValid())
            return null;
        
        ArrayList<Monomial> result = new ArrayList<Monomial>();
        if(mono1.getPower() == mono2.getPower())
        {
            Monomial res = new Monomial(mono1.getCoefficient().subtract(mono2.getCoefficient()), mono1.getPower());
            result.add(res);
        }
        else
        {
            result.add(mono1);
            result.add(mono2);
        }
        
        return MathStaticMethod.compact(result);
    }
    
    public ArrayList<Monomial> minus(ArrayList<Monomial> listMono1, ArrayList<Monomial> listMono2)
    {
        if(listMono1.isEmpty() && listMono2.isEmpty())
            return null;
        
        if(listMono1.isEmpty() && !listMono2.isEmpty())
        { 
            return MathStaticMethod.compact(listMono2);
        }
            
        
        if(!listMono1.isEmpty() && listMono2.isEmpty())
        {
            return MathStaticMethod.compact(listMono1);
        }
        
        for(int i = 0; i < listMono2.size(); i++)
        {
            listMono2.get(i).translate();
        }

        listMono1.addAll(listMono2);
        return MathStaticMethod.compact(listMono1);
    }
    
    public Polynomial minus(Polynomial poly1, Polynomial poly2)
    {
        ArrayList<Monomial> listMono1 = poly1.getPolynomial();
        ArrayList<Monomial> listMono2 = poly2.getPolynomial();
        
        return new Polynomial(minus(listMono1, listMono2));
    }
}
