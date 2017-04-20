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

/**
 * Created by Pho on 4/6/17.
 */
public class PickupProcess {

    public static void Pickup(){
        Label enterphone,lbcustomer,lbphone,lbaddress;
        Button btnsearch;
        TextField textsearch;
        GridPane display;
        Scene scene,scene1,scene2,scene3;
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
        scene=new Scene(display,400,400);
        scene1=new Scene(display, 400,400);
        scene2=new Scene(display, 400,400);

        window.setTitle("Pick-ups");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();


        btnsearch.setOnAction(e-> {
            long phonenumber = Long.parseLong(textsearch.getText());
            Connection c;
            try{
                c = DataBaseConnection.connect();
                String PhoneSearch = "SELECT cpn.customerphonenumberid, cpn.CustomerPhoneNumber, " +
                        "c.customerfirstname, c.customerlastname, ca.address "
                        + "FROM customerphonenumber as cpn "
                        + "INNER JOIN customer as c ON cpn.customerphonenumberID = c.customerphonenumberID "
                        + "INNER JOIN customeraddress as ca ON cpn.customerphonenumberid = ca.customerphonenumberid "
                        + "WHERE cpn.customerphonenumber = '" + phonenumber +"' ";
                //customer- first name, last name, address, phone number
                //customer order
                ResultSet rs = c.createStatement().executeQuery(PhoneSearch);
                String cust1 = rs.getString(1);
                String cust2 = rs.getString(2);
                String cust3 = rs.getString(3);
                String cust4 = rs.getString(4);
                String cust5 = rs.getString(5);

                if (rs != null && rs.next()){
                    window.setScene(scene1);
                    //Find customer with that ID
                    System.out.println("You found a number");
                    System.out.println(cust1
                            + " " + cust2
                            + " " + cust3
                            + " " + cust4);
                }
                else{
                    //Create new customer
                    System.out.println("You found a number ");
                }
            }
            catch (Exception a){
                a.printStackTrace();
                System.out.println("Error, prob doesn't exist");
            }

        });


    }




    public static void Delivery(){
        Label enterphone,lbcustomer,lbphone,lbaddress;
        Button btnsearch;
        //THe text field to serach the phone number
        TextField textsearch;
        GridPane display,customergrid;
        Scene scene,scene1,scene2;
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

        //Customer Grid
        customergrid=new GridPane();
        VBox fields = new VBox();
        TextField firstname = new TextField();
        TextField lastname = new TextField();
        TextField address = new TextField();
        TextField apartment = new TextField();
        Button custsubmit = new Button("Submit");
        fields.getChildren().addAll(firstname,lastname,address,apartment,custsubmit);
        customergrid.add(fields,0,1);
        scene1=new Scene(customergrid,400,500);

        GridPane anothergrid = new GridPane();
        scene2=new Scene(anothergrid, 400, 500);

        window.setTitle("Delivery");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();


        btnsearch.setOnAction(e-> {
            long phonenumber = Long.parseLong(textsearch.getText());
            Connection c;
            try{
                c = DataBaseConnection.connect();
                String PhoneSearch = "SELECT cpn.customerphonenumberid, cpn.customerphonenumber, "
                        + "c.customerfirstname, c.customerlastname, ca.address,ca.apartmentnumber"
                        + "FROM customerphonenumber as cpn "
                        + "INNER JOIN customer as c ON cpn.customerphonenumberID = c.customerphonenumberID "
                        + "INNER JOIN customeraddress as ca ON cpn.customerphonenumberid = ca.customerphonenumberid "
                        + "WHERE cpn.customerphonenumber = '" + phonenumber +"' ";
                //customer- first name, last name, address, phone number
                //customer order
                ResultSet rs = c.createStatement().executeQuery(PhoneSearch);
                int rsphoneid = rs.getInt(1);
                Long rsphonenumber = Long.parseLong(rs.getString(2));
                String rsfirstname= rs.getString(3);
                String rslastname= rs.getString(4);
                String rsaddress= rs.getString(5);
                String rsapartment= rs.getString(6);

                if (rs != null && rs.next()){
                    //Find customer with that ID
                    firstname.setText(rsfirstname);
                    lastname.setText(rslastname);
                    address.setText(rsaddress);
                    apartment.setText(rsapartment);
                    window.setScene(scene1);
                    System.out.println("You found a number");
                    System.out.println(rs.getString(1)
                            + " " + rs.getString(2)
                            + " " + rs.getString(3)
                            + " " + rs.getString(4)
                            + " " + rs.getString(5));

                    custsubmit.setOnAction(a -> {
                        window.setScene(scene2);
                    });
                }
                else{
                    //Create new customer
                    System.out.println("You found a number ");
                }
            }
            catch (Exception a){
                a.printStackTrace();
                System.out.println("Error, prob doesn't exist");
            }



        });


    }
}
