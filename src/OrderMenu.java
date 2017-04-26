import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderMenu {

    public static void AddOrder(int CustOrderID) {
        final ObservableList options1 = FXCollections.observableArrayList();
        final ObservableList options2= FXCollections.observableArrayList();
        final ObservableList options3 = FXCollections.observableArrayList();
        final ObservableList options4 = FXCollections.observableArrayList();
        final ObservableList options5 = FXCollections.observableArrayList();
        final ObservableList options6 = FXCollections.observableArrayList();
        final ObservableList options7 = FXCollections.observableArrayList();
        final ObservableList options8 = FXCollections.observableArrayList();
        final ObservableList options9 = FXCollections.observableArrayList();
        final ObservableList options10 = FXCollections.observableArrayList();

        ComboBox<String> lunchitem, appitem,soupitem,dietitem, specialitem,beefitem,chickenitem,porkitem,seafooditem,vegitem,riceitem,drinkitem;

        Stage window = new Stage();

        VBox col1;
        HBox row1,row2,row3,row4;
        BorderPane layout;

        PreparedStatement prep = null;
        Connection c;
        int countitems = 1;
        String query = "SELECT itemname FROM menuitems WHERE itemid = " +countitems+ " ";
        try{
            c=DataBaseConnection.connect();
            prep = c.prepareStatement(query);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                options1.add(rs.getString("itemname"));

            }
            prep.close();
            rs.close();
        }
        catch(SQLException e){
            Logger.getLogger(OrderMenu.class.getName()).log(Level.SEVERE,null,e);

        }

        lunchitem = new ComboBox(options1);
        lunchitem.setPromptText("Lunch Items");
        lunchitem.setMaxHeight(30);

        appitem = new ComboBox(options2);
        appitem.setMaxHeight(30);

        soupitem = new ComboBox(options3);
        soupitem.setMaxHeight(30);

        dietitem = new ComboBox(options4);
        dietitem.setMaxHeight(30);

        specialitem = new ComboBox(options5);
        specialitem.setMaxHeight(30);

        beefitem = new ComboBox(options6);
        beefitem.setMaxHeight(30);

        chickenitem = new ComboBox(options7);
        chickenitem.setMaxHeight(30);

        porkitem = new ComboBox(options8);
        porkitem.setMaxHeight(30);

        seafooditem = new ComboBox(options9);
        seafooditem.setMaxHeight(30);

        vegitem = new ComboBox(options10);
        vegitem.setMaxHeight(30);

        riceitem = new ComboBox(options10);
        riceitem.setMaxHeight(30);

        drinkitem = new ComboBox(options10);
        drinkitem.setMaxHeight(30);


        //Add boxes to hboxes
        col1=new VBox();
        row1=new HBox();
        row2=new HBox();
        row3=new HBox();
        row4=new HBox();
        row1.getChildren().addAll(lunchitem,appitem,soupitem);
        row2.getChildren().addAll(dietitem,specialitem,beefitem);
        row3.getChildren().addAll(chickenitem,porkitem,seafooditem);
        row4.getChildren().addAll(vegitem,riceitem,drinkitem);

        col1.getChildren().addAll(row1,row2,row3,row4);
        layout = new BorderPane();
        layout.setTop(col1);
        Scene scene2 = new Scene(layout,600,400);
        window.setScene(scene2);
        window.show();
    }
}
