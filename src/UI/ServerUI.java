package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.text.Text;

/**
 * Created by nattakarnklongyut on 4/3/16.
 */
public class ServerUI {

    @FXML
    TableColumn IPTableView;

    @FXML
    Button SearchBtn;

    @FXML
    Button StartBtn;

    @FXML
    Text start_text;

    public ServerUI() {

    }

    @FXML
    public void SearchBtnAction() {

    }

    public void StartBtnAction() {
        this.start_text.setText("Start!!");
    }

}
