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
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
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
    private Text txtTXD_result;
    @FXML
    private Text txtDaoHam_tu;
    @FXML
    private Text txtDaoHam_mau;
    @FXML
    private Text txtDaoHam_result;
    @FXML
    private Text txtBangBienThien_result;
    @FXML
    private Text txtNhanXet_result;
    @FXML
    private Button btnKhaoSat;
    @FXML
    private Line Ox;
    @FXML
    private Line Oy;
    @FXML
    private Text O;
    @FXML
    private ToggleButton iconArrowLeft;
    @FXML
    private ToggleButton iconArrowRight;
    @FXML
    private ToggleButton iconArrowUp;
    @FXML
    private ToggleButton iconArrowDown;
    @FXML
    private Pane pane;
    @FXML
    private Pane paneOx;
    @FXML
    private Pane paneOy;
    @FXML
    private Pane paneOxValues;
    @FXML
    private Pane paneOyValues;
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
    public void btnOnAction()
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
    
    //Axis
    // x values: layoutX - 5; layoutY + 20
    // y values: layoutX - 10; layoutY + 5
    
    @FXML
    public void iconArrowLeftOnAction()
    {
        iconArrowLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    moveLeft();
                    
                    paintGraph();
                }
            }
        });
    }
    
    @FXML
    public void iconArrowRightOnAction()
    {
        iconArrowRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    moveRight();
                    
                    paintGraph();
                }
            }
        });
    }
    
    @FXML
    public void iconArrowUpOnAction()
    {
        iconArrowUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    moveUp();
                    
                    paintGraph();
                }
            }
        });
    }
    
    @FXML
    public void iconArrowDownOnAction()
    {
        iconArrowDown.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.PRIMARY)
                {
                    moveDown();
                    
                    paintGraph();
                }
            }
        });
    }
    
    public void process()
    {
        reset();
        
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
                    iconArrowLeft.setDisable(false);
                    iconArrowRight.setDisable(false);
                    iconArrowUp.setDisable(false);
                    iconArrowDown.setDisable(false);

                    pop = new LinearOnLinear(numPoly, denPoly);
                    
                    if(!((LinearOnLinear)pop).isSimplified())
                    {
                        txtTXD_result.setText(((LinearOnLinear)pop).getSet());
                        txtDaoHam_tu.setText(((LinearOnLinear)pop).getDerivative().toString());
                        txtDaoHam_mau.setText("(" + denPoly.toString() + ")^2");
                        txtDaoHam_result.setText(((LinearOnLinear)pop).derivative());
                        txtBangBienThien_result.setText(((LinearOnLinear)pop).variant());
                        txtNhanXet_result.setText(((LinearOnLinear)pop).comment());
                    }
                    
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
    
    private void reset()
    {
        iconArrowLeft.setDisable(true);
        iconArrowRight.setDisable(true);
        iconArrowUp.setDisable(true);
        iconArrowDown.setDisable(true);

        minX = -5.5;
        maxX = 5.5;
        minY = -4.5;
        maxY = 4.5;
        
        O.setLayoutX(292);
        O.setLayoutY(245);
        O.setVisible(true);
        
        pane.getChildren().clear();
        
        if(Oy.getLayoutX() < 310)
        {
            double distanceX = (310 - Oy.getLayoutX());
            
            for(int i = 0; i < paneOy.getChildren().size(); i++)
            {
                paneOy.getChildren().get(i).setLayoutX(paneOy.getChildren().get(i).getLayoutX() + distanceX);
                paneOy.getChildren().get(i).setVisible(true);
            }
            
            for(int i = 0; i < paneOyValues.getChildren().size(); i++)
            {
                paneOyValues.getChildren().get(i).setLayoutX(paneOyValues.getChildren().get(i).getLayoutX() + distanceX);
                double yValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                
                if(yValue == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }
            
            for(int i = 0; i < paneOxValues.getChildren().size(); i++)
            {
                double xValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                xValue -= distanceX/50;
                
                ((Text)paneOxValues.getChildren().get(i)).setText(String.valueOf(xValue));
                
                if(xValue == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }            
        }
        else
        {
            double distanceX = (Oy.getLayoutX() - 310);
            
            for(int i = 0; i < paneOy.getChildren().size(); i++)
            {
                paneOy.getChildren().get(i).setLayoutX(paneOy.getChildren().get(i).getLayoutX() - distanceX);
                paneOy.getChildren().get(i).setVisible(true);
            }
            
            for(int i = 0; i < paneOyValues.getChildren().size(); i++)
            {
                paneOyValues.getChildren().get(i).setLayoutX(paneOyValues.getChildren().get(i).getLayoutX() - distanceX);
                double yValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                
                if(yValue == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }
            
            for(int i = 0; i < paneOxValues.getChildren().size(); i++)
            {
                double xValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                xValue += distanceX/50;
                
                ((Text)paneOxValues.getChildren().get(i)).setText(String.valueOf(xValue));
                
                if(xValue == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }  
        }
        
        if(Ox.getLayoutY() < 250)
        {
            double distanceY = 250 - Ox.getLayoutY();
            
            for(int i = 0; i < paneOx.getChildren().size(); i++)
            {
                paneOx.getChildren().get(i).setLayoutY(paneOx.getChildren().get(i).getLayoutY() + distanceY);
                paneOx.getChildren().get(i).setVisible(true);
            }
            
            for(int i = 0; i < paneOxValues.getChildren().size(); i++)
            {
                paneOxValues.getChildren().get(i).setLayoutY(paneOxValues.getChildren().get(i).getLayoutY() + distanceY);
                
                double xValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                if(xValue == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }
            
            for(int i = 0; i < paneOyValues.getChildren().size(); i++)
            {
                double yValue = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
                yValue += distanceY/50;
                
                ((Text)paneOyValues.getChildren().get(i)).setText(String.valueOf(yValue));
                
                if(yValue == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }
        }
        else
        {
            double distanceY = Ox.getLayoutY() - 250;
            
            for(int i = 0; i < paneOx.getChildren().size(); i++)
            {
                paneOx.getChildren().get(i).setLayoutY(paneOx.getChildren().get(i).getLayoutY() - distanceY);
                paneOx.getChildren().get(i).setVisible(true);
            }
            
            for(int i = 0; i < paneOxValues.getChildren().size(); i++)
            {
                paneOxValues.getChildren().get(i).setLayoutY(paneOxValues.getChildren().get(i).getLayoutY() - distanceY);
                
                double xValue = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                if(xValue == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }
            
            for(int i = 0; i < paneOyValues.getChildren().size(); i++)
            {
                double yValue = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
                yValue -= distanceY/50;
                
                ((Text)paneOyValues.getChildren().get(i)).setText(String.valueOf(yValue));
                
                if(yValue == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }
        }         
    }
    
    private void moveRight()
    {
        minX -= 0.5;
        maxX -= 0.5;

        O.setLayoutX(O.getLayoutX() + 25); // Move 0.5 point
        if(O.getLayoutX() > 585)
        {
            O.setVisible(false);
        }
        else
        {
            O.setVisible(true);
        }

        for(int i = 0; i < paneOy.getChildren().size(); i++)
        {
            double layoutX = paneOy.getChildren().get(i).getLayoutX();
            paneOy.getChildren().get(i).setLayoutX(layoutX + 25);
            
            if(paneOy.getChildren().get(i).getLayoutX() > 585)
            {
                paneOy.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOy.getChildren().get(i).setVisible(true);
            }
        }

        for(int i = 0; i < paneOyValues.getChildren().size(); i++)
        {
            double layoutX = paneOyValues.getChildren().get(i).getLayoutX();
            paneOyValues.getChildren().get(i).setLayoutX(layoutX + 25); // Move 0.5 point
            
            if(paneOyValues.getChildren().get(i).getLayoutX() > 585)
            {
                paneOyValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                double value = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
                
                if(value == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }            
        }

        for(int i = 0; i < paneOxValues.getChildren().size(); i++)
        {
            double value = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
            value -= 0.5;

            ((Text)paneOxValues.getChildren().get(i)).setText(String.valueOf(value));

            if(value == 0)
            {
                paneOxValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOxValues.getChildren().get(i).setVisible(true);
            }    
        }
    }
    
    private void moveLeft()
    {
        minX += 0.5;
        maxX += 0.5;

        O.setLayoutX(O.getLayoutX() - 25); // Move 0.5 point
        if(O.getLayoutX() < 15)
        {
            O.setVisible(false);
        }
        else
        {
            O.setVisible(true);
        }     

        for(int i = 0; i < paneOy.getChildren().size(); i++)
        {
            double layoutX = paneOy.getChildren().get(i).getLayoutX();
            paneOy.getChildren().get(i).setLayoutX(layoutX - 25);
            
            if(paneOy.getChildren().get(i).getLayoutX() < 15)
            {
                paneOy.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOy.getChildren().get(i).setVisible(true);
            }
        }

        for(int i = 0; i < paneOyValues.getChildren().size(); i++)
        {
            double layoutX = paneOyValues.getChildren().get(i).getLayoutX();
            paneOyValues.getChildren().get(i).setLayoutX(layoutX - 25); // Move 0.5 point
            
            if(paneOyValues.getChildren().get(i).getLayoutX() < 15)
            {
                paneOyValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                double value = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
                
                if(value == 0)
                {
                    paneOyValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOyValues.getChildren().get(i).setVisible(true);
                }
            }            
        }

        for(int i = 0; i < paneOxValues.getChildren().size(); i++)
        {
            double value = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
            value += 0.5;

            ((Text)paneOxValues.getChildren().get(i)).setText(String.valueOf(value));

            if(value == 0)
            {
                paneOxValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOxValues.getChildren().get(i).setVisible(true);
            }    
        }
    }
    
    private void moveUp()
    {
        minY -= 0.5;
        maxY -= 0.5;

        O.setLayoutY(O.getLayoutY() - 25); // Move 0.5 point
        if(O.getLayoutY() < 25)
        {
            O.setVisible(false);
        }
        else
        {
            O.setVisible(true);
        }

        for(int i = 0; i < paneOx.getChildren().size(); i++)
        {
            double layoutY = paneOx.getChildren().get(i).getLayoutY();
            paneOx.getChildren().get(i).setLayoutY(layoutY - 25);
            
            if(paneOx.getChildren().get(i).getLayoutY() < 25)
            {
                paneOx.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOx.getChildren().get(i).setVisible(true);
            }
        }

        for(int i = 0; i < paneOxValues.getChildren().size(); i++)
        {
            double layoutY = paneOxValues.getChildren().get(i).getLayoutY();
            paneOxValues.getChildren().get(i).setLayoutY(layoutY - 25); // Move 0.5 point
            
            if(paneOxValues.getChildren().get(i).getLayoutY() < 25)
            {
                paneOxValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                double value = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                
                if(value == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }            
        }

        for(int i = 0; i < paneOyValues.getChildren().size(); i++)
        {
            double value = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
            value -= 0.5;

            ((Text)paneOyValues.getChildren().get(i)).setText(String.valueOf(value));

            if(value == 0)
            {
                paneOyValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOyValues.getChildren().get(i).setVisible(true);
            }    
        }
    }
    
    private void moveDown()
    {
        minY += 0.5;
        maxY += 0.5;

        O.setLayoutY(O.getLayoutY() + 25); // Move 0.5 point
        if(O.getLayoutY() > 475)
        {
            O.setVisible(false);
        }
        else
        {
            O.setVisible(true);
        }

        for(int i = 0; i < paneOx.getChildren().size(); i++)
        {
            double layoutY = paneOx.getChildren().get(i).getLayoutY();
            paneOx.getChildren().get(i).setLayoutY(layoutY + 25);
            
            if(paneOx.getChildren().get(i).getLayoutY() > 457)
            {
                paneOx.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOx.getChildren().get(i).setVisible(true);
            }
        }

        for(int i = 0; i < paneOxValues.getChildren().size(); i++)
        {
            double layoutY = paneOxValues.getChildren().get(i).getLayoutY();
            paneOxValues.getChildren().get(i).setLayoutY(layoutY + 25); // Move 0.5 point
            
            if(paneOxValues.getChildren().get(i).getLayoutY() > 475)
            {
                paneOxValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                double value = Double.parseDouble(((Text)paneOxValues.getChildren().get(i)).getText());
                
                if(value == 0)
                {
                    paneOxValues.getChildren().get(i).setVisible(false);
                }
                else
                {
                    paneOxValues.getChildren().get(i).setVisible(true);
                }
            }            
        }

        for(int i = 0; i < paneOyValues.getChildren().size(); i++)
        {
            double value = Double.parseDouble(((Text)paneOyValues.getChildren().get(i)).getText());
            value += 0.5;

            ((Text)paneOyValues.getChildren().get(i)).setText(String.valueOf(value));

            if(value == 0)
            {
                paneOyValues.getChildren().get(i).setVisible(false);
            }
            else
            {
                paneOyValues.getChildren().get(i).setVisible(true);
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
        
        Text x = new Text(((LinearOnLinear)pop).getAsymstoreX().toString());
        x.setFont(Font.font(18));
        x.setLayoutX(135);
        x.setLayoutY(20);
        paneVariant.getChildren().add(x);
        
        x = new Text(((LinearOnLinear)pop).getAsymstoreX().toString());
        x.setFont(Font.font(18));
        x.setLayoutX(190);
        x.setLayoutY(20);
        paneVariant.getChildren().add(x);
        
        if(((LinearOnLinear)pop).getDerivative().isPositive())
        {
            Text limNegative = new Text("-∞");
            limNegative.setFont(Font.font(18));
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(120);
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setFont(Font.font(18));
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(45);
            paneVariant.getChildren().add(limPositive);
            
            Text asymValue = new Text(((LinearOnLinear)pop).getAsymstoreY().toString());
            asymValue.setFont(Font.font(18));
            asymValue.setLayoutX(135);
            asymValue.setLayoutY(45);
            paneVariant.getChildren().add(asymValue);
            
            asymValue = new Text(((LinearOnLinear)pop).getAsymstoreY().toString());
            asymValue.setFont(Font.font(18));
            asymValue.setLayoutX(190);
            asymValue.setLayoutY(120);
            paneVariant.getChildren().add(asymValue);
            
            Line line = new Line(0, 0, 100, -50);
            line.setLayoutX(30);
            line.setLayoutY(105);
            paneVariant.getChildren().add(line);
            
            Line upArrow = new Line(0, 0, -5, -2);
            upArrow.setLayoutX(130);
            upArrow.setLayoutY(55);
            paneVariant.getChildren().add(upArrow);
            
            Line downArrow = new Line(0, 0, -2, 5);
            downArrow.setLayoutX(130);
            downArrow.setLayoutY(55);
            paneVariant.getChildren().add(downArrow);
            
            line = new Line(0, 0, 100, -50);
            line.setLayoutX(215);
            line.setLayoutY(105);
            paneVariant.getChildren().add(line);
            
            upArrow = new Line(0, 0, -5, -2);
            upArrow.setLayoutX(315);
            upArrow.setLayoutY(55);
            paneVariant.getChildren().add(upArrow);
            
            downArrow = new Line(0, 0, -2, 5);
            downArrow.setLayoutX(315);
            downArrow.setLayoutY(55);
            paneVariant.getChildren().add(downArrow);
        }
        else
        {
            Text limNegative = new Text("-∞");
            limNegative.setFont(Font.font(18));
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(45);
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setFont(Font.font(18));
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(120);
            paneVariant.getChildren().add(limPositive);
            
            Text asymValue = new Text(((LinearOnLinear)pop).getAsymstoreY().toString());
            asymValue.setFont(Font.font(18));
            asymValue.setLayoutX(135);
            asymValue.setLayoutY(120);
            paneVariant.getChildren().add(asymValue);
            
            asymValue = new Text(((LinearOnLinear)pop).getAsymstoreY().toString());
            asymValue.setFont(Font.font(18));
            asymValue.setLayoutX(190);
            asymValue.setLayoutY(45);
            paneVariant.getChildren().add(asymValue);
            
            Line line = new Line(0, 0, 100, 50);
            line.setLayoutX(30);
            line.setLayoutY(60);
            paneVariant.getChildren().add(line);
            
            Line downArrow = new Line(0, 0, -5, 2);
            downArrow.setLayoutX(130);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);
            
            Line upArrow = new Line(0, 0, -2, -5);
            upArrow.setLayoutX(130);
            upArrow.setLayoutY(110);
            paneVariant.getChildren().add(upArrow);
            
            line = new Line(0, 0, 100, 50);
            line.setLayoutX(215);
            line.setLayoutY(60);
            paneVariant.getChildren().add(line);
            
            downArrow = new Line(0, 0, -5, 2);
            downArrow.setLayoutX(315);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);
            
            upArrow = new Line(0, 0, -2, -5);
            upArrow.setLayoutX(315);
            upArrow.setLayoutY(110);
            paneVariant.getChildren().add(upArrow);
        }
    }
}
