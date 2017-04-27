import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmController {
    public void ConfirmOrder(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("ConfirmOrder.fxml"));
        Scene scene1 = new Scene(parent,600,400);
        Stage window = new Stage();
        window.setScene(scene1);
        window.show();
    }
    public void checkout(ActionEvent actionEvent) throws IOException {

        Parent parent1 = FXMLLoader.load(getClass().getResource("ConfirmOrder.fxml"));
        Scene scene1 = new Scene(parent1,600,400);
        Stage window = new Stage();
        window.hide();
        window.setScene(scene1);
        window.show();
    }
    public void cancel(ActionEvent actionEvent) throws IOException{

    }
    @FXML private javafx.scene.control.Button cancel;
    @FXML
    private void cancel(){
        // get a handle to the stage
        Stage stage = (Stage) cancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
