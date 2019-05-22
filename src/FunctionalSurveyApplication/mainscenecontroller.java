
package FunctionalSurveyApplication;

import MathProcessing.*;
import java.awt.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author T-PC
 */
public class mainscenecontroller{
    @FXML
    private AnchorPane paneMain;
    @FXML
    private Text txtHamSo;
    @FXML
    private TextField txtFieldFX;
    @FXML
    private Text txtTXD_result; 
    @FXML
    private Text txtDaoHam_result; 
    @FXML
    private Text txtGioiHan_result;
    @FXML
    private Text txtBangBienThien_result;
    @FXML
    private Text txtNhanXet_result; 
    
    @FXML
    protected void TextFieldOnKeyPress(KeyEvent event)
    {
        txtFieldFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) 
            {
                VBox vb = new VBox();
                
                if(event.getCode() == KeyCode.ENTER)
                {
                    Expression exp = new Expression(txtFieldFX.getText());
                    
                    if(!exp.isValid())
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Biểu thức không hợp lệ", ButtonType.OK);
                        alert.show();
                    }
                    else
                    {
                        exp.standardize();
                        
                        Polynomial poly = new Polynomial();
                        poly.create(exp.createExpressionTree());
                        txtHamSo.setText("Hàm số y = " + poly.toString());
                        txtHamSo.setTextAlignment(TextAlignment.CENTER);
                        poly.createDerivative();
                        poly.classify();
                        
                        CommonEquation ce;
                        
                        if(poly.isLinear())
                        {
                            ce = new LinearEquation(poly);
                            txtTXD_result.setText(ce.getSet());
                            txtDaoHam_result.setText("y' = " + poly.derivativeString());
                            txtGioiHan_result.setText(ce.limitation());
                            txtBangBienThien_result.setText(ce.variant());
                            txtNhanXet_result.setText(ce.comment());
                                    
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Linear", ButtonType.OK);
//                            alert.show();
                        }
                        else if(poly.isQuadratic())
                        {
                            ce = new QuadraticEquation(poly);
                            txtTXD_result.setText(ce.getSet());
                            txtDaoHam_result.setText("y' = " + poly.derivativeString());
                            txtGioiHan_result.setText(ce.limitation());
                            txtBangBienThien_result.setText(ce.variant());
                            txtNhanXet_result.setText(ce.comment());
                            
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Quadratic", ButtonType.OK);
//                            alert.show();
                        }
                        else if(poly.isCubic())
                        {
                            ce = new CubicEquation(poly);
                            txtTXD_result.setText(ce.getSet());
                            txtDaoHam_result.setText("y' = " + poly.derivativeString());
                            txtGioiHan_result.setText(ce.limitation());
                            txtBangBienThien_result.setText(ce.variant());
                            txtNhanXet_result.setText(ce.comment());
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cubic", ButtonType.OK);
//                            alert.show();
                        }
                        else if(poly.isQuartic())
                        {
                            ce = new QuarticEquation(poly);
                            txtTXD_result.setText(ce.getSet());
                            txtDaoHam_result.setText("y' = " + poly.derivativeString());
                            txtGioiHan_result.setText(ce.limitation());
                            txtBangBienThien_result.setText(ce.variant());
                            txtNhanXet_result.setText(ce.comment());
//                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cubic", ButtonType.OK);
//                            alert.show();
                        }
                        
                        txtFieldFX.setText("");
                        
                    }
                }
            }
        });        
    }
}
