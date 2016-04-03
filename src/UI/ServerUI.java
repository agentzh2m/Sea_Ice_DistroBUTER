package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ServerUI {
    @FXML
    TableView ProgressTable;

    @FXML
    TableColumn IPColumn, ProgressPercentColumn;

    @FXML
    Button SearchBtn, StartBtn;

    @FXML
    TextArea ProgressArea; // using appendText()
                           // ProgressArea.appendText("\n" + message);

    @FXML
    public void SearchBtnAction() {

    }

    public void StartBtnAction() {


    }


}
