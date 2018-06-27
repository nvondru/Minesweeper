/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Nicolas Vondru
 */
public class StartController implements Initializable {
    
//    Deklaration Variablen
    private Minesweeper controllerMain;
    
    @FXML
    private Label label;
    
   
//    Deklaration Methoden
    @FXML
    private void  handleLinkSetFieldSize(){
        controllerMain.showFieldSize();
    }
    @FXML
    private void handleLinkPlay(){
        controllerMain.getControllerFieldSize().startGame();
        
    }
    
    
    public void setMainController(Minesweeper controllerMain){
        this.controllerMain = controllerMain;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
