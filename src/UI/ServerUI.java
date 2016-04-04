package UI;

import Master.ServerModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerUI {
    @FXML
    TableView ProgressTable;

    @FXML
    TableColumn IPColumn, ProgressPercentColumn;

    @FXML
    Button SearchBtn, StartBtn, choose_file;

    @FXML
    TextArea ProgressArea, cf_field; // using appendText()
                           // ProgressArea.appendText("\n" + message);

    @FXML
    public void SearchBtnAction() {

    }

    public void StartBtnAction() {
        List<String> clientLst = new ArrayList<>();
        if(cf_field.getText().length() < 1) {
            Master.ServerModel sm = new ServerModel(clientLst, new File(cf_field.getText()));
        }else {
            //insert error popup box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("");
            alert.setContentText("No file choosen!!!");
            alert.show();
        }
    }

    public void choose_file(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null){
            cf_field.setText(selectedFile.getAbsolutePath());
        }else{
            System.out.println("File is not valid");
        }
    }

//    FileChooser fc = new FileChooser();



}
