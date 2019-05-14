/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
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
    public void start(Stage primaryStage){
        primaryStage.setTitle("Khảo sát đồ thị hàm số");
        primaryStage.setIconified(false);
        
        TabPane tabPane = new TabPane();
        
        PolynomialTab polynomial = new PolynomialTab();
        polynomial.setText("Đa thức");
        polynomial.setClosable(false);
        polynomial.arrangeControls();
        
        Tab subdivision = new Tab();
        subdivision.setText("Phân thức");
        subdivision.setClosable(false);
        
        tabPane.getTabs().add(polynomial);
        tabPane.getTabs().add(subdivision);
        
        Group root = new Group();
        root.getChildren().add(tabPane);
        
        Scene scene = new Scene(root, 800, 500);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
