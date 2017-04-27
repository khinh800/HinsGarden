import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PhoneSearch {
    public static void search(String phone){
        Label labelfirstname,labellastname,labeladdress,labelapartment;
        TextField firstname,lastname,address,apartment;
        Stage window = new Stage();
        //Customer Grid
        GridPane customergrid=new GridPane();
        VBox fields = new VBox();
        HBox buttonfield = new HBox();

        //Label
        labelfirstname=new Label("First Name");
        labellastname=new Label("Last Name");
        labeladdress=new Label("Address");
        labelapartment=new Label("Apartment #");
        //input
        firstname = new TextField();
        lastname = new TextField();
        address = new TextField();
        apartment = new TextField();
        Button custsubmit = new Button("Submit");
        Button backbutton = new Button("Back");

        buttonfield.getChildren().addAll(backbutton,custsubmit);
        fields.getChildren().addAll(labelfirstname,firstname,labellastname,lastname,labeladdress,
                address,labelapartment,apartment,buttonfield);
        customergrid.add(fields,0,1);
        Scene scene1=new Scene(customergrid,400,300);
        window.setScene(scene1);
        window.setTitle("Delivery");
        window.show();

        //styling
        customergrid.setVgap(20);
        customergrid.setHgap(10);
        customergrid.setValignment(fields, VPos.CENTER);
        customergrid.setAlignment(Pos.CENTER);
        buttonfield.setAlignment(Pos.CENTER);
        buttonfield.setSpacing(20);
        customergrid.setStyle("-fx-background-color: antiquewhite");
        backbutton.setStyle("-fx-font: 16.5 arial; -fx-base: #84bde2;-fx-text-fill:white;");
        custsubmit.setStyle("-fx-font: 16.5 arial; -fx-base: orangered;-fx-text-fill:white;");
        fields.setSpacing(5);

        long phonenumber = Long.parseLong(phone);
        String PhoneSearch = "SELECT customerphonenumber.customerphonenumberid, customerphonenumber.customerphonenumber, "
                + "customer.customerfirstname, customer.customerlastname, customeraddress.address, "
                + "customeraddress.apartmentnumber, customer.customerid, customeraddress.addressid "
                + "FROM customerphonenumber "
                + "INNER JOIN customer ON customerphonenumber.customerphonenumberid = customer.customerphonenumberid "
                + "INNER JOIN customeraddress ON customerphonenumber.customerphonenumberid = customeraddress.customerphonenumberid "
                + "WHERE customerphonenumber.customerphonenumber = " + phonenumber +" ";


        System.out.println(phonenumber);
        Connection c;
        try{

            //customer- first name, last name, address, phone number
            //customer order
            c = DataBaseConnection.connect();
            ResultSet rs = c.createStatement().executeQuery(PhoneSearch);
            if (rs.next()){
                window.setScene(scene1);

                int rsphoneid = rs.getInt(1);
                int rscustomerid = rs.getInt(7);
                int rsaddressid = rs.getInt(8);
                Long rsphonenumber = Long.parseLong(rs.getString(2));
                String rsfirstname= rs.getString(3);
                String rslastname= rs.getString(4);
                String rsaddress= rs.getString(5);
                String rsapartment= rs.getString(6);
                //Find customer with that ID
                firstname.setText(rsfirstname);
                lastname.setText(rslastname);
                address.setText(rsaddress);
                apartment.setText(rsapartment);
                System.out.println("You found a number");
                System.out.println(rs.getString(1)
                        + " " + rs.getString(2)
                        + " " + rs.getString(3)
                        + " " + rs.getString(4)
                        + " " + rs.getString(5));
                custsubmit.setOnAction(e->{

                    try{
                        String UpdateCustomer = "UPDATE customer " +
                                "SET customerfirstname = '"+firstname.getText() + "', " +
                                "customerlastname =  '"+lastname.getText()+"', " +
                                "customerphonenumberid = "+ rsphoneid +" " +
                                "WHERE customerid = " + rscustomerid + " " ;
                        String UpdateAddress = "UPDATE customeraddress "
                                + "SET address = '"+ address.getText() +"', "
                                + "apartmentnumber = '"+apartment.getText()+"' "
                                + "WHERE addressid = "+ rsaddressid+" ";
                        Connection b;
                        b = DataBaseConnection.connect();
                        PreparedStatement prep = b.prepareStatement(UpdateCustomer);
                        prep.executeUpdate();
                        prep = b.prepareStatement(UpdateAddress);
                        prep.executeUpdate();

                    }
                    catch(SQLException a){
                        System.out.println("You got an error while running the submit button");
                    }
                    //OrderMenu.AddOrder(5);
                    System.out.println("Testing stuff");
                    window.close();
                    try{showmenu();}
                    catch(Exception b){
                        System.out.println(b);
                        System.out.println("Nothing is working");
                    }
                });
            }
            else{
                System.out.println("You didn't find a number, if statement fails");
            }
        }
        catch (SQLException a){
            a.printStackTrace();
            System.out.println("ERROR, there's a bit of an error ");
        }

        backbutton.setOnAction(e->{
            window.close();
            DeliveryProcess.Delivery();
        });
    }
    public static void searchpickup(String phone){
        Label labelfirstname,labellastname;
        TextField firstname,lastname;
        Stage window = new Stage();
        //Customer Grid
        GridPane customergrid=new GridPane();
        VBox fields = new VBox();
        HBox buttonfield = new HBox();

        //Label
        labelfirstname=new Label("First Name");
        labellastname=new Label("Last Name");
        //input
        firstname = new TextField();
        lastname = new TextField();
        Button custsubmit = new Button("Submit");
        Button backbutton = new Button("Back");

        buttonfield.getChildren().addAll(backbutton,custsubmit);
        fields.getChildren().addAll(labelfirstname,firstname,labellastname,lastname,buttonfield);
        customergrid.add(fields,0,1);
        Scene scene1=new Scene(customergrid,400,300);
        window.setScene(scene1);
        window.setTitle("Pick-Up");
        window.show();

        //styling
        customergrid.setVgap(20);
        customergrid.setHgap(10);
        customergrid.setValignment(fields, VPos.CENTER);
        customergrid.setAlignment(Pos.CENTER);
        buttonfield.setAlignment(Pos.CENTER);
        buttonfield.setSpacing(20);
        customergrid.setStyle("-fx-background-color: antiquewhite");
        backbutton.setStyle("-fx-font: 16.5 arial; -fx-base: #84bde2;-fx-text-fill:white;");
        custsubmit.setStyle("-fx-font: 16.5 arial; -fx-base: orangered;-fx-text-fill:white;");
        fields.setSpacing(5);

        long phonenumber = Long.parseLong(phone);
        String PhoneSearch = "SELECT customerphonenumber.customerphonenumberid, customerphonenumber.customerphonenumber, "
                + "customer.customerfirstname, customer.customerlastname, customer.customerid "
                + "FROM customerphonenumber "
                + "INNER JOIN customer ON customerphonenumber.customerphonenumberid = customer.customerphonenumberid "
                + "WHERE customerphonenumber.customerphonenumber = " + phonenumber +" ";


        System.out.println(phonenumber);
        Connection c;
        try{

            //customer- first name, last name, address, phone number

            c = DataBaseConnection.connect();
            ResultSet rs = c.createStatement().executeQuery(PhoneSearch);
            if (rs.next()){
                window.setScene(scene1);

                int rsphoneid = rs.getInt(1);
                int rscustomerid = rs.getInt(5);
                Long rsphonenumber = Long.parseLong(rs.getString(2));
                String rsfirstname= rs.getString(3);
                String rslastname= rs.getString(4);
                //Find customer with that ID
                firstname.setText(rsfirstname);
                lastname.setText(rslastname);
                System.out.println("You found a number");
                System.out.println(rs.getString(1)
                        + " " + rs.getString(2)
                        + " " + rs.getString(3)
                        + " " + rs.getString(4)
                        + " " + rs.getString(5));
                custsubmit.setOnAction(e->{

                    try{
                        String UpdateCustomer = "UPDATE customer " +
                                "SET customerfirstname = '"+firstname.getText() + "', " +
                                "customerlastname =  '"+lastname.getText()+"', " +
                                "customerphonenumberid = "+ rsphoneid +" " +
                                "WHERE customerid = " + rscustomerid + " " ;
                        Connection b;
                        b = DataBaseConnection.connect();
                        PreparedStatement prep = b.prepareStatement(UpdateCustomer);
                        prep.executeUpdate();

                    }
                    catch(SQLException a){
                        System.out.println("You got an error while running the submit button");
                    }
                    //OrderMenu.AddOrder(5);
                    System.out.println("Testing pickups ");
                    window.close();
                    try{
                        showmenu();
                    }
                    catch(Exception b){
                        System.out.println(b);
                    }
                });
            }
            else{
                System.out.println("You didn't find a number, if statement fails");
            }
        }
        catch (SQLException a){
            a.printStackTrace();
            System.out.println("ERROR, there's a bit of an error ");
        }

        backbutton.setOnAction(e->{
            window.close();
            PickupProcess.PickUp();
        });
    }
    public static void showmenu() throws Exception{
        Stage window = new Stage();
        Parent root = FXMLLoader.load(Main.class.getResource("ordermenu.fxml"));
        Scene scene2 = new Scene(root,600,400 );
        window.setScene(scene2);
        window.show();
    }

}
