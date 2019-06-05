/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import com.sun.deploy.uitoolkit.Window;
import com.sun.prism.paint.Color;
import java.awt.Desktop;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author TranCamTu
 */
public class MainStage extends Application {
    public static void main(String args[])
    {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        
        primaryStage.setTitle("Khảo sát đồ thị hàm số");
        primaryStage.setIconified(false);
                
        TabPane tabPane = new TabPane();
        tabPane.autosize();   
        tabPane.setSide(Side.RIGHT);
        
                
        PolynomialTab polynomial = new PolynomialTab();
        polynomial.setText("Đa thức");
        polynomial.setClosable(false);
        polynomial.arrangeControls();
        
        DivideTab division = new DivideTab();
        division.setText("Phân thức");
        division.setClosable(false);
        division.arrangeControls();
        
        tabPane.getTabs().add(polynomial);
        tabPane.getTabs().add(division);
        
        Group root = new Group();
        root.getChildren().add(tabPane);
        root.getStylesheets().add(this.getClass().getResource("tabpanestyle.css").toExternalForm());        
        
        Scene scene = new Scene(root);
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
