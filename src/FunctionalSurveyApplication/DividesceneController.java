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
    
    @FXML
    public void numOnKeyPress(KeyEvent event)
    {
        txtFieldNum.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER)
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
