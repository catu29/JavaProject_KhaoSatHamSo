
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
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
    private Text txtDoThi_result;
    @FXML
    private Button btnKhaoSat;
    @FXML
    private Pane paneScene;
    @FXML
    private Pane pane;  
    @FXML
    private Pane paneVariant;
    
    private CommonEquation equation;
    private double minX = -5.5;
    private double maxX = 5.5;
    private double minY = -4.5;
    private double maxY = 4.5;
    
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

            equation = new AnotherEquation(poly);

            if(poly.isLinear())
            {
                equation = new LinearEquation(poly);                
            }
            else if(poly.isQuadratic())
            {
                equation = new QuadraticEquation(poly);
            }
            else if(poly.isCubic())
            {
                equation = new CubicEquation(poly);
            }
            else if(poly.isQuartic())
            {
                equation = new QuarticEquation(poly);
            }   
            else
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Phần mềm chưa hỗ trợ tính năng khảo sát cho hàm số này.", ButtonType.OK);
                alert.show();
                
                paintGraph();
                return;
            }
            
            txtTXD_result.setText(equation.getSet());
            txtDaoHam_result.setText("y' = " + poly.derivativeString());
            txtGioiHan_result.setText(equation.limitation());
            txtBangBienThien_result.setText(equation.variant());
            txtNhanXet_result.setText(equation.comment());
            txtDoThi_result.setText(equation.value());
            txtFieldFX.setText(equation.getExpression().toString());
            
            paintGraph();
            paintVariantTable();
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
        double _y = equation.calculate(-5.5);
        
        for(double x = minX; x <= maxX; x += 0.125)
        {
            double y = equation.calculate(x);
            
            if(y > maxY || y < minY)
            {
                _y = y;
                continue;
            }
            
            Line line = new Line();
            if(pane.getChildren().size() != 0)
            {
                Line _line = (Line)pane.getChildren().get(pane.getChildren().size()-1);
                _line.setEndX(6.25);
                
                if(y > _y)
                {
                    _line.setEndY(-Math.abs(y - _y)*50);                    
                }  
                else
                {
                    _line.setEndY(Math.abs(y - _y)*50);
                }
            }
            
            line.setSmooth(true);
            line.setLayoutX(layoutX + (x-(-5.5))*50);
            line.setLayoutY(layoutY - (y-(4.5))*50);
            
            pane.getChildren().add(line);
            _y = y;
        }
    }
    
    private void paintVariantTable()
    {
        /*
        Roots:
        - Quardratic: (170, 20)
        - Quartic: (80, 20) (165, 20) (250, 20)
        - Cubic: (110, 20) (220, 20)
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        paneVariant.getChildren().clear();
        Polynomial poly = equation.getExpression();
        if(poly.isLinear())
        {
            paintLinear();
        }
        else if(poly.isQuadratic())
        {
            paintQuadratic();
        }
        else if(poly.isCubic())
        {
            paintCubic();
        }
        else if(poly.isQuartic())
        {
            paintQuartic();
        }
    }
    
    private void paintLinear()
    {
        /*
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        Polynomial poly = equation.getExpression();
        if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
        {
            Text limNegative = new Text("-∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(120);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(45);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);

            Line line = new Line(0, 0, 300, -60);
            line.setLayoutX(30);
            line.setLayoutY(110);
            line.setSmooth(true);
            paneVariant.getChildren().add(line);

            Line leftArrow = new Line(0, 0, -10, -5);
            leftArrow.setLayoutX(330);
            leftArrow.setLayoutY(50);
            paneVariant.getChildren().add(leftArrow);

            Line rightArrow = new Line(0, 0, -5, 10);
            rightArrow.setLayoutX(330);
            rightArrow.setLayoutY(50);
            paneVariant.getChildren().add(rightArrow);
        }
        else
        {
            Text limNegative = new Text("+∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(45);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("-∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(120);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);

            Line line = new Line(0, 0, 300, 60);
            line.setLayoutX(30);
            line.setLayoutY(50);
            line.setSmooth(true);
            paneVariant.getChildren().add(line);

            Line leftArrow = new Line(0, 0, -10, 5);
            leftArrow.setLayoutX(330);
            leftArrow.setLayoutY(110);
            paneVariant.getChildren().add(leftArrow);

            Line rightArrow = new Line(0, 0, -5, -10);
            rightArrow.setLayoutX(330);
            rightArrow.setLayoutY(110);
            paneVariant.getChildren().add(rightArrow);
        }
    }
    
    private void paintQuadratic()
    {
        /*
        Roots:
        - Quardratic: (170, 20)
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        
        Fractor Ix = ((QuadraticEquation)equation).getIX();
        
        Text root = new Text(Ix.toString());
        root.setLayoutX(170);
        root.setLayoutY(20);
        root.setFont(Font.font(18));
        paneVariant.getChildren().add(root);
        
        Text rootValue = new Text(equation.calculate(Ix).toString());
        rootValue.setLayoutX(170);
        rootValue.setFont(Font.font(18));
        
        Polynomial poly = equation.getExpression();
        
        if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
        {
            rootValue.setLayoutY(120);
            paneVariant.getChildren().add(rootValue);
            
            Text limNegative = new Text("+∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(45);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(45);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
            
            Line down = new Line(0, 0, 130, 60);
            down.setLayoutX(30);
            down.setLayoutY(50);
            paneVariant.getChildren().add(down);
            
            Line downArrow = new Line(0, 0, -5, -10);
            downArrow.setLayoutX(160);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);
            
            downArrow = new Line(0, 0, -10, 3);
            downArrow.setLayoutX(160);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);
            
            Line up = new Line(0, 0, 130, -60);
            up.setLayoutX(190);
            up.setLayoutY(110);
            paneVariant.getChildren().add(up);
            
            Line upArrow = new Line(0, 0, -10, -5);
            upArrow.setLayoutX(320);
            upArrow.setLayoutY(50);
            paneVariant.getChildren().add(upArrow);
            
            upArrow = new Line(0, 0, -3, 12);
            upArrow.setLayoutX(320);
            upArrow.setLayoutY(50);
            paneVariant.getChildren().add(upArrow);
        }
        else
        {
            rootValue.setLayoutY(45);
            paneVariant.getChildren().add(rootValue);
            
            Text limNegative = new Text("-∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(120);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("-∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(120);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
            
            Line up = new Line(0, 0, 130, -60);
            up.setLayoutX(30);
            up.setLayoutY(110);
            paneVariant.getChildren().add(up);
            
            Line upArrow = new Line(0, 0, -10, -5);
            upArrow.setLayoutX(160);
            upArrow.setLayoutY(50);
            paneVariant.getChildren().add(upArrow);
            
            upArrow = new Line(0, 0, -3, 12);
            upArrow.setLayoutX(160);
            upArrow.setLayoutY(50);
            paneVariant.getChildren().add(upArrow);
            
            Line down = new Line(0, 0, 130, 60);
            down.setLayoutX(190);
            down.setLayoutY(50);
            paneVariant.getChildren().add(down);
            
            Line downArrow = new Line(0, 0, -5, -10);
            downArrow.setLayoutX(320);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);
            
            downArrow = new Line(0, 0, -10, 3);
            downArrow.setLayoutX(320);
            downArrow.setLayoutY(110);
            paneVariant.getChildren().add(downArrow);   
        }
    }
    
    private void paintCubic()
    {
        /*
        Roots:
        - Cubic: (110, 20) (220, 20)
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        
        Polynomial poly = equation.getExpression();
        
        if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
        {
            Text limNegative = new Text("-∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(120);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(45);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
        }
        else
        {
            Text limNegative = new Text("+∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(45);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("-∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(120);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
        }

        if(((CubicEquation)equation).getDelta().isPositive()) // delta > 0 => derivative equation has 2 roots
        {
            double x1 = ((CubicEquation)equation).getX1();
            double x2 = ((CubicEquation)equation).getX2();
            
            Text X1 = new Text(String.valueOf(x1));
            X1.setFont(Font.font(18));
            X1.setLayoutY(20);
            
            Text x1Value = new Text(String.valueOf(equation.calculate(x1)));
            x1Value.setFont(Font.font(18));
            
            Text X2 = new Text(String.valueOf(x2));
            X2.setFont(Font.font(18));
            X2.setLayoutY(20);
            
            Text x2Value = new Text(String.valueOf(equation.calculate(x2)));
            x2Value.setFont(Font.font(18));
            
            // Test -x^3+3x^2-4
            if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
            {                
                if(x1 < x2)
                {
                    X1.setLayoutX(110);
                    paneVariant.getChildren().add(X1);
                    
                    X2.setLayoutX(220);
                    paneVariant.getChildren().add(X2);

                    x1Value.setLayoutX(110);
                    x1Value.setLayoutY(45);
                    paneVariant.getChildren().add(x1Value);
                    
                    x2Value.setLayoutX(220);
                    x2Value.setLayoutY(120);
                    paneVariant.getChildren().add(x2Value);  
                }
                else
                {
                    X1.setLayoutX(220);
                    paneVariant.getChildren().add(X1);
                    
                    X2.setLayoutX(110);
                    paneVariant.getChildren().add(X2);

                    x1Value.setLayoutX(220);
                    x1Value.setLayoutY(120);
                    paneVariant.getChildren().add(x1Value);
                    
                    x2Value.setLayoutX(110);
                    x2Value.setLayoutY(45);
                    paneVariant.getChildren().add(x2Value);                    
                }
                
                Line up = new Line(0, 0, 80, -60);
                up.setLayoutX(30);
                up.setLayoutY(110);
                paneVariant.getChildren().add(up); // Line from -∞

                Line upArrow = new Line(0, 0, -10, 0); // Line from -∞
                upArrow.setLayoutX(110);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow); 

                upArrow = new Line(0, 0, -5, 10); // Line from -∞
                upArrow.setLayoutX(110);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                up = new Line(0, 0, 80, -60); // Line to +∞
                up.setLayoutX(260);
                up.setLayoutY(110);
                paneVariant.getChildren().add(up); 

                upArrow = new Line(0, 0, -10, 0); // Line to +∞
                upArrow.setLayoutX(340); 
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                upArrow = new Line(0, 0, -5, 10); // Line to +∞
                upArrow.setLayoutX(340); 
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                Line down = new Line(0, 0, 80, 60);
                down.setLayoutX(130);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);

                Line downArrow = new Line(0, 0, -5, -10);
                downArrow.setLayoutX(210);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                downArrow = new Line(0, 0, -10, 0);
                downArrow.setLayoutX(210);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);
            }
            else
            {
                if(x1 < x2)
                {
                    X1.setLayoutX(110);
                    paneVariant.getChildren().add(X1);
                    
                    X2.setLayoutX(220);
                    paneVariant.getChildren().add(X2);

                    x1Value.setLayoutX(110);
                    x1Value.setLayoutY(120);
                    paneVariant.getChildren().add(x1Value);
                    
                    x2Value.setLayoutX(220);
                    x2Value.setLayoutY(45);
                    paneVariant.getChildren().add(x2Value);
                }
                else
                {
                    X1.setLayoutX(220);
                    paneVariant.getChildren().add(X1);
                    
                    X2.setLayoutX(110);
                    paneVariant.getChildren().add(X2);

                    x1Value.setLayoutX(220);
                    x1Value.setLayoutY(45);
                    paneVariant.getChildren().add(x1Value);
                    
                    x2Value.setLayoutX(110);
                    x2Value.setLayoutY(120);
                    paneVariant.getChildren().add(x2Value);
                }
                
                Line down = new Line(0, 0, 80, 55);
                down.setLayoutX(30);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down); // Line from -∞

                Line downArrow = new Line(0, 0, -5, -10); // Line from -∞
                downArrow.setLayoutX(110);
                downArrow.setLayoutY(105);
                paneVariant.getChildren().add(downArrow);

                downArrow = new Line(0, 0, -10, 0); // Line from -∞
                downArrow.setLayoutX(110);
                downArrow.setLayoutY(105);
                paneVariant.getChildren().add(downArrow);

                down = new Line(0, 0, 80, 60); // Line to +∞
                down.setLayoutX(250);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down); 

                downArrow = new Line(0, 0, -5, -10); // Line to +∞
                downArrow.setLayoutX(330);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                downArrow = new Line(0, 0, -10, 0); // Line to +∞
                downArrow.setLayoutX(330);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                Line up = new Line(0, 0, 80, -55);
                up.setLayoutX(140);
                up.setLayoutY(105);
                paneVariant.getChildren().add(up);

                Line upArrow = new Line(0, 0, -5, 10);
                upArrow.setLayoutX(220);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                upArrow = new Line(0, 0, -10, 0);
                upArrow.setLayoutX(220);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
            }
        }
        else
        {
            paintLinear();
        }
    }
    
    private void paintQuartic()
    {
        /*
        Roots:
        - Quartic: (80, 20) (165, 20) (250, 20)
        Values:
        - Top-Left: (5, 45)
        - Bottom-Left: (5, 120)
        - Top-Right: (335, 45)
        - Bottom-Right: (335, 120)
        */
        
        Polynomial poly = equation.getExpression();
        
        if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
        {
            Text limNegative = new Text("+∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(45);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("+∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(45);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
        }
        else
        {
            Text limNegative = new Text("-∞"); 
            limNegative.setLayoutX(5);
            limNegative.setLayoutY(120);
            limNegative.setFont(Font.font(18));
            paneVariant.getChildren().add(limNegative);

            Text limPositive = new Text("-∞");
            limPositive.setLayoutX(335);
            limPositive.setLayoutY(120);
            limPositive.setFont(Font.font(18));
            paneVariant.getChildren().add(limPositive);
        }
        
        Text zero = new Text("0");
        zero.setFont(Font.font(18));
        zero.setLayoutX(165);
        zero.setLayoutY(20);
        paneVariant.getChildren().add(zero);
        
        Text zeroValue = new Text(String.valueOf(equation.calculate(0)));
        zeroValue.setLayoutX(155);
        zeroValue.setFont(Font.font(18));
        
        Fractor root = ((QuarticEquation)equation).getRoot();
        
        if(root.isPositive()) // Deravative of quartic equation has 2 roots
        {            
            double X1 = ((QuarticEquation)equation).getX1();
            double X2 = ((QuarticEquation)equation).getX2();
            
            Text x1 = new Text(String.valueOf(X1));
            x1.setLayoutY(20);
            x1.setFont(Font.font(18));
            
            Text x2 = new Text(String.valueOf(X2));
            x2.setLayoutY(20);
            x2.setFont(Font.font(18));
            
            Text x1Value = new Text(String.valueOf(((QuarticEquation)equation).getX1()));
            x1Value.setFont(Font.font(18));
            
            Text x2Value = new Text(String.valueOf(((QuarticEquation)equation).getX2()));
            x2Value.setFont(Font.font(18));
            
            if(X1 < X2)
            {
                x1.setLayoutX(80);                    
                paneVariant.getChildren().add(x1);

                x1Value.setLayoutX(80);                

                x2.setLayoutX(250);
                paneVariant.getChildren().add(x2);

                x2Value.setLayoutX(250);                
            }
            else
            {
                x1.setLayoutX(250);
                paneVariant.getChildren().add(x1);

                x1Value.setLayoutX(250);

                x2.setLayoutX(80);
                paneVariant.getChildren().add(x2);

                x2Value.setLayoutX(80);
            }
            
            if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
            {
                zeroValue.setLayoutY(45);
                paneVariant.getChildren().add(zeroValue);
                
                x1Value.setLayoutY(115);
                paneVariant.getChildren().add(x1Value);
                
                x2Value.setLayoutY(115);
                paneVariant.getChildren().add(x2Value);
                
                Line down = new Line(0, 0, 50, 50); // Line from -∞
                down.setLayoutX(30);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);
                
                Line downArrow = new Line(0, 0, 0, -5);
                downArrow.setLayoutX(80);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                downArrow = new Line(0, 0, -5, 0);
                downArrow.setLayoutX(80);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                down = new Line(0, 0, 65, 50); // Line from root zero
                down.setLayoutX(185);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);
                
                downArrow = new Line(0, 0, 0, -5);
                downArrow.setLayoutX(250);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                downArrow = new Line(0, 0, -5, 0);
                downArrow.setLayoutX(250);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                Line up = new Line(0, 0, 50, -50); // Line to root zero
                up.setLayoutX(110);
                up.setLayoutY(100);
                paneVariant.getChildren().add(up);
                
                Line upArrow = new Line(0, 0, -5, 0);
                upArrow.setLayoutX(160);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                upArrow = new Line(0, 0, 0, 5);
                upArrow.setLayoutX(160);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                up = new Line(0, 0, 60, -50); // Line to +∞
                up.setLayoutX(280);
                up.setLayoutY(100);
                paneVariant.getChildren().add(up);
                
                upArrow = new Line(0, 0, -5, 0);
                upArrow.setLayoutX(340);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                upArrow = new Line(0, 0, 0, 5);
                upArrow.setLayoutX(340);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
            }
            else
            {
                zeroValue.setLayoutY(120);
                paneVariant.getChildren().add(zeroValue);
                
                x1Value.setLayoutY(45);
                paneVariant.getChildren().add(x1Value);
                
                x2Value.setLayoutY(45);
                paneVariant.getChildren().add(x2Value);
                
                Line up = new Line(0, 0, 50, -50); // Line from -∞
                up.setLayoutX(30);
                up.setLayoutY(100);
                paneVariant.getChildren().add(up);
                
                Line upArrow = new Line(0, 0, -5, 0);
                upArrow.setLayoutX(80);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                upArrow = new Line(0, 0, 0, 5);
                upArrow.setLayoutX(80);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                up = new Line(0, 0, 60, -50); // Line to from zero
                up.setLayoutX(185);
                up.setLayoutY(100);
                paneVariant.getChildren().add(up);
                
                upArrow = new Line(0, 0, -5, 0);
                upArrow.setLayoutX(245);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                upArrow = new Line(0, 0, 0, 5);
                upArrow.setLayoutX(245);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
                
                Line down = new Line(0, 0, 50, 50); // Line to root zero
                down.setLayoutX(110);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);
                
                Line downArrow = new Line(0, 0, 0, -5);
                downArrow.setLayoutX(160);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                downArrow = new Line(0, 0, -5, 0);
                downArrow.setLayoutX(160);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                down = new Line(0, 0, 65, 50); // Line from root zero
                down.setLayoutX(280);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);
                
                downArrow = new Line(0, 0, 0, -5);
                downArrow.setLayoutX(345);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
                
                downArrow = new Line(0, 0, -5, 0);
                downArrow.setLayoutX(345);
                downArrow.setLayoutY(100);
                paneVariant.getChildren().add(downArrow);
            }
        }
        else
        {
            if(poly.getPolynomial().get(poly.getPolynomial().size()-1).getCoefficient().isPositive()) // a>0
            {
                zeroValue.setLayoutY(120);
                paneVariant.getChildren().add(zeroValue);
                
                Line down = new Line(0, 0, 130, 60);
                down.setLayoutX(30);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);

                Line downArrow = new Line(0, 0, -5, -10);
                downArrow.setLayoutX(160);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                downArrow = new Line(0, 0, -10, 3);
                downArrow.setLayoutX(160);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                Line up = new Line(0, 0, 130, -60);
                up.setLayoutX(190);
                up.setLayoutY(110);
                paneVariant.getChildren().add(up);

                Line upArrow = new Line(0, 0, -10, -5);
                upArrow.setLayoutX(320);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                upArrow = new Line(0, 0, -3, 12);
                upArrow.setLayoutX(320);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);
            }
            else
            {
                zeroValue.setLayoutY(45);
                paneVariant.getChildren().add(zeroValue);
                
                Line up = new Line(0, 0, 130, -60);
                up.setLayoutX(30);
                up.setLayoutY(110);
                paneVariant.getChildren().add(up);

                Line upArrow = new Line(0, 0, -10, -5);
                upArrow.setLayoutX(160);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                upArrow = new Line(0, 0, -3, 12);
                upArrow.setLayoutX(160);
                upArrow.setLayoutY(50);
                paneVariant.getChildren().add(upArrow);

                Line down = new Line(0, 0, 130, 60);
                down.setLayoutX(190);
                down.setLayoutY(50);
                paneVariant.getChildren().add(down);

                Line downArrow = new Line(0, 0, -5, -10);
                downArrow.setLayoutX(320);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);

                downArrow = new Line(0, 0, -10, 3);
                downArrow.setLayoutX(320);
                downArrow.setLayoutY(110);
                paneVariant.getChildren().add(downArrow);  
            }
        }
    }
}
