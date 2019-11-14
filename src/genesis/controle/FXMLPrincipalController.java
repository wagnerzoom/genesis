/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genesis.controle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLPrincipalController implements Initializable {

    @FXML
    private Button btnPessoa;
    
    @FXML
    private Button btnProduto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.btnPessoa.setOnAction((event) -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/genesis/visao/FXMLPessoa.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnProduto.setOnAction((event) -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/genesis/visao/FXMLProduto.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
