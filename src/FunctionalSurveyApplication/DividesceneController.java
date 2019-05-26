/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import MathProcessing.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author TranCamTu
 */
public class DividesceneController{
    @FXML
    private TextField txtFieldNum;
    @FXML
    private TextField txtFieldDen;
//    
//    @FXML
//    private Text txtDaoHam_result_num;
//    @FXML
//    private Text txtDaoHam_result;
//    @FXML
//    private Text txtTXD_result;
    
    @FXML
    public void numOnKeyPress(KeyEvent event)
    {
//        txtDaoHam_result_num.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\neee\neweee\n");
//        txtDaoHam_result.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\neee\neweee\n");
//        txtTXD_result.setText("1\n2\n3\n4\n5\n6\n7\n8\n9\neee\neweee\n");
        txtFieldNum.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.TAB)
                {
                    if(txtFieldDen.getText().isEmpty())
                    {
                        txtFieldDen.requestFocus();
                    }
                    else
                    {
                        txtFieldNum.setText("");
                        txtFieldDen.setText("");
                    }
                }
            }
            
        });
    }

    @FXML
    public void denOnKeyPress(KeyEvent event)
    {
        txtFieldDen.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER)
                {
                    if(txtFieldNum.getText().isEmpty())
                    {
                        txtFieldNum.requestFocus();
                    }
                    else
                    {
                        txtFieldNum.setText("");
                        txtFieldDen.setText("");
                    }
                }
            }
            
        });
    }    
}
