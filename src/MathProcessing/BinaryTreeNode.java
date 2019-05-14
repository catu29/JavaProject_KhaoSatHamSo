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
public class BinaryTreeNode {
    public BinaryTreeNode leftChild;
    public BinaryTreeNode rightChild;
    public String value;
    
    public BinaryTreeNode(String val)
    {
        value = val;
    }
    
    public boolean isLeaf()
    {
        if(leftChild == null && rightChild == null)
            return true;
        
        return false;
    }
}
