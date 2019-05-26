
package FunctionalSurveyApplication;

import MathProcessing.*;
import java.awt.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author T-PC
 */
public class mainscenecontroller{
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
    private Button btnKhaoSat;
    
    @FXML
    protected void TextFieldOnKeyPress()
    {
        txtFieldFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) 
            {
                if(event.getCode() == KeyCode.ENTER)
                {                    
                    process();
                }
            }
        });          
    }
        
    @FXML
    protected void btnOnAction()
    {
        btnKhaoSat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    process();
                }
            }
        });
    }
    
    private void process()
    {
        VBox vb = new VBox();
                
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
            }
            else if(poly.isQuadratic())
            {
                ce = new QuadraticEquation(poly);
                txtTXD_result.setText(ce.getSet());
                txtDaoHam_result.setText("y' = " + poly.derivativeString());
                txtGioiHan_result.setText(ce.limitation());
                txtBangBienThien_result.setText(ce.variant());
                txtNhanXet_result.setText(ce.comment());
            }
            else if(poly.isCubic())
            {
                ce = new CubicEquation(poly);
                txtTXD_result.setText(ce.getSet());
                txtDaoHam_result.setText("y' = " + poly.derivativeString());
                txtGioiHan_result.setText(ce.limitation());
                txtBangBienThien_result.setText(ce.variant());
                txtNhanXet_result.setText(ce.comment());
            }
            else if(poly.isQuartic())
            {
                ce = new QuarticEquation(poly);
                txtTXD_result.setText(ce.getSet());
                txtDaoHam_result.setText("y' = " + poly.derivativeString());
                txtGioiHan_result.setText(ce.limitation());
                txtBangBienThien_result.setText(ce.variant());
                txtNhanXet_result.setText(ce.comment());
            }

            txtFieldFX.setText("");                        
        } 
    }
}
