import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;


public class PickupProcess {
    public static void PickUp(){
        Label enterphone,lbcustomer,lbphone,lbaddress,warninglabel;
        Button btnsearch;
        //THe text field to serach the phone number
        TextField textsearch;
        GridPane display,customergrid;
        Scene scene,scene1,scene2;
        Stage window = new Stage();

        display=new GridPane();
        warninglabel=new Label("");
        enterphone=new Label("Search for Phone Number");
        btnsearch=new Button("Search");
        textsearch=new TextField();
        textsearch.setPromptText("(###)-###-####");
        textsearch.maxHeight(25);
        textsearch.maxWidth(100);
        display.add(enterphone,0, 0);
        display.add(warninglabel,0,1);
        display.add(textsearch, 0, 2);
        display.add(btnsearch, 0,3);
        display.setAlignment(Pos.CENTER);
        display.setHgap(10);
        display.setVgap(10);
        scene=new Scene(display,400,300);


        GridPane anothergrid = new GridPane();
        scene2=new Scene(anothergrid, 400, 500);

        window.setTitle("Pick-Up");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();

        //styling
        btnsearch.setStyle("-fx-font: 16.5 arial; -fx-base: #ff4500;-fx-text-fill:white;");
        display.setStyle("-fx-background-color: antiquewhite");
        // Only permits entering of numbers inside the text search.
        // Author: Narayan
        textsearch.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()){
                    char ch = textsearch.getText().charAt(oldValue.intValue());
                    if(!(ch >='0' && ch <= '9')){
                        textsearch.setText(textsearch.getText().substring(0,textsearch.getText().length()-1));
                    }
                }
            }
        });
        btnsearch.setOnAction(e-> {

           if (textsearch.getText() == null || textsearch.getText().trim().isEmpty()){
               System.out.println("Nothing was entered into the text field.");
               warninglabel.setText("Please enter a number");
               warninglabel.setStyle("-fx-text-fill: red; ");
           }
           else{
               PhoneSearch.searchpickup(textsearch.getText());
               window.close();


           }
        });


    }
}
