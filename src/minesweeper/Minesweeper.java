/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Nicolas Vondru
 */
public class Minesweeper extends Application {
    // Variablen
    //---------------------------------------------------------------------------------
    Stage stageStart;
    Stage stageFieldSize = new Stage();
    Stage stagePlayGround = new Stage();
    Stage stageWin = new Stage();
    
    // Controller für Startfenster
    private StartController controllerStart;
    // Controller für Einstellungsfenster
    private FieldSizeController controllerFieldSize;
    // Controller für Spielfeld
    private PlayGroundController controllerPlayGround;
    // Controller für Winfenster
    private WinController controllerWin;
    //---------------------------------------------------------------------------------

   
    
    //Methoden
    //---------------------------------------------------------------------------------
    // Startcontroller wird dem Maincontroller bekanntgemacht
    public void initStartWindow(Stage stage){        
        try {
            stageStart = stage;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLStart.fxml"));
            Parent root = loader.load();
            controllerStart = loader.getController();
            controllerStart.setMainController(this);
            Scene scene = new Scene(root);
            stageStart.setScene(scene);
            stageStart.setTitle("Minesweeper modern");
            
            
            
            stageStart.show();
        } catch (IOException ex) {
            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Einstellungscontroller wird dem Maincontroller bekannt gemacht
     public void initFieldSizeWindow(){        
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML_FieldSize.fxml"));
            Parent root = loader.load();
            controllerFieldSize = loader.getController();
            controllerFieldSize.setMainController(this);
            Scene scene = new Scene(root);
            stageFieldSize.setScene(scene);
            stageFieldSize.setTitle("Minesweeper modern");
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     // Spielfeldcontroller wird dem Maincontroller bekanntgemacht
     public void initPlayGroundWindow(){        
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML_PlayGround.fxml"));
            Parent root = loader.load();
            controllerPlayGround = loader.getController();
            controllerPlayGround.setMainController(this);
            Scene scene = new Scene(root);
            stagePlayGround.setScene(scene);
            stagePlayGround.setResizable(false);
            stagePlayGround.setTitle("Minesweeper modern");
            
            
        } catch (IOException ex) {
            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void initWinWindow(){        
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXML_Win.fxml"));
            Parent root = loader.load();
            controllerWin = loader.getController();
            controllerWin.setMainController(this);
            Scene scene = new Scene(root);
            stageWin.setScene(scene);
            stageWin.setTitle("Minesweeper modern");
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Minesweeper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     
     public void showFieldSize(){
         stageStart.hide();
         stageFieldSize.show();
     }

     public void showPlayGround(){
         stageStart.hide();
         stagePlayGround.show();
     }
     public void showWin(){
         stageWin.show();
     }
    public Stage getStageWin(){
        return stageWin;
    }
    public Stage getStageStart() {
        return stageStart;
    }

    public Stage getStageFieldSize() {
        return stageFieldSize;
    }
     public Stage getStagePlayGround() {
        return stagePlayGround;
    }

    public StartController getControllerStart() {
        return controllerStart;
    }

    public FieldSizeController getControllerFieldSize() {
        return controllerFieldSize;
    }

    public PlayGroundController getControllerPlayGround() {
        return controllerPlayGround;
    }
    public WinController getWinController(){
        return controllerWin;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //---------------------------------------------------------------------------------
    
    
    //Programmeinstieg, wird beim Programmstart 1x ausgeführt
    //---------------------------------------------------------------------------------
     @Override
    public void start(Stage stage) throws Exception {
        initStartWindow(stage);
        initFieldSizeWindow();
        Image minePicture = new Image("/images/mine_modern_trans.png");
        stageStart.getIcons().add(minePicture);
        stageFieldSize.getIcons().add(minePicture);
        stagePlayGround.getIcons().add(minePicture);
        stageWin.getIcons().add(minePicture);
    }
    //---------------------------------------------------------------------------------
    protected void finalize() throws Throwable{
        try{
            System.out.println("We all get finalized() ...");
        }catch(Throwable t){
            throw t;
        }finally{
            System.out.println("Calling finalize of super Class");
            super.finalize();
        }
    }
        
}
