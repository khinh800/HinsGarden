import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeliveryProcess {
    public static void Delivery(){
        Label enterphone,lbphone,lbaddress,warninglabel;
        Button btnsearch;
        //THe text field to serach the phone number
        TextField textsearch;
        GridPane display1;
        Scene scene,scene1,scene2;
        Stage window = new Stage();

        display1=new GridPane();
        warninglabel=new Label("");
        enterphone=new Label("Search for Number");
        btnsearch=new Button("Search");
        textsearch=new TextField();
        textsearch.setPromptText("(###)-###-####");
        textsearch.maxHeight(25);
        textsearch.maxWidth(100);
        display1.add(enterphone,0, 0);
        display1.add(warninglabel,0,1);
        display1.add(textsearch, 0, 2);
        display1.add(btnsearch, 0,3);
        display1.setAlignment(Pos.CENTER);
        display1.setHgap(10);
        display1.setVgap(10);
        scene=new Scene(display1,400,300);


        GridPane anothergrid = new GridPane();
        scene2=new Scene(anothergrid, 400, 500);

        window.setTitle("Delivery");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();

        //styling
        btnsearch.setStyle("-fx-font: 16.5 arial; -fx-base: #ff4500;-fx-text-fill:white;");
        display1.setStyle("-fx-background-color: antiquewhite");
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
                PhoneSearch.search(textsearch.getText());
                window.close();


            }
        });


    }
}
