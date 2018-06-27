/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.fxml.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Nicolas Vondru
 */
public class FieldSizeController implements Initializable {
    
    private Minesweeper controllerMain;
    
    @FXML
    private Button buttonBack;
    @FXML
    private Button buttonSave;
    
    @FXML
    private TextField fieldWidth;    
    @FXML
    private TextField fieldHeight;
    @FXML
    private TextField fieldMines;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            fieldHeight.setText("10");
            fieldWidth.setText("10");
            fieldMines.setText("20");
    }    
    
    
    public void setMainController(Minesweeper controllerMain){
        this.controllerMain = controllerMain;
    }
    
    @FXML
    private void handleButtonBack(){
        controllerMain.getStageFieldSize().hide();
        controllerMain.getStageStart().show();
    }
    @FXML
    private void handleButtonSave(){
        controllerMain.initPlayGroundWindow();
        int zaehlerX;
        int zaehlerY;
        int minesCount;
        
        zaehlerX = Integer.parseInt(fieldWidth.getText());
        zaehlerY = Integer.parseInt(fieldHeight.getText());
        minesCount = Integer.parseInt(fieldMines.getText());
        
//        System.out.println(zaehlerX);
//        System.out.println(zaehlerY);
        
        
        if(zaehlerX > 20 || zaehlerY > 15 || minesCount > zaehlerX * zaehlerY - 1 || zaehlerX < 4){
            
            if(zaehlerX > 20){
                fieldWidth.setText("20");
                fieldWidth.setBorder(new Border(new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            }
            if(zaehlerY > 15){
                fieldHeight.setText("15");
                fieldHeight.setBorder(new Border(new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
            }
            if(minesCount > zaehlerX * zaehlerY - 1 || minesCount > 300){
                fieldMines.setBorder(new Border(new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                fieldMines.setText(String.valueOf(Integer.valueOf(fieldHeight.getText()) * Integer.valueOf(fieldWidth.getText()) - 1));
            }
            if(zaehlerX < 4){
                fieldWidth.setBorder(new Border(new BorderStroke(Paint.valueOf("RED"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                fieldWidth.setText("4");
            }
        }
        else{
            fieldHeight.setBorder(Border.EMPTY);
            fieldWidth.setBorder(Border.EMPTY);
            fieldMines.setBorder(Border.EMPTY);
            
            controllerMain.getControllerPlayGround().createFields(zaehlerX, zaehlerY, minesCount);
            controllerMain.showPlayGround();
            controllerMain.stageFieldSize.hide();
            
           
        }
        
       
    }
    public void startGame(){
        handleButtonSave();
    }
    
}
