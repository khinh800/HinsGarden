import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Pho on 4/6/17.
 */
public class Manager {
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
}
