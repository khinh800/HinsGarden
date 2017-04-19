import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;


public class Main extends Application{
    Stage window = new Stage();
    Button btn1, btn2,btnpickup,btndelivery,btnmanager,btnreports;
    Label lbl1, lbl2,lbl3;
    GridPane gr1, gr2;
    Scene scene1, scene2;
    public static void main(String[] args){
        //This statement connects the java code to the server
        //Connections must always be surrounded with try/catch methods.

        //THIS TRY/CATCH REQUIRES YOU TO HAVE A TABLE CALLED LOCATION AND A COLUMN IN IT CALLED ADDRESS
        //But you can edit the sql string to your specific needs if you want. :)
        try {
            Connection c = DataBaseConnection.connect();
            Statement stmt = c.createStatement();
            //Write an SQL query
            String SQL = "SELECT * FROM MenuItems";
            //Runs query inside database
            ResultSet rs = stmt.executeQuery(SQL);
            //rs.next selects the next row.
            for(int i = 0;i< 40;i++){
                rs.next();
                //rs.getString gets the String on the row where the column is "Address"
                String first = rs.getString("ItemName");
                String second = rs.getString("ItemPrice");
                String third = rs.getString("ItemID");
                //prints it all out.
                System.out.println(first + "|" + second + "|" + third);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        //Typical stuff.
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.3, 0.3, 0.3));
        window = primaryStage;
        window.setTitle("Hin's Garden Application");

        lbl1=new Label("Management");
        lbl2=new Label("Pickup");
        lbl3=new Label("Delivery");
        btn1=new Button("Click this");
        btn2=new Button("Click Back");

        btnmanager=new Button("Manager");
        btnmanager.setEffect(dropShadow);
        btnmanager.setMinSize(200, 100);
        btnmanager.setMaxSize(200, 100);
        btnmanager.setPadding(new Insets(30, 20, 30, 20));
        btnmanager.setStyle("" + "-fx-font-size: 20px;" + "-fx-border-radius: 50; " + "-fx-background-radius: 15; "
                + "-fx-background-color: #ff4500;" + "-fx-text-fill: white;");
        btnreports=new Button("Reports");
        btnreports.setEffect(dropShadow);
        btnreports.setMinSize(200, 100);
        btnreports.setMaxSize(200, 100);
        btnreports.setPadding(new Insets(30, 20, 30, 20));
        btnreports.setStyle("" + "-fx-font-size: 20px;" + "-fx-border-radius: 50; " + "-fx-background-radius: 15; "
                + "-fx-background-color: #ff4500;" + "-fx-text-fill: white;");
        btnpickup=new Button("Pick-Up");
        btnpickup.setEffect(dropShadow);
        btnpickup.setMinSize(200, 100);
        btnpickup.setMaxSize(200, 100);
        btnpickup.setPadding(new Insets(30, 20, 30, 20));
        btnpickup.setStyle("" + "-fx-font-size: 20px;" + "-fx-border-radius: 50; " + "-fx-background-radius: 15; "
                + "-fx-background-color: #ff4500;" + "-fx-text-fill: white;");
        btndelivery=new Button("Delivery");
        btndelivery.setEffect(dropShadow);
        btndelivery.setMinSize(200, 100);
        btndelivery.setMaxSize(200, 100);
        btndelivery.setPadding(new Insets(30, 20, 30, 20));
        btndelivery.setStyle("" + "-fx-font-size: 20px;" + "-fx-border-radius: 50; " + "-fx-background-radius: 15; "
                + "-fx-background-color: #ff4500;" + "-fx-text-fill: white;");

        gr1=new GridPane();
        gr1.setHgap(10);
        gr1.setStyle("-fx-background-color: antiquewhite");
        gr1.add(btnpickup,0,0);
        gr1.add(btndelivery,5, 0);
        gr1.setMargin(btnpickup, new Insets(10,10,10,10));
        gr1.setMargin(btndelivery, new Insets(10,10,10,520));

        Image mainImg = new Image("img/logo.png");
        ImageView iv2 = new ImageView();
        iv2.setImage(mainImg);

        AnchorPane anc = new AnchorPane();
        anc.getChildren().addAll(btnmanager,btnreports,btnpickup,btndelivery,iv2);
        anc.setStyle("-fx-background-color: antiquewhite");
        anc.setTopAnchor(btnmanager, 10.0);
        anc.setLeftAnchor(btnmanager,10.0);
        anc.setTopAnchor(btnreports, 10.0);
        anc.setRightAnchor(btnreports,10.0);

        anc.setBottomAnchor(btnpickup,50.0);
        anc.setLeftAnchor(btnpickup,200.0);
        anc.setBottomAnchor(btndelivery,50.0);
        anc.setRightAnchor(btndelivery, 200.0);


        anc.setTopAnchor(iv2, 100.0);
        anc.setLeftAnchor(iv2, 150.0);


        scene1=new Scene(anc, 1000,600);

        btnpickup.setOnAction(e -> PickupProcess.Pickup());
        btndelivery.setOnAction(e -> PickupProcess.Delivery());
        btnmanager.setOnAction(e -> Manager.managermenu());
        btn2.setOnAction(e -> handleButtonAction(e));
        btn1.setOnAction(e -> handleButtonAction(e));


		/* ==================LOGIN GRID================= */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: antiquewhite");
        grid.setAlignment(Pos.CENTER);

		/*
		 * ==================WINDOW DISPLAY================ This sets up the
		 * scenes and shows the windows.
		 */
        Scene logMenu = new Scene(grid, 400, 400);
        window.setScene(logMenu);
        window.show();

        // Name Label, placed in grid 0, 0
        Label userLabel = new Label("Username");
        GridPane.setConstraints(userLabel, 0, 1);
        userLabel.setStyle("-fx-font: 16.5 arial;");

        // Name Input, placed in grid 1, 0
        TextField userIn = new TextField();
        userIn.setPromptText("username");
        GridPane.setConstraints(userIn, 1, 1);

        // Password Label, placed in grid 0, 1
        Label passLabel = new Label("Password");
        passLabel.setStyle("-fx-font: 16.5 arial;");
        GridPane.setConstraints(passLabel, 0, 2);

        // Password Input
				/*
				 * TextField passIn = new TextField(); passIn.setPromptText("password");
				 */
        PasswordField passIn = new PasswordField();
        passIn.setPromptText("password");

        GridPane.setConstraints(passIn, 1, 2);

        // Login Button,
        Button logButt = new Button("Login");
        logButt.setStyle("-fx-font: 16.5 arial; -fx-base: #ff4500");
        logButt.setMinWidth(250);
        logButt.setAlignment(Pos.CENTER);
        GridPane.setConstraints(logButt, 1, 4);

        Image disImg = new Image("img/takeout.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(disImg);
        iv1.setFitHeight(200);
        iv1.setFitWidth(200);
        GridPane.setConstraints(iv1, 1, 0);

        grid.getChildren().addAll(userLabel, userIn, passLabel, passIn, logButt, iv1);
				/*
				 * ==================================CONDITIONAL
				 * LOGIN==============================================
				 */

        logButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if ((userIn.getText() != null && userIn.getText().contains("a")) && passIn.getText().contains("a")) {
                    System.out.println("Success!");
                    window.setScene(scene1);
                } else if ((userIn.getText() != null && userIn.getText().contains("Employee"))
                        && passIn.getText().contains("Employee")) {
                    System.out.println("Success!");
                    window.setScene(scene2);
                } else {

                    System.out.println("looololol, try 'a' in both fields or 'Customer' in both fields");

                }

            }

        });


    }

    /*
    *
    * */
    private void handleButtonAction(ActionEvent event){
        if (event.getTarget()==btn1)
            window.setScene(scene1);
        else if(event.getTarget()==btn2)
            window.setScene(scene2);
        else
            System.out.println("Nothing");
    }
}