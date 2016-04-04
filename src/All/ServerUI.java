package All;

//import Discovery.ScanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ServerUI {
    static ArrayList<String> all_ip_list;

    public static ObservableList<IP_List> ipList;

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
    public void SearchBtnAction() throws InterruptedException{
        ipList = FXCollections.observableArrayList();

        // Browse for bonjour service
        ScanService discover = new ScanService("Thread 1");
        Thread D1 = new Thread(discover);
        D1.start();
        Thread.sleep(500);
        D1.interrupt();

        // Find all ip
        FindIP name = new FindIP("Thread 2");
        Thread D2 = new Thread(name);
        D2.start();
        D2.sleep(500);
        D2.interrupt();

        System.out.println(all_ip_list);

        for (int i = 0; i < all_ip_list.size(); i++) {
            ipList.add(new IP_List(all_ip_list.get(i), "0"));
        }

        IPColumn.setCellValueFactory(new PropertyValueFactory("IPadr"));
        ProgressPercentColumn.setCellValueFactory(new PropertyValueFactory("percent"));
        ProgressTable.setItems(ipList);


    }

    public static class IP_List {
        private StringProperty IPadr;
        private StringProperty percent;


        private IP_List(String IPadr, String percent) {
            this.IPadr = new SimpleStringProperty(IPadr);
            this.percent = new SimpleStringProperty(percent);

        }

        public String getIPadr() {
            return IPadr.get();
        }

        public StringProperty IPadrProperty() {
            return IPadr;
        }

        public void setIPadr(String firstName) {
            this.IPadr.set(firstName);
        }

        public String getPercent() {
            return percent.get();
        }

        public StringProperty PercentProperty() {
            return percent;
        }

        public void setPercent(String surname) {
            this.percent.set(surname);
        }
    }

    public void StartBtnAction() {
        List<String> clientLst = new ArrayList<>();
        if(cf_field.getText().length() < 1) {
            ServerModel sm = new ServerModel(clientLst, new File(cf_field.getText()));
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
