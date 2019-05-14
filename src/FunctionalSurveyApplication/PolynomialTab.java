/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionalSurveyApplication;

import javafx.geometry.Pos;
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
    Label instruction = new Label();
    TextField inputPolynomial = new TextField();
    Button submit = new Button();
    
    void arrangeControls()
    {
        VBox mainLayout = new VBox();
        HBox inputLayout = new HBox();
        
        inputLayout.getChildren().addAll(inputPolynomial, submit);
        mainLayout.getChildren().addAll(instruction, inputLayout);
        
        instruction.setText("Nhập vào đa thức bậc 1, bậc 2, bậc 3 hoặc trùng phương. Lưu ý:\n"
                + "- Chỉ nhận các toán tử +, -, *, /, ^ và ngoặc tròn \"()\".\n"
                + "- Ứng dụng không thể giải quyết các đa thức ngoài các loại đã nêu trên.\n"
                + "- Chỉ chấp nhận hàm số dạng y = f(x).");
        instruction.setTextFill(Color.RED);
        instruction.setWrapText(true);
        
        submit.setText("Nhập");
        submit.setPrefSize(50, 10);
        submit.setAlignment(Pos.CENTER);
        inputPolynomial.setPrefSize(500, 10);
        
        this.setContent(mainLayout);
    }
}
