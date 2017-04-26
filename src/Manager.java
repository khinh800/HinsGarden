import com.sun.tools.javah.Mangle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Manager {
    private static ObservableList<ObservableList> data;
    private static TableView tableview;

    public static void managermenu() {
        String SQL = "SELECT Employee.EmployeeFirstName, Employee.EmplyoeeLastName, EmployeeType.EmployeeType"
                + " FROM Employee" +
                " ";
        Stage window = new Stage();
        BorderPane bp = new BorderPane();
        TabPane tabPane = new TabPane();

        //set up buttons
        Button btn1,btn2,btn3,btn4,btn5;
        btn1=new Button("Employees");
        btn2=new Button("Managers");
        btn3=new Button("Menu Items");
        btn4=new Button("Exit");
        btn1.getStyleClass().add("mmbutton");
        btn2.getStyleClass().add("mmbutton");
        btn3.getStyleClass().add("mmbutton");
        btn4.getStyleClass().add("mmbutton");

        //set up tabs
        Tab tab1 = new Tab("Show");
        Tab tab2 = new Tab("Add");
        Tab tab3 = new Tab("Delete");
        tabPane.getTabs().addAll(tab1,tab2,tab3);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        BuildReports BR = new BuildReports();



        VBox vertaddemp = new VBox();
        vertaddemp.setPadding(new Insets(20, 50, 50, 20));

        //Show add employee
        Label lfirstname = new Label("Employee First Name");
        Label llastname = new Label("Employee Last Name");

        TextField fieldfirstname = new TextField();
        TextField fieldlastname = new TextField();
        Button EmpSubmit = new Button("Add New Employee");
        Label successadded = new Label(" ");
        vertaddemp.getChildren().addAll(lfirstname,fieldfirstname, llastname,fieldlastname,EmpSubmit);
        vertaddemp.getChildren().add(successadded);
        tab2.setContent(vertaddemp);


        String EmpSQL = "SELECT * FROM employee";
        String MangSQL = "SELECT * FROM manager";
        String MenuSQL = "SELECT * FROM  menuitems";


        BorderPane emptable = new BorderPane();
        tableview = new TableView();
        tableview.setMinWidth(580);
        buildData(EmpSQL);
        emptable.setCenter(tableview);
        tab1.setContent(emptable);

        VBox verticalbox = new VBox();
        verticalbox.getChildren().addAll(btn1,btn2,btn3,btn4);
        verticalbox.setSpacing(5.0);
        verticalbox.getStyleClass().add("boxcolor");

        AnchorPane anklePane = new AnchorPane();
        anklePane.getStyleClass().add("altboxcolor");
        anklePane.getChildren().addAll(tabPane);

        bp.setLeft(verticalbox);
        bp.setCenter(anklePane);
        vertaddemp.setSpacing(15);
        EmpSubmit.setStyle("-fx-font: 16.5 arial; -fx-base: #ff4500;-fx-text-fill:white;");



        Scene scene1 = new Scene(bp,800,500);
        scene1.getStylesheets().add(Manager.class.getResource("style.css").toExternalForm());
        window.setScene(scene1);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Management Window");
        window.show();


        //THis is all of the button actions
        btn1.setOnAction(e->{
            vertaddemp.getChildren().remove(successadded);
            tableview = new TableView();
            tableview.setMinWidth(580);
            buildData(EmpSQL);
            emptable.setCenter(tableview);
            tab1.setContent(emptable);
            lfirstname.setText("Employee First Name");
            llastname.setText("Employee Last Name");
            EmpSubmit.setText("Add New Employee");
            EmpSubmit.setOnAction(a->{
                //On action, submit information into a new record
                Connection c;
                try{
                    String potato = FindLast.getTransID(1,EmpSQL);
                    int add1 = Integer.parseInt(potato)+1;
                    System.out.println(add1);
                    c=DataBaseConnection.connect();
                    String AddEmp = "INSERT INTO employee (employeeid, employeefirstname, employeelastname,managerid, employeetypeid) " +
                            "VALUES ("+add1+",'"+fieldfirstname.getText()+"','"+fieldlastname.getText()+"',"+1+","+1+ " );";
                    Statement stmt = c.createStatement();
                    stmt.execute(AddEmp);
                    vertaddemp.getChildren().add(successadded);
                    successadded.setText("Employee successfully added");

                }catch(SQLException ex){
                    System.out.println(ex);
                }

            });
        });
        btn2.setOnAction(e->{
            vertaddemp.getChildren().remove(successadded);
            tableview = new TableView();
            tableview.setMinWidth(580);
            buildData(MangSQL);
            emptable.setCenter(tableview);
            tab1.setContent(emptable);
            lfirstname.setText("Manager First Name");
            llastname.setText("Manager Last Name");
            EmpSubmit.setText("Add New Manager");
            EmpSubmit.setOnAction(a->{
                //On action, submit information into a new record
                Connection c;
                try{
                    String potato = FindLast.getTransID(1,MangSQL);
                    int add1 = Integer.parseInt(potato)+1;
                    System.out.println(add1);
                    c=DataBaseConnection.connect();
                    String AddMang = "INSERT INTO manager (managerid, managerfirstname, managerlastname,managertypeid) " +
                            "VALUES ("+add1+",'"+fieldfirstname.getText()+"','"+fieldlastname.getText()+"',"+1+");";
                    Statement stmt = c.createStatement();
                    stmt.execute(AddMang);
                    vertaddemp.getChildren().add(successadded);
                    successadded.setText("Manager successfully added");

                }catch(SQLException ex){
                    System.out.println(ex);
                }

            });
        });
        btn3.setOnAction(e->{
            vertaddemp.getChildren().remove(successadded);
            tableview = new TableView();
            tableview.setMinWidth(580);
            buildData(MenuSQL);
            emptable.setCenter(tableview);
            tab1.setContent(emptable);
            lfirstname.setText("New Menu Name");
            llastname.setText("Menu Price");
            EmpSubmit.setText("Add New Menu Item");
            EmpSubmit.setOnAction(a->{
                //On action, submit information into a new record
                Connection c;
                try{
                    String potato = FindLast.getTransID(1,MenuSQL);
                    int add1 = Integer.parseInt(potato)+1;
                    System.out.println(add1);
                    c=DataBaseConnection.connect();
                    String AddMenu = "INSERT INTO menuitems (menuitemid, itemname, itemprice,itemid) " +
                            "VALUES ("+add1+",'"+fieldfirstname.getText()+"','"+ Double.parseDouble(fieldlastname.getText()) +"',"+1+");";
                    Statement stmt = c.createStatement();
                    stmt.execute(AddMenu);
                    vertaddemp.getChildren().add(successadded);
                    successadded.setText("Menu Item successfully added");

                }catch(SQLException ex){
                    System.out.println(ex);
                }

            });
        });
        btn4.setOnAction(e->{
            window.close();
        });

    }

    public static void buildData(String InsertSql) {
        Connection c;
        data = FXCollections.observableArrayList();
        try {
            c = DataBaseConnection.connect();
            // SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = InsertSql;

            // ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                // We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(
                        new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            }
                        });

                tableview.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while (rs.next()) {
                // Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    // Iterate Column
                    String notthere = "null";
                    rs.getString(i);
                    if (rs.wasNull()) {
                        row.add(notthere);
                    } else {
                        row.add(rs.getString(i));
                    }

                }
                data.add(row);

            }

            // FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

    }
}
