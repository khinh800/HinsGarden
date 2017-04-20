
import java.sql.Connection;
import java.sql.ResultSet;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


public class ViewReports {
    private static ObservableList<ObservableList> data;
    private static TableView tableview;

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
                        new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
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
    public static void ReportDisplay(){

        String TotalOrderEachCust = "SELECT  COUNT (cust.CustomerFirstName) As TotalNumberOfOrderPerCustomer, CustomerFirstName, CustomerPhoneNumber "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID "
                + "GROUP BY cust.CustomerFirstName, custp.CustomerPhoneNumber ";

        Stage window = new Stage();

        HBox TopBox = new HBox();
        Label GeneralLocs = new Label("Hin's Garden Reports");
        GeneralLocs.setStyle("-fx-font-size: 40;");
        GeneralLocs.setPadding(new Insets(30, 30, 30, 30));

        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: antiquewhite");



        HBox CenterValue = new HBox();
        CenterValue.setSpacing(20);
        ScrollPane Scrolls = new ScrollPane();
        Scrolls.setContent(CenterValue);

        Button ButtGenTick = new Button("Sales\nReport");
        Button ButtEmpRep = new Button("Employee\nReport");
        Button ButtLocRep = new Button("Order\nReport");
        CenterValue.getChildren().addAll(ButtGenTick,ButtEmpRep,ButtLocRep);
        CenterValue.setAlignment(Pos.TOP_CENTER);
        //CSS for Buttons
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(10.0);
        dropShadow.setOffsetY(10.0);
        dropShadow.setColor(Color.color(0.2, 0.2, 0.2));

        ButtGenTick.setEffect(dropShadow);
        ButtGenTick.setOnAction(e -> SalesRep());
        ButtGenTick.setMinSize(200, 125);
        ButtGenTick.setMaxSize(100, 100);
        ButtGenTick.setStyle(""
                + "-fx-font-size: 25;"
                + "-fx-background-radius:25; "
                + "-fx-background-color: #4395aa");


        ButtEmpRep.setEffect(dropShadow);
        ButtEmpRep.setOnAction(e -> CustReports());
        ButtEmpRep.setPadding(new Insets(0, 30, 0, 30));
        ButtEmpRep.setMinSize(200, 125);
        ButtEmpRep.setMaxSize(100, 100);
        ButtEmpRep.setStyle(""
                + "-fx-font-size: 25;"
                + "-fx-background-radius:25; "
                + "-fx-background-color: #8fb96f");

        ButtLocRep.setEffect(dropShadow);
        ButtLocRep.setOnAction(e -> OrderReports());
        ButtLocRep.setMinSize(200, 125);
        ButtLocRep.setMaxSize(100, 100);
        ButtLocRep.setStyle(""
                + "-fx-font-size: 25;"
                + "-fx-background-radius:25; "
                + "-fx-background-color: #aa6c8f");


//the close button to exit out of window
        Button Close = new Button("Close");
        Close.setOnAction(e-> window.close());
        Close.setPrefWidth(100);
        Close.setStyle(""
                + "-fx-font-size: 20px;"
                + "-fx-background-radius:50; "
                + "-fx-background-color: #ff6961 ");
        Close.setAlignment(Pos.TOP_RIGHT);
        TopBox.getChildren().addAll(GeneralLocs, Close);


        layout.setTop(TopBox);
        layout.setCenter(CenterValue);
        Scene scene = new Scene(layout, 950, 600);

        window.setScene(scene);
        window.show();


		/*=====================================================================*/
        BorderPane Ticklayout = new BorderPane();
        Label LabTick = new Label("Ticket Sales Reports");
        Scene SceneTicket = new Scene(Ticklayout, 950, 600);


		/*=====================================================================*/
        BorderPane EmpLayout = new BorderPane();

        Scene SceneEmp = new Scene(EmpLayout, 950, 600);
    }

    public static void SalesRep() {
// Create buttons
        String MoneyDay = "SELECT Distinct sum (itemprice) as TotalProfitofaDay "
                + "FROM ManagerType as mt INNER JOIN Manager as m "
                + "ON mt.ManagerTypeID = m.ManagerTypeID "
                + "INNER JOIN Employee as emp "
                + "ON m.ManagerID = emp.ManagerID "
                + "INNER JOIN EmployeeType as empt "
                + "ON emp.EmployeeTypeID = empt.EmployeeTypeID "
                + "INNER JOIN CustomerOrder as co "
                + "ON emp.EmployeeID = co.EmployeeID "
                + "INNER JOIN CustomerPhoneNumber as cp "
                + "ON co.CustomerPhoneNumberID = cp.CustomerPhoneNumberID "
                + "INNER JOIN CustomerAddress as ca "
                + "ON cp.CustomerPhoneNumberID = ca.CustomerPhoneNumberID "
                + "INNER JOIN OrderList as ol "
                + "ON co.CustomerOrderID = ol.CustomerOrderID "
                + "INNER JOIN MenuItems as mi "
                + "ON ol.MenuItemID = mi.MenuItemID "
                + "INNER JOIN Customer as c "
                + "ON c.CustomerID = cp.CustomerID	"
                + "WHERE co.OrderDate ='2017-01-01' "
                + "ORDER By TotalProfitofaDay ";
        String MoneyWeek = "SELECT Distinct sum (itemprice) as TotalProfitofaWeek "
                + "FROM ManagerType as mt INNER JOIN Manager as m "
                + "ON mt.ManagerTypeID = m.ManagerTypeID "
                + "INNER JOIN Employee as emp "
                + "ON m.ManagerID = emp.ManagerID "
                + "INNER JOIN EmployeeType as empt "
                + "ON emp.EmployeeTypeID = empt.EmployeeTypeID "
                + "INNER JOIN CustomerOrder as co "
                + "ON emp.EmployeeID = co.EmployeeID "
                + "INNER JOIN CustomerPhoneNumber as cp "
                + "ON co.CustomerPhoneNumberID = cp.CustomerPhoneNumberID "
                + "INNER JOIN CustomerAddress as ca "
                + "ON cp.CustomerPhoneNumberID = ca.CustomerPhoneNumberID "
                + "INNER JOIN OrderList as ol "
                + "ON co.CustomerOrderID = ol.CustomerOrderID "
                + "INNER JOIN MenuItems as mi "
                + "ON ol.MenuItemID = mi.MenuItemID "
                + "INNER JOIN Customer as c "
                + "ON c.CustomerID = cp.CustomerID	"
                + "WHERE co.OrderDate BETWEEN '2017-01-01' AND '2017-01-07' "
                + "ORDER By TotalProfitofaWeek ";
        String MoneyMonth = "SELECT Distinct sum (itemprice) as TotalProfitofaMonth "
                + "FROM ManagerType as mt INNER JOIN Manager as m "
                + "ON mt.ManagerTypeID = m.ManagerTypeID "
                + "INNER JOIN Employee as emp "
                + "ON m.ManagerID = emp.ManagerID "
                + "INNER JOIN EmployeeType as empt "
                + "ON emp.EmployeeTypeID = empt.EmployeeTypeID "
                + "INNER JOIN CustomerOrder as co "
                + "ON emp.EmployeeID = co.EmployeeID "
                + "INNER JOIN CustomerPhoneNumber as cp "
                + "ON co.CustomerPhoneNumberID = cp.CustomerPhoneNumberID "
                + "INNER JOIN CustomerAddress as ca "
                + "ON cp.CustomerPhoneNumberID = ca.CustomerPhoneNumberID "
                + "INNER JOIN OrderList as ol "
                + "ON co.CustomerOrderID = ol.CustomerOrderID "
                + "INNER JOIN MenuItems as mi "
                + "ON ol.MenuItemID = mi.MenuItemID "
                + "INNER JOIN Customer as c "
                + "ON c.CustomerID = cp.CustomerID	"
                + "WHERE co.OrderDate BETWEEN '2017-01-01' AND '2017-01-31' "
                + "ORDER By TotalProfitofaMonth ";
        String MoneyYear = "SELECT Distinct sum (itemprice) as TotalProfitofaYear "
                + "FROM ManagerType as mt INNER JOIN Manager as m "
                + "ON mt.ManagerTypeID = m.ManagerTypeID "
                + "INNER JOIN Employee as emp "
                + "ON m.ManagerID = emp.ManagerID "
                + "INNER JOIN EmployeeType as empt "
                + "ON emp.EmployeeTypeID = empt.EmployeeTypeID "
                + "INNER JOIN CustomerOrder as co "
                + "ON emp.EmployeeID = co.EmployeeID "
                + "INNER JOIN CustomerPhoneNumber as cp "
                + "ON co.CustomerPhoneNumberID = cp.CustomerPhoneNumberID "
                + "INNER JOIN CustomerAddress as ca "
                + "ON cp.CustomerPhoneNumberID = ca.CustomerPhoneNumberID "
                + "INNER JOIN OrderList as ol "
                + "ON co.CustomerOrderID = ol.CustomerOrderID "
                + "INNER JOIN MenuItems as mi "
                + "ON ol.MenuItemID = mi.MenuItemID "
                + "INNER JOIN Customer as c "
                + "ON c.CustomerID = cp.CustomerID	"
                + "WHERE co.OrderDate BETWEEN '2017-01-01' AND '2017-12-31' "
                + "ORDER By TotalProfitofaYear ";

        Stage window = new Stage();
        Label GeneralLocs = new Label("Sales Reports");
        Label RandomInfo = new Label("Select one of the columns");
        RandomInfo.setStyle("-fx-font-size: 20;");

        //=========Buttons set up for time
        Button ButtDay = new Button("Total Profit\n by Day");
        ButtDay.setMinSize(150, 50);
        ButtDay.setMaxSize(100, 50);
        ButtDay.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:10; "
                + "-fx-background-color: #7adce2");
        Button ButtWeek = new Button("Total Profit\n by Week");
        ButtWeek.setMinSize(150, 50);
        ButtWeek.setMaxSize(100, 50);
        ButtWeek.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:10; "
                + "-fx-background-color: #84bde2");
        Button ButtMonth = new Button("Total Profit\n by Month");
        ButtMonth.setMinSize(150, 50);
        ButtMonth.setMaxSize(100, 50);
        ButtMonth.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:10; "
                + "-fx-background-color: #7adce2");
        Button ButtYear = new Button("Total Profit\n by Year");
        ButtYear.setMinSize(150, 50);
        ButtYear.setMaxSize(100, 50);
        ButtYear.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:10; "
                + "-fx-background-color: #84bde2");




        BorderPane layout = new BorderPane();
        VBox Left = new VBox();
        VBox CenterValue = new VBox();
        ScrollPane Scrolls = new ScrollPane();
        Scrolls.setContent(CenterValue);
        CenterValue.getChildren().addAll(RandomInfo);

        window.setTitle("Hin's Garden");
        window.initModality(Modality.APPLICATION_MODAL);
        layout.setStyle("-fx-background-color: antiquewhite");
        GeneralLocs.setStyle("-fx-font-size: 40;");
        GeneralLocs.setPadding(new Insets(30, 30, 30, 30));

        //=======PERFORM BUTTON ACTION, make tables
        ButtDay.setOnAction(e -> {
            tableview = new TableView();
            buildData(MoneyDay);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtWeek.setOnAction(e -> {
            tableview = new TableView();
            buildData(MoneyWeek);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtMonth.setOnAction(e -> {
            tableview = new TableView();
            buildData(MoneyMonth);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtYear.setOnAction(e -> {
            tableview = new TableView();
            buildData(MoneyYear);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });

        Left.getChildren().addAll(ButtDay, ButtWeek, ButtMonth, ButtYear);

        VBox Right = new VBox();
        Label Stuff = new Label("                 ");
        Right.minWidth(100);
        Right.getChildren().add(Stuff);

        //CSS to set everything
        Left.setPadding(new Insets(0, 10, 0, 10));
        HBox Bottom = new HBox();
        Bottom.setPadding(new Insets(0, 10, 30, 30));
        Button Close = new Button("Close");
        Close.setOnAction(e-> window.close());
        Close.setPrefWidth(100);
        Close.setStyle(""
                + "-fx-font-size: 20px;"
                + "-fx-background-radius:50; "
                + "-fx-background-color: #ff6961 ");
        Bottom.getChildren().add(Close);
        layout.setBottom(Bottom);

        layout.setRight(Right);
        layout.setTop(GeneralLocs);
        layout.setLeft(Left);
        layout.setCenter(CenterValue);
        // Main Scene
        Scene scene = new Scene(layout, 950, 600);

        window.setScene(scene);
        window.show();

    }
//Show customer Report
    public static void CustReports() {

        Stage window = new Stage();
        Label GeneralLocs = new Label("Customer Reports");
        Label RandomInfo = new Label("<-Select one of the options to view in the left column");
        RandomInfo.setStyle("-fx-font-size: 20;");

        Button ButtGenEmp = new Button("General Customer\nInformation");
        ButtGenEmp.setMinSize(150, 50);
        ButtGenEmp.setMaxSize(100, 50);
        ButtGenEmp.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:100; "
                + "-fx-background-color: #C06A45");
        Button ButtEmpSchedule = new Button("Location Employees");
        ButtEmpSchedule.setMinSize(150, 50);
        ButtEmpSchedule.setMaxSize(100, 50);
        ButtEmpSchedule.setStyle(""
                + "-fx-font-size: 11px;"
                + "-fx-background-radius:100; "
                + "-fx-background-color: #B96F6F");
        Button ButtLocSales = new Button("Location \n"
                + "Costs & Sales");
        ButtLocSales.setMinSize(150, 50);
        ButtLocSales.setMaxSize(100, 50);
        ButtLocSales.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:100; "
                + "-fx-background-color: #C98A4B");


        BorderPane layout = new BorderPane();
        VBox Left = new VBox();
        VBox CenterValue = new VBox();
        ScrollPane Scrolls = new ScrollPane();
        Scrolls.setContent(CenterValue);
        CenterValue.getChildren().addAll(RandomInfo);

        window.setTitle("Update Locations");
        window.initModality(Modality.APPLICATION_MODAL);
        layout.setStyle("-fx-background-color: antiquewhite");
        GeneralLocs.setStyle("-fx-font-size: 40;");
        GeneralLocs.setPadding(new Insets(30, 30, 30, 30));

        ButtGenEmp.setOnAction(e -> {
            tableview = new TableView();
//            buildData(OrderHistory);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtEmpSchedule.setOnAction(e -> {
            tableview = new TableView();
//            buildData(TotalOrderEachCust);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        Left.getChildren().addAll(ButtGenEmp, ButtEmpSchedule, ButtLocSales);

        VBox Right = new VBox();
        Label Stuff = new Label("                 ");
        Right.minWidth(100);
        Right.getChildren().add(Stuff);

        //CSS to set everything
        Left.setPadding(new Insets(0, 10, 0, 10));
        HBox Bottom = new HBox();
        Bottom.setPadding(new Insets(0, 10, 30, 30));
        Button Close = new Button("Close");
        Close.setOnAction(e-> window.close());
        Close.setPrefWidth(100);
        Close.setStyle(""
                + "-fx-font-size: 20px;"
                + "-fx-background-radius:50; "
                + "-fx-background-color: #ff6961 ");
        Bottom.getChildren().add(Close);
        layout.setBottom(Bottom);

        layout.setRight(Right);
        layout.setTop(GeneralLocs);
        layout.setLeft(Left);
        layout.setCenter(CenterValue);
        // Main Scene
        Scene scene = new Scene(layout, 950, 600);

        window.setScene(scene);
        window.show();
    }

    public static void OrderReports() {
        String OrderHistory = "SELECT ItemName, ItemPrice, OrderType, OrderTime, OrderDate, (CustomerFirstName+' '+CustomerLastName) AS CustomerName "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID ";
        String TotalOrderPrice = "SELECT  SUM (mn.ItemPrice) as TOTALofOrderPrice, CustomerFirstName, CustomerLastName "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID "
                + "GROUP BY mn.ItemPrice, cust.CustomerFirstName, cust.CustomerLastName "
                + "ORDER BY TOTALofOrderPrice DESC ";
        String DaysMostOrd = "SELECT  COUNT (Custo.OrderDate) as DaywithMostOrders, Custo.OrderDate "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID "
                + "INNER JOIN Employee as emp "
                + "ON Custo.EmployeeID = emp.EmployeeID "
                + "Group By Custo.OrderDate,Custo.OrderDate "
                + "ORDER BY DaywithMostOrders Desc ";
        String DayLessOrd = "SELECT  COUNT (Custo.OrderDate) as DaywithLeastOrders, Custo.OrderDate "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID "
                + "INNER JOIN Employee as emp "
                + "ON Custo.EmployeeID = emp.EmployeeID "
                + "Group By Custo.OrderDate,Custo.OrderDate "
                + "ORDER BY DaywithLeastOrders Asc ";
        String BusyTimeDay = "SELECT  COUNT (Custo.OrderTime) as Busytime, Custo.OrderTime "
                + "FROM MenuItems as mn INNER JOIN OrderList as ol "
                + "ON mn.MenuItemID = ol.MenuItemID "
                + "INNER JOIN CustomerOrder as Custo "
                + "on ol.CustomerOrderID = Custo.CustomerOrderID "
                + "INNER JOIN CustomerPhoneNumber as custp "
                + "ON Custo.CustomerPhoneNumberID = custp.CustomerPhoneNumberID "
                + "INNER JOIN Customer as cust "
                + "ON custp.CustomerID = cust.CustomerID "
                + "INNER JOIN Employee as emp "
                + "ON Custo.EmployeeID = emp.EmployeeID "
                + "Group By Custo.OrderTime,Custo.OrderTime "
                + "ORDER BY Busytime Desc ";

        Stage window = new Stage();
        Label GeneralLocs = new Label("Order Reports");
        Label RandomInfo = new Label("<- Pick a report to view");
        RandomInfo.setStyle("-fx-font-size: 20;");

        Button ButtGenOrdHist = new Button("General Order\nHistory");
        ButtGenOrdHist.setMinSize(150, 50);
        ButtGenOrdHist.setMaxSize(100, 50);
        ButtGenOrdHist.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:5; "
                + "-fx-background-color: #ee9ef2");
        Button ButtOrdPrice = new Button("Total Order\nPrice");
        ButtOrdPrice.setMinSize(150, 50);
        ButtOrdPrice.setMaxSize(100, 50);
        ButtOrdPrice.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:5; "
                + "-fx-background-color: #c07aa3");
        Button ButtMoreOrder = new Button("Days with\nMost Orders");
        ButtMoreOrder.setMinSize(150, 50);
        ButtMoreOrder.setMaxSize(100, 50);
        ButtMoreOrder.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:5; "
                + "-fx-background-color: #ee9ef2");
        Button ButtLessOrder = new Button("Days with\nLeast Orders");
        ButtLessOrder.setMinSize(150, 50);
        ButtLessOrder.setMaxSize(100, 50);
        ButtLessOrder.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:5; "
                + "-fx-background-color: #c07aa3");
        Button ButtBusyTime = new Button("Busiest \nTimes");
        ButtBusyTime.setMinSize(150, 50);
        ButtBusyTime.setMaxSize(100, 50);
        ButtBusyTime.setStyle(""
                + "-fx-font-size: 13px;"
                + "-fx-background-radius:5; "
                + "-fx-background-color: #ee9ef2");


        BorderPane layout = new BorderPane();
        VBox Left = new VBox();
        VBox CenterValue = new VBox();
        ScrollPane Scrolls = new ScrollPane();
        Scrolls.setContent(CenterValue);
        CenterValue.getChildren().addAll(RandomInfo);

        window.setTitle("Update Locations");
        window.initModality(Modality.APPLICATION_MODAL);
        layout.setStyle("-fx-background-color: antiquewhite");
        GeneralLocs.setStyle("-fx-font-size: 40;");
        GeneralLocs.setPadding(new Insets(30, 30, 30, 30));

        ButtGenOrdHist.setOnAction(e -> {
            tableview = new TableView();
            buildData(OrderHistory);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtOrdPrice.setOnAction(e -> {
            tableview = new TableView();
            buildData(TotalOrderPrice);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtMoreOrder.setOnAction(e -> {
            tableview = new TableView();
            buildData(DaysMostOrd);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtLessOrder.setOnAction(e -> {
            tableview = new TableView();
            buildData(DayLessOrd);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });
        ButtBusyTime.setOnAction(e -> {
            tableview = new TableView();
            buildData(BusyTimeDay);
            CenterValue.getChildren().clear();
            CenterValue.getChildren().addAll(tableview);
        });

        Left.getChildren().addAll(ButtGenOrdHist, ButtOrdPrice, ButtMoreOrder,ButtLessOrder,ButtBusyTime);

        VBox Right = new VBox();
        Label Stuff = new Label("                 ");
        Right.minWidth(100);
        Right.getChildren().add(Stuff);

        //CSS to set everything
        Left.setPadding(new Insets(0, 10, 0, 10));
        HBox Bottom = new HBox();
        Bottom.setPadding(new Insets(0, 10, 30, 30));
        Button Close = new Button("Close");
        Close.setOnAction(e-> window.close());
        Close.setPrefWidth(100);
        Close.setStyle(""
                + "-fx-font-size: 20px;"
                + "-fx-background-radius:10; "
                + "-fx-background-color: #ff6961 ");
        Bottom.getChildren().add(Close);
        layout.setBottom(Bottom);

        layout.setRight(Right);
        layout.setTop(GeneralLocs);
        layout.setLeft(Left);
        layout.setCenter(CenterValue);
        // Main Scene
        Scene scene = new Scene(layout, 950, 600);

        window.setScene(scene);
        window.show();

    }
}