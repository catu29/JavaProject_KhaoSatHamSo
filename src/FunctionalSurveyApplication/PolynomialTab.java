/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author TranCamTu
 */
public class PolynomialTab extends Tab {
    
    void arrangeControls() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        parent.getStylesheets().add(this.getClass().getResource("mainscenestyle.css").toExternalForm());
        this.setContent(parent);        
    }
}
