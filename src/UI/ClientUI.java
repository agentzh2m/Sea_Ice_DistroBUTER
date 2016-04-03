package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nattakarnklongyut on 4/3/16.
 */
public class ClientUI {
    @FXML
    Button start;

    @FXML
    Text filename, data_recv, total_data, percent, client_ip;

    @FXML
    TextArea textArea;

    ArrayList<Text> textlst;

    @FXML
    public void startbtnaction(){

        /* make the text color blue*/
        textlst = new ArrayList<>(Arrays.asList(filename, data_recv, total_data, percent, client_ip));
        for (int i=0; i<textlst.size(); i++){
            textlst.get(i).setFill(Color.web("#0076a3"));
        }

        client_ip.setText("127.0.0.0.1");
        filename.setText("Batman vs Superman Super HD.mp4");
        data_recv.setText("200 MB");
        total_data.setText("4.2 GB");
        percent.setText("13%");
    }

}
