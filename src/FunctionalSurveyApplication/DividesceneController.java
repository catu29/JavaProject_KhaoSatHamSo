/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import MathProcessing.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    @FXML
    private Button btnKhaoSat;
    @FXML
    private Pane pane;
    @FXML
    private Pane paneVariant;
    
    private PolyOnPoly pop;
    private double minX = -5.5;
    private double maxX = 5.5;
    private double minY = -4.5;
    private double maxY = 4.5;
    
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
                }
                
                if(event.getCode() == KeyCode.ENTER)
                {
                    process();
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
                if(event.getCode() == KeyCode.TAB)
                {
                    if(txtFieldNum.getText().isEmpty())
                    {
                        txtFieldNum.requestFocus();
                    }
                }
                
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
    
    public void process()
    {
        if(txtFieldNum.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Chưa nhập tử số", ButtonType.OK);
            alert.show();
        }
        else if(txtFieldDen.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Chưa nhập mẫu số", ButtonType.OK);
            alert.show();
        }   
        else
        {
            Expression numExp = new Expression(txtFieldNum.getText());
            Expression denExp = new Expression(txtFieldDen.getText());
            if(!numExp.isValid())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Biểu thức tử số không hợp lệ", ButtonType.OK);
                alert.show();
            }
            else if(!denExp.isValid())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Biểu thức mẫu số không hợp lệ", ButtonType.OK);
                alert.show();
            }
            else
            {
                numExp.standardize();
                denExp.standardize();
                
                Polynomial numPoly = new Polynomial();
                numPoly.create(numExp.createExpressionTree());
                
                Polynomial denPoly = new Polynomial();
                denPoly.create(denExp.createExpressionTree());
                
                txtFieldNum.setText(numPoly.toString());
                txtFieldDen.setText(denPoly.toString());
                
                pop = new PolyOnPoly(numPoly, denPoly);
                
                if(pop.isLinearOnLinear())
                {
                    pop = new LinearOnLinear(numPoly, denPoly);
                    
                    paintVariantTable();                    
                    paintGraph();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ứng dụng hiện tại chỉ hỗ trợ khảo sát hàm bậc nhất trên bậc nhất.", ButtonType.OK);
                    alert.show();
                }
            }
        }
    }
    
    private void paintGraph()
    {
        //Ox, OY => layout Y = 250, layout X = 310
        //y = 4.5 => layout Y = 25
        //y = -4.5 => layout Y = 475
        //x = -5.5 => layout X = 35
        //x = 5.5 => layout X = 585
        //1 đơn vị = 50px => 2 vạch = 25px
        // x += a => layoutX += 50a
        // y += a => layoutY -= 50a  
                
        pane.getChildren().clear();
        
        double layoutX = 35;        
        double layoutY = 25;
        
        if(!((LinearOnLinear)pop).isSimplified())
        {
            Fractor asymStoreX = ((LinearOnLinear)pop).getAsymstoreX();
            double ax = asymStoreX.getNumerator()*1.0/asymStoreX.getDenominator();

            Fractor asymStoreY = ((LinearOnLinear)pop).getAsymstoreY();
            double ay = asymStoreY.getNumerator()*1.0/asymStoreY.getDenominator();

            Line asymX = new Line(0, 0, 0, 490);
            asymX.setSmooth(true);
            asymX.setLayoutX(layoutX + (ax-(minX))*50);
            asymX.setLayoutY(0);
            if(asymX.getLayoutX() > 585 || asymX.getLayoutX() < 35)
            {
                asymX.setVisible(false);
            }
            else
            {
                asymX.setVisible(true);
            }
            pane.getChildren().add(asymX);

            Line asymY = new Line(0, 0, 610, 0);
            asymY.setLayoutY(layoutY - (ay-(maxY))*50);
            asymY.setLayoutX(0);
            asymY.setSmooth(true);
            if(asymY.getLayoutY() < 25 || asymY.getLayoutY() > 475)
            {
                asymY.setVisible(false);
            }
            else
            {
                asymY.setVisible(true);
            }
            pane.getChildren().add(asymY);

            double _y = ((LinearOnLinear)pop).calculate(minX);

            for(double x = minX; x < maxX; x += 0.01)
            {
                double y;
                
                if((x < ax && (x+0.01) >= ax) || x == ax)
                {                    
                    continue;
                }
                else
                {
                    y = ((LinearOnLinear)pop).calculate(x);
                }

                if(y > maxY || y < minY)
                {
                    _y = y;
                    continue;
                }
                else if(_y < minY && y > minY)
                {
                    _y = minY;
                }
                else if(y < maxY && _y > maxY)
                {
                    _y = maxY;
                }
                                
                if(pane.getChildren().size() > 2)
                {
                    Line _line = (Line)pane.getChildren().get(pane.getChildren().size() - 1);
                
                    _line.setEndX(0.5);

                    if(y > _y)
                    {
                        _line.setEndY(-Math.abs(y - _y)*50);
                    }  
                    else
                    {
                        _line.setEndY(Math.abs(y - _y)*50);
                    }
                }

                Line line = new Line();                
                line.setStroke(Color.BLUE);
                line.setSmooth(true);
                line.setLayoutX(layoutX + (x-(minX))*50);
                line.setLayoutY(layoutY - (y-(maxY))*50);

                pane.getChildren().add(line);
                _y = y;
            }
        }
        else
        {
            Fractor asymStoreY = ((LinearOnLinear)pop).getAsymstoreY();
            double ay = asymStoreY.getNumerator()*1.0/asymStoreY.getDenominator();
            
            Line asymY = new Line(0, 0, 610, 0);
            asymY.setLayoutY(layoutY - (ay-(maxY))*50);
            asymY.setLayoutX(0);
            asymY.setSmooth(true);
            if(asymY.getLayoutY() < 25 || asymY.getLayoutY() > 475)
            {
                asymY.setVisible(false);
            }
            else
            {
                asymY.setVisible(true);
            }
            pane.getChildren().add(asymY);
        }
    }
    
    private void paintVariantTable()
    {
        /*
        x: (135, 20) (190, 20)
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        
        paneVariant.getChildren().clear();
        
    }
}
