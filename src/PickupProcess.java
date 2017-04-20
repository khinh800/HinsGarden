import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by Pho on 4/6/17.
 */
public class PickupProcess {

    public static void Pickup(){
        Label enterphone,lbcustomer,lbphone,lbaddress;
        Button btnsearch;
        TextField textsearch;
        GridPane display;
        Scene scene;
        Stage window = new Stage();
        display=new GridPane();
        enterphone=new Label("Search for Phone Number");
        btnsearch=new Button("Search");
        textsearch=new TextField();
        textsearch.setPromptText("(###)-###-####");
        textsearch.maxHeight(25);
        textsearch.maxWidth(100);
        display.add(enterphone,0, 0);
        display.add(textsearch, 0, 1);
        display.add(btnsearch, 0,2);
        display.setAlignment(Pos.CENTER);
        display.setHgap(10);
        display.setVgap(10);
        scene=new Scene(display,400,500);

        window.setTitle("Pick-ups");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();


        btnsearch.setOnAction(e-> {
            boolean customerexist=false;
            if (customerexist == false){
                //leave this scene section blank, fill in all that is required
                System.out.println("You will display empty fields");
            }
            else{
                //fill in the necessary data for this section based on info from the database
                System.out.println("You grab data from the database that matches the phone number and populate fields");
            }
        });


    }
    public static void Delivery(){
        Label enterphone,lbcustomer,lbphone,lbaddress;
        Button btnsearch;
        TextField textsearch;
        GridPane display;
        Scene scene;
        Stage window = new Stage();
        display=new GridPane();
        enterphone=new Label("Search for Phone Number");
        btnsearch=new Button("Search");
        textsearch=new TextField();
        textsearch.setPromptText("(###)-###-####");
        textsearch.maxHeight(25);
        textsearch.maxWidth(100);
        display.add(enterphone,0, 0);
        display.add(textsearch, 0, 1);
        display.add(btnsearch, 0,2);
        display.setAlignment(Pos.CENTER);
        display.setHgap(10);
        display.setVgap(10);
        scene=new Scene(display,400,500);

        window.setTitle("Pick-ups");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();


        btnsearch.setOnAction(e-> {
            long phonenumber = Long.parseLong(textsearch.getText());
            Connection c;
            try{
                c = DataBaseConnection.connect();
                String PhoneSearch = "SELECT cpn.customerphonenumberid "
                        + "FROM customerphonenumber as cpn "
                        + "INNER JOIN customer as c ON cpn.customerphonenumberID = c.customerphonenumberID "
                        + "INNER JOIN customeraddress as ca ON cpn.customerphonenumberid = ca.customerphoennumberid "
                        + "WHERE cpn.customerphonenumber = %" + phonenumber +"%";
                //customer- first name, last name, address, phone number
                //customer order
                ResultSet rs = c.createStatement().executeQuery(PhoneSearch);
                if (!rs.isBeforeFirst()){
                    //leave this scene section blank, fill in all that is required
                    System.out.println("You will display empty fields");
                }
                else{
                    //fill in the necessary data for this section based on info from the database
                    System.out.println("You did not find a number ");
                }
            }
            catch (Exception a){
                a.printStackTrace();
                System.out.println("Error, prob doesn't exist");
            }



        });


    }
}
