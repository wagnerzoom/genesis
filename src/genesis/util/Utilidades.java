package genesis.util;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Utilidades {

    public static void mudarFoco(TextField textFieldOrigem, TextField textFieldDestino) {
        textFieldOrigem.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                textFieldDestino.requestFocus();
            }
        });
    }

    public static void formatarMaiusculasETamanho(TextField textFieldOrigem, int tamanho) {
        textFieldOrigem.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.length() <= tamanho) {
                textFieldOrigem.setText(newValue.toUpperCase());
            } else {
                textFieldOrigem.setText(oldValue);
            }
        });
    }

    public static void chamarMensagem(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Mensagem");
        alert.setContentText(mensagem);
        alert.setHeaderText(titulo);
        alert.show();
    }
}
