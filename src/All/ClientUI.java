package All;

import Client.ClientModel;
//import com.sun.codemodel.internal.fmt.JTextFile;
//import com.sun.glass.ui.Accessible;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
//import javafx.scene.input.KeyEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by nattakarnklongyut on 4/3/16.
 */
public class ClientUI {
    @FXML
    Button start, enter_name;

    @FXML
    Text filename, data_recv, total_data, percent, client_ip, client_name;

    @FXML
    TextArea textArea;

    @FXML
    TextField clientName_field;

    ArrayList<Text> textlst;

    @FXML
    public void startbtnaction(){
        ClientModel cm = new ClientModel();

        /* make the text color blue*/
        textlst = new ArrayList<>(Arrays.asList(filename, data_recv, total_data, percent, client_ip));
        for (int i=0; i<textlst.size(); i++){
            textlst.get(i).setFill(Color.web("#0076a3"));
        }

        /* get client's ip */
        try {
            client_ip.setText(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        filename.setText(ClientModel.filename);
        data_recv.setText("200 MB");
        total_data.setText("4.2 GB");
        percent.setText("13%");
    }

    /* enter the client's name */
    public void enter_name(){

        if (client_name.getText() != ""){
            //insert error popup box if user alr put in the name
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("");
            alert.setContentText("You have already enter your name.");
            alert.show();
        }else {
            client_name.setText(clientName_field.getText());
            client_name.setFill(Color.web("#0076a3"));
            clientName_field.clear();
        }



    }

}
