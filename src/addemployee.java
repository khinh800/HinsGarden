import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class addemployee {
    static ComboBox<String> combobox;
    public static void display(){

        Stage window = new Stage();
        window.setTitle("Add Employee");
        window.initModality(Modality.APPLICATION_MODAL);



        BorderPane emp_Addemp = new BorderPane();
        VBox VBoxAddEmp = new VBox();
        VBoxAddEmp.setPadding(new Insets(20,20,20,20));

        Label Text = new Label("Add Employee Information");
        Text.setStyle("-fx-font-size: 40;");
        Text.setPadding(new Insets(30,30,30,30));


        Label EmployeeFName = new Label("First Name");
        TextField Da = new TextField();
        Label EmployeeLName = new Label("Last Name");
        TextField Ha = new TextField();
        Label Phone = new Label("Phone Number");
        TextField Ga = new TextField();
        Ga.setPromptText("xxx-xxx-xxxx");
        VBoxAddEmp.getChildren().addAll(EmployeeFName, Da, EmployeeLName, Ha, Phone, Ga);

        VBox OtherInfo = new VBox();
        OtherInfo.setPadding(new Insets(20,20,20,20));
        Label Location = new Label("Location");
        TextField Qa = new TextField();
        Label Role = new Label("Role");
        TextField Pa = new TextField();
        OtherInfo.getChildren().addAll(Location, Qa, Role, Pa);
        emp_Addemp.setTop(Text);
        emp_Addemp.setLeft(VBoxAddEmp);
        emp_Addemp.setCenter(OtherInfo);


        Scene EmpScene1 = new Scene(emp_Addemp, 700, 700);
        window.setScene(EmpScene1);
        window.showAndWait();

    }
}
