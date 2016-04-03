package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainUI {
    @FXML
    Button server, client;

    @FXML
    public void clientBtn() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ClientUI.fxml"));
        Scene ss = new Scene(root);
        Stage stage = (Stage) client.getScene().getWindow();
        stage.setScene(ss);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void serverBtn() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ServerUI.fxml"));
        Scene ss = new Scene(root);
        Stage stage = (Stage) server.getScene().getWindow();
        stage.setScene(ss);
        stage.setResizable(false);
        stage.show();
    }

}
