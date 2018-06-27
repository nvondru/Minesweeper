/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author nvondru
 */
public class WinController implements Initializable {

    private Minesweeper controllerMain;
    
    @FXML
    private Label lblWinLoose;
    
    @FXML
    private Label lblMinesDetec;
    
    @FXML
    private Label lblMinesTotal;
    
    @FXML
    private Label lblPassedTime;
    
    public void setMainController(Minesweeper controllerMain){
        this.controllerMain = controllerMain;
    }
    
    public void setLblWinLoose(boolean winLoose){
        if(winLoose){
            lblWinLoose.setText("!! SIEG !!");
            lblWinLoose.setTextFill(Paint.valueOf("#16a400"));
        }
        else{
            lblWinLoose.setText("NIEDERLAGE");
            lblWinLoose.setTextFill(Paint.valueOf("#ff2200"));
        }
    }
    public void setLblMines(String minesDetec, String minesTotal){
        lblMinesDetec.setText(minesDetec);
        lblMinesTotal.setText(minesTotal);
        
    }
    public void setLblTime(String timePassed){
        lblPassedTime.setText(timePassed);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
    private void handleButtonRest(){
        controllerMain.getStageWin().close();
        controllerMain.getStagePlayGround().close();
        controllerMain.getControllerFieldSize().startGame();
    }
    @FXML
    private void handleButtonMenue(){
        controllerMain.getStageWin().close();
        controllerMain.getStagePlayGround().close();
        controllerMain.getStageStart().show();
    }
}
