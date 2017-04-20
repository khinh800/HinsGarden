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


public class Manager {
    private static ObservableList<ObservableList> data;
    private static TableView tableview;

    public static void managermenu() {
        String SQL = "SELECT Employee.EmployeeFirstName, Employee.EmplyoeeLastName, EmployeeType.EmployeeType"
                + " FROM Employee" +
                "";
        Stage window = new Stage();
        BorderPane bp = new BorderPane();
        TabPane tabPane = new TabPane();

        Button btn1,btn2,btn3,btn4,btn5;
        btn1=new Button("Employees");
        btn2=new Button("Managers");
        btn3=new Button("Menu Items");
        btn4=new Button("Other");
        btn1.getStyleClass().add("mmbutton");
        btn2.getStyleClass().add("mmbutton");
        btn3.getStyleClass().add("mmbutton");
        btn4.getStyleClass().add("mmbutton");

        Tab tab1 = new Tab("Show");
        Tab tab2 = new Tab("Add");
        Tab tab3 = new Tab("Delete");
        tabPane.getTabs().addAll(tab1,tab2,tab3);

        BuildReports BR = new BuildReports();



        VBox vertaddemp = new VBox();
        vertaddemp.setPadding(new Insets(20, 50, 50, 20));

        //Show add employee
        Label lfirstname = new Label("Employee First Name");
        Label llastname = new Label("Employee Last Name");

        TextField fieldfirstname = new TextField();
        TextField fieldlastname = new TextField();
        vertaddemp.getChildren().addAll(lfirstname,fieldfirstname, llastname,fieldlastname);
        tab2.setContent(vertaddemp);


        String EmpSQL = "SELECT * FROM employee";
        BorderPane emptable = new BorderPane();
        tableview = new TableView();
        buildData(EmpSQL);
        emptable.setCenter(tableview);
        //emptable.getChildren().addAll(tableview);
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



        Scene scene1 = new Scene(bp,700,500);
        scene1.getStylesheets().add(Manager.class.getResource("style.css").toExternalForm());
        window.setScene(scene1);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Management Window");
        window.show();

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
                System.out.println("Column [" + i + "] ");
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
                System.out.println("Row [1] added " + row);
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
