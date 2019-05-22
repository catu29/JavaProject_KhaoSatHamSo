/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

/**
 *
 * @author TranCamTu
 */
public class DivideTab extends Tab{
    public void arrangeControls() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dividescene.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        parent.getStylesheets().add(this.getClass().getResource("mainscenestyle.css").toExternalForm());
        this.setContent(parent);
    }
}
