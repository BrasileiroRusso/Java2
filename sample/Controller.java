package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextArea messageList;
    @FXML
    private TextField messageTxt;
    @FXML
    private Button SendBtn;

    @FXML
    public void clickSendBtn(ActionEvent actionEvent) {
        sendMessage();
    }

    @FXML
    public void clickKey(ActionEvent actionEvent){
        sendMessage();
    }

    private void sendMessage(){
        if (!messageTxt.getText().isEmpty()){
            messageList.appendText(messageTxt.getText() + "\n");
            messageTxt.clear();
        }

        messageTxt.requestFocus();
    }
}
