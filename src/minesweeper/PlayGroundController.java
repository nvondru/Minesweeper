/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;



/**
 * FXML Controller class
 *
 * @author Nicolas Vondru
 */
public class PlayGroundController implements Initializable {
    
    private Minesweeper controllerMain;
    
    private ArrayList<Field> listFields = new ArrayList<Field>();
    
    private int x = 17;    
    private int y = 15;
    private int secondsPassed = 0;
    private int winCounter;
    private boolean winLoose = true;
    private int flagCounter = 0;
    private int defFlagCounter;
    
    Timer someTimer = new Timer();
        TimerTask someTask = new TimerTask(){
            public void run(){
                
                Platform.runLater(() -> {
                    secondsPassed ++;
                    counterTime.setText(String.valueOf(secondsPassed));
            });
            }
        };
    
    
    private boolean gameHasStarted = false;
    
    @FXML
    private AnchorPane panePlayGround;
    
    @FXML
    private BorderPane playWidow;
    @FXML
    private Label counterTime;
    @FXML
    private Label counterMines;
    
    
    
    // Maincontroller wird dem Playground-Controller bekannt gemacht
//     wird beim Programmstart einmalig ausgführt
    public void setMainController(Minesweeper controllerMain){
        this.controllerMain = controllerMain;
        
    }
    
//  Objekte der Klasse "Field" werden erstellt und in einem Array gespeichert, danach werden für jedes Objekt "Field" 
//  ein Label mit entsprechendem Text und ein GUI-Feld auf dem Spielfeld mit den korrekten Koordinaten erstellt.    
    public void createFields(int zaehlerX, int zaehlerY, int minesCount){
           
        
        // Variablen werden deklariert und initialisiert 
        int counterRow = 0;
        int counterCol = 1; 
        int baseX = 17;
        int baseY = 15;
        int baseCounter = 0;
//        flagCounter = 0;
        
        
        
//        panePlayGround.setMinSize(zaehlerX * 58 + 40, zaehlerY * 58 + 100);
        
        winCounter = zaehlerX * zaehlerY - minesCount;

        panePlayGround.setPrefWidth(zaehlerX * 58);
        panePlayGround.setPrefHeight(zaehlerY * 58);
        
        counterMines.setText(String.valueOf(minesCount));
        
        
        
        // Objekte der Klasse "Field" werden erstellt
        // X- & Y-Koordinaten werden durch loops korrekt gesetzt/verändert
        while(counterRow < zaehlerY){
            
            createListField();
            
            while(counterCol < zaehlerX){
                
                x += 58;
                createListField();
                counterCol += 1;
                if(counterCol == zaehlerX ){
                   
                    x = baseX;
                }
            }
            
            counterCol = 1;            
            y += 58;
            counterRow += 1;
        }
        

        
//        Für jdes Objekt "Field" wird ein entsprechendes GUI-Element (Rectangle) erstellt 
        int idPos = 0;
        for(Field tempField : listFields){        
            createField(tempField.getCoordX(), tempField.getCoordY(), tempField.isMineField(), zaehlerX, zaehlerY, minesCount, idPos);
            
               
            
            idPos ++;
        }     
        

//        createButtonBack();
        
    }
    
//  Funktion des Button "btnBack"
    @FXML
    private void handleButtonBack(){
        controllerMain.stagePlayGround.hide();
        controllerMain.stageStart.show();
    }
    
//  Funktion zur Bestimmung des Texts eines Labels (Text des Labels = Anzahl Minen um das zugehörige Feld)
//  Mit ermitteltem Text wird eine separate Funktion "createLabel()" aufgerufen
//  Parameterverwendung:
//  tempField -> Anzahl benachbarter Minenfelder wird gesetzt // zaehlerX -> wird zur Ermittlung benachbarter Felder verwendet // pos -> Index des aktuellen Objekts "Field"
    public void createLabelNum(Field tempField, int zaehlerX, int pos){
        
//       werden für jedes "Field" neu bestimmt und geben an, ob sich das Feld mittig, oder an einem Rand befindet
         boolean hasUpperLine, isLeft, isRight, hasUnderLine;
            
//          Anzahl benachbarter Minenfelder (wird bei jeder Zutreffenden if-Abfrage um 1 erhöht
            int countNeigh = 0; 
            
            
            
            // Es wird überprüft ob das Feld über dem aktuellen, innerhalb des Spielfeldes liegt
            if(pos - zaehlerX >= 0){
                hasUpperLine = true;
                
                //Wenn das Feld überhalb des aktuellen eine Mine ist -> counter wird erhöht
                if(listFields.get(pos - zaehlerX).isMineField()){
                        countNeigh ++;
                }                     
            }
            else{
                hasUpperLine = false;
                System.out.println("Index kleiner 0");
            }
            
            // Es wird überprüft, ob das Feld unterhalb des aktuellen, innerhalb des Spielfelds liegt
            if(pos + zaehlerX <= listFields.size() - 1){
                hasUnderLine = true;
                 // Wenn das Feld unterhalb des aktuellen eine Mine ist -> counter wird erhöht
                if(listFields.get(pos  + zaehlerX).isMineField()){
                       countNeigh ++;
                }           
            }
            else{  
                hasUnderLine = false;
               System.out.println("Index grösser listSize");
            }
            // Es wird überprüft, ob das Feld links des aktuellen, innerhalb des Spielfelds liegt
            if((pos) % zaehlerX != 0){
                isLeft = false;
                // Wenn das Feld links des aktuellen eine Mine ist -> counter wird erhöht
                if(listFields.get(pos -1).isMineField()){
                    countNeigh ++;
                }                
            }
            else{
                    isLeft = true;
                    System.out.println("Dieses Feld ist ganz Links");
            }
            // Es wird überprüft, ob das Feld rechts des aktuellen innerhalb des Spielfelds liegt
            if((pos + 1) % zaehlerX != 0){
                isRight = false;
                // Wenn das Feld rechts des aktuellen eine Mine ist -> counter wird erhöht
                if(listFields.get(pos + 1).isMineField()){
                    countNeigh ++;
                }                
            }
            else{
                    isRight = true;
                    System.out.println("Dieses Feld ist ganz Rechts");
            }
            listFields.get(pos).setLayout(hasUpperLine, hasUnderLine, isRight, isLeft);
//          Es wird überprüft, ob das Feld am oberen und/oder am rechten Rand des Spielfelds liegt
            if(hasUpperLine &! isRight){
//              Wenn das Feld rechts/oberhalb des aktuellen ein Minenfeld ist -> counter wird erhöht
                if(listFields.get(pos - (zaehlerX - 1)).isMineField()){
                    countNeigh ++;
                }
            }
//          Es wird überprüft, ob das Feld am oberen und/oder am linken Rand des Spielfelds liegt
            if(hasUpperLine &! isLeft){
                
//              Wenn das Feld links/oberhalb des aktuellen ein Minenfeld ist -> counter wird erhöht
                if(listFields.get(pos - (zaehlerX + 1)).isMineField()){
                    countNeigh ++;
                }
            }
  
//          Es wird überprüft, ob das Feld am unteren und/oder am rechten Rand des Spielfelds liegt
            if(hasUnderLine &! isRight){
                
//              Wenn das Feld rechts/unterhalb des aktuellen ein Minenfeld ist -> counter wird erhöht
                if(listFields.get(pos + (zaehlerX + 1)).isMineField()){
                    countNeigh ++;
                }
            }
            
//          Es wird überprüft, ob das Feld am unteren und/oder am linken Rand des Spielfelds liegt
            if(hasUnderLine &! isLeft){
//              Wenn das Feld links/unterhalb des aktuellen ein Minenfeld ist -> counter wird erhöht
                if(listFields.get(pos + (zaehlerX - 1)).isMineField()){
                    countNeigh ++;
                }
            }
//          countNeigh wird in String umgewandelt
            String letter = Integer.toString(countNeigh);
            
//          Für das aktuelle Objekt "Field" wird das Attribut "neighs" mit dem ermittelten Wert gesetzt
            tempField.setNeighs(countNeigh);
            
//          Es wird ein Label mit den korrekten X/Y Koordinaten, und korrektem Label erstellt
            createLabel(tempField.getCoordX(), tempField.getCoordY(), letter);
            
                        
    }
    
//  Funktion zur Erstellung eines Objekts "Field" 
    public void createListField(){
//      Konstruktoraufruf
        Field tempField = new Field(false, false, false, x + 7, y +7);
//      Objekt wird dem Array hinzgefügt
        listFields.add(tempField);
    }
    
//  Funktion zur Erstellung eines GUI-Elements Rectangle (stellt ein Feld auf dem Spielfeld dar)
//  Parameterverwendung:
//  xVal -> setzen des X-Layouts // yVal -> setzen des Y-Layouts // isMine -> Abfrage bei onClick() // zaehlerX -> Ermittlung benachbarter Felder // idPos -> Index des aktuellen Objekts "Field"    
     public void createField(int xVal, int yVal, boolean isMine, int zaehlerX, int zaehlerY, int minesCount, int idPos){
     
        
        // Rectangle werden initialisiert
        Rectangle tempRectangleOuts = new Rectangle();
        Rectangle tempRectangleIns = new Rectangle();
        
        // Layout des Rectangle-Outs wird gesetzt
        tempRectangleOuts.setId(Integer.toString(idPos) + "Rectangle");
        tempRectangleOuts.setArcHeight(12);
        tempRectangleOuts.setArcWidth(12);
        tempRectangleOuts.setWidth(50);
        tempRectangleOuts.setHeight(50);
        tempRectangleOuts.setLayoutX(xVal - 7);
        tempRectangleOuts.setLayoutY(yVal - 7);
        tempRectangleOuts.setStrokeWidth(4);
        tempRectangleOuts.setStrokeType(StrokeType.OUTSIDE);
        tempRectangleOuts.setFill(Paint.valueOf("#4da0ee"));
        tempRectangleOuts.setOpacity(0.8);
        tempRectangleOuts.setStroke(Paint.valueOf("WHITE"));

        // Layout des Rectangle-Ins wird gesetzt
        tempRectangleIns.setId(Integer.toString(idPos));
        tempRectangleIns.setArcHeight(12);
        tempRectangleIns.setArcWidth(12);
        tempRectangleIns.setWidth(36);
        tempRectangleIns.setHeight(36);
        tempRectangleIns.setLayoutX(xVal);
        tempRectangleIns.setLayoutY(yVal);
        tempRectangleIns.setStrokeWidth(4);
        tempRectangleIns.setStrokeType(StrokeType.OUTSIDE);
        tempRectangleIns.setFill(Paint.valueOf("DODGERBLUE"));
        tempRectangleIns.setStroke(Paint.valueOf("WHITE"));
        
        // onClick-Methode für Rectangle-Ins
        tempRectangleIns.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            
            // Bei Linksklick
            if(e.isPrimaryButtonDown()){
                System.out.println(tempRectangleIns.getId() + ". Field was left-clicked");
                if(gameHasStarted){
                    for(Field tempField : listFields){
                    // angeklicktes Feld wird im Array gesucht (Abfrage auf X/Y Koordinaten)
                    if(tempField.getCoordX() == tempRectangleIns.getLayoutX() && tempField.getCoordY() == tempRectangleIns.getLayoutY()){
                        //Überprüfung, ob Feld bereits geflaggt wurde
                        if(tempField.isFlagged() != true){
                            
                            // Überprüfung, ob angeklicktes Feld ein Minenfeld ist
                            if(listFields.get(idPos).isMineField()){
                            
                                
                                someTimer.cancel();
                                someTask.cancel();
                                System.out.println("Momentaner FlagCounter: " + flagCounter);
                                defFlagCounter = flagCounter;
                                // Timer wird resetted
                                
                                
                                if(winLoose){
                                    winLoose = false;
                                    constructWinWindow(minesCount);
                                }
                                
                                secondsPassed = 0;
                                
                              
                            
                                tempField.setTurned(true);
                                // Feld wird als Minenfeld gezeichnet
                                tempRectangleOuts.setFill(Paint.valueOf("RED"));
                                tempRectangleOuts.setOpacity(1);
                                tempRectangleIns.setOpacity(0.2);
                                tempRectangleIns.setFill(Paint.valueOf("BLACK"));
                                int posMine = 0;
                            
                            // Auf jedes Minenfeld wird ein Klick generiert
                            for(Field tempMineField : listFields){
                                if(tempMineField.isMineField() &! tempMineField.isTurned()){
                                    
                           
                                    for(Node mineNode : panePlayGround.getChildren()){
                                        if(String.valueOf(posMine).equals(mineNode.getId())){
                                            
                                             
                                            Event.fireEvent(mineNode, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                true, true, true, true, true, true, null));
                                        }
                                        
                                        
                                    }
                                    
                                }   
                                    
                                    posMine ++;
                            }                            
                            
                            

                            // Alle Elemente ausser "btnBack" ignorieren nun Klicks
                            for(Node a : panePlayGround.getChildren()){
                                if(a.getId().contains("btnBack") != true || a.getId().contains("btnRest") != true){
                                    a.setMouseTransparent(true);
                                }

                            }
                            }
                            // Wenn das Feld kein Minenfeld ist
                            else{
                                // Feld wird optisch aufgedeckt
                                
                                winCounter --;
                                
                                tempRectangleOuts.setFill(Paint.valueOf("#dadfe8"));
                                tempRectangleOuts.setOpacity(0.5);
                                tempRectangleIns.setOpacity(0.2);
                                tempRectangleIns.setFill(Paint.valueOf("BLACK"));
                                tempRectangleIns.setStrokeWidth(0);
                                
                                int pos = 0;
                                // Es werden die Felder ermittelt, die um das Angeklickte herum liegen, und je nach Staus der Felder ein Klick darauf generiert
                                for(Field tempField_2 : listFields){

                                    //Das angeklickte Feld wird ermittelt und der Status auf "turned" gesetzt
                                    if(tempField_2.getCoordX() == tempRectangleIns.getLayoutX() && tempField_2.getCoordY() == tempRectangleIns.getLayoutY()){
                                        tempField_2.setTurned(true);

                                        // Es werden nur dann Felder in der Umgebung gesucht, wenn das aktuelle keine Minenfelder als Nachbarn hat
                                        if(tempField_2.getNeighs() < 1){
                                            
                                            boolean hasUpperLine, isLeft, isRight, hasUnderLine;

                                            // Es wird überprüft ob das Feld über dem aktuellen, innerhalb des Spielfeldes liegt
                                            if(pos - zaehlerX >= 0){
                                                hasUpperLine = true;
                                                

                                                //  Es wird überprüft, ob das Feld oberhalb des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos - zaehlerX).isMineField() == false && listFields.get(pos - zaehlerX).isTurned() == false){

                                                    // wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        //Es wird ein Click auf das Feld oberhalb simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) - zaehlerX))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                hasUpperLine = false;
                                                
                                                //                        System.out.println("Index kleiner 0");
                                            }
                                            // Es wird überprüft, ob das Feld unterhalb des aktuellen, innerhalb des Spielfelds liegt
                                            if(pos + zaehlerX < listFields.size() - 1){
                                                hasUnderLine = true;
                                                

                                                //  Es wird überprüft, ob das Feld unterhalb des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos + zaehlerX).isMineField() == false && listFields.get(pos + zaehlerX).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        //Es wird ein Click auf das Feld unterhalb simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) + zaehlerX))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                hasUnderLine = false;
                                                
                                                //                       System.out.println("Index grösser listSize");
                                            }
                                            // Es wird überprüft, ob das Feld links des aktuellen, innerhalb des Spielfelds liegt
                                            if((pos) % zaehlerX != 0){
                                                isLeft = false;
                                                
                                                //  Es wird überprüft, ob das Feld links des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos - 1).isMineField() == false && listFields.get(pos - 1).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        // Es wird ein Click auf das Feld links simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) - 1))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                isLeft = true;
                                                
                                                //                            System.out.println("Dieses Feld ist ganz Links");
                                            }
                                            // Es wird überprüft, ob das Feld rechts des aktuellen innerhalb des Spielfelds liegt
                                            if((pos + 1) % zaehlerX != 0){
                                                isRight = false;
                                                

                                                //  Es wird überprüft, ob das Feld rechts des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos + 1).isMineField() == false && listFields.get(pos + 1).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        // Es wird ein Click auf das Feld rechts simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) + 1))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            else{
                                                isRight = true;
                                                
                                                // System.out.println("Dieses Feld ist ganz Rechts");
                                            }

                                            if(hasUpperLine &! isRight){
                                                //  Es wird überprüft, ob das Feld oben/rechts des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos - (zaehlerX - 1)).isMineField() == false && listFields.get(pos - (zaehlerX - 1)).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        //  Es wird ein Click auf das Feld oben/rechts simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) - (zaehlerX - 1)))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            if(hasUpperLine &! isLeft){
                                                //  Es wird überprüft, ob das Feld oben/links des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos - (zaehlerX + 1)).isMineField() == false && listFields.get(pos - (zaehlerX + 1)).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        // Es wird ein Click auf das Feld oben/links simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) - (zaehlerX + 1)))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }


                                            if(hasUnderLine &! isRight){
                                               //  Es wird überprüft, ob das Feld unten/rechts des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos + (zaehlerX + 1)).isMineField() == false && listFields.get(pos + (zaehlerX + 1)).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        // Es wird ein Click auf das Feld unten/rechts simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) + (zaehlerX + 1)))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                            if(hasUnderLine &! isLeft){
                                                //  Es wird überprüft, ob das Feld unten/links des Angeklickten ein Minenfeld ist und ob es bereits gedreht wurde
                                                if(listFields.get(pos + (zaehlerX - 1)).isMineField() == false && listFields.get(pos + (zaehlerX - 1)).isTurned() == false){

                                                    //  wenn beides nicht zutrifft, wird das entsprechende GUI-Element gesucht
                                                    for(Node a : panePlayGround.getChildren()){

                                                        // Es wird ein Click auf das Feld unten/links simuliert
                                                        if(a.getId().equals(String.valueOf(Integer.valueOf(tempRectangleIns.getId()) + (zaehlerX - 1)))){
                                                            Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                                                        }
                                                    }
                                                }
                                            }
                                        }                    
                                    }
                                    pos ++;

                                }                            
                            }
                        }
                    }
                        if(winCounter == 0){
                            
                            someTimer.cancel();
                            someTask.cancel();
                            defFlagCounter = flagCounter;
                            if(winLoose){
                                constructWinWindow(minesCount);
                                winLoose = false;
                            }
                            
                            secondsPassed = 0;
                            for(Node a : panePlayGround.getChildren()){
                                if(a.getId().contains("btnBack") != true || a.getId().contains("btnRest") != true){
                                    a.setMouseTransparent(true);
                                }

                            }
                        }
                    }   
                }else{
                    
            
                    int posStart = 0;
                    for(Field tempFieldStart : listFields){
                        if(tempFieldStart.getCoordX() == tempRectangleIns.getLayoutX() && tempFieldStart.getCoordY() == tempRectangleIns.getLayoutY()){
                           // Die Minenfelder werden zufällig bestimmt und bei den entsprechenden Objekten das Attribut "isMineField" auf "true" gesetzt
                            createMineFields(zaehlerX, zaehlerY, minesCount, posStart);
                        }
                        posStart ++;
                    }
                    int posSout = 0;
                    for(Field tempFieldSout : listFields){
                        System.out.println(posSout + ". Feld is Mine: " + tempFieldSout.isMineField());
                        posSout ++;
                    }
                    
                    gameHasStarted = true;
                    
                    someTimer.scheduleAtFixedRate(someTask, 1000, 1000);

                    //      Für jedes Objekt "Field" wird ein Label erstellt, welches anzeigt, wieviele Minenfelder um das aktuelle Feld herum liegen
                    
                    int pos = 0;        
                    for(Field tempField : listFields){
                        
                        createLabelNum(tempField, zaehlerX, pos);
                        pos ++;
                    }
                    
                    int posi = 0;
                    for(Field turnField : listFields){
                       if(turnField.getCoordX() == tempRectangleIns.getLayoutX() && turnField.getCoordY() == tempRectangleIns.getLayoutY()){
                           for(Node a : panePlayGround.getChildren()){
                               if(a.getId() == tempRectangleIns.getId()){
                                   Event.fireEvent(a, new MouseEvent(MouseEvent.MOUSE_PRESSED, 0,
                                                                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                                                                    true, true, true, true, true, true, null));
                               }
                           }
                       }
                       posi ++;
                    }
                    
                }
                           
            }
            // Bei Rechtsklick
            if(e.isSecondaryButtonDown()){
                System.out.println(tempRectangleIns.getId() + ". Field has got right-clicked");                
               
                
                
                for(Field tempField : listFields){                
                    //Das angeklickte Feld wird ermittelt und der Status gewechselt
                    if(tempField.isTurned() != true){
                        if(tempField.getCoordX() == tempRectangleIns.getLayoutX() && tempField.getCoordY() == tempRectangleIns.getLayoutY()){
                        
                        // Wenn Feld ungeflagged ist, wird Status auf "flagged" gewechselt
                        if(tempField.isFlagged() != true){
                            if(tempField.isMineField()){
                                flagCounter ++;
                            }
                            
                            tempField.setFlagged(true);
                            tempRectangleOuts.setFill(Paint.valueOf("Black"));
                            tempRectangleIns.setFill(Paint.valueOf("DIMGRAY"));
                            
                            counterMines.setText(String.valueOf(Integer.valueOf(counterMines.getText()) - 1));
                        }
                        // Wenn Feld geflagged ist, wird Status auf "unflagged" gewechselt
                        else{
                            if(tempField.isMineField()){
                                flagCounter --;
                            }
                            tempField.setFlagged(false);
                            tempRectangleOuts.setFill(Paint.valueOf("#4da0ee"));
                            tempRectangleIns.setFill(Paint.valueOf("DODGERBLUE"));
                            counterMines.setText(String.valueOf(Integer.valueOf(counterMines.getText()) + 1));
                        }      
                            System.out.println(flagCounter);
                        
                    }
                    }
                    
                }                
            }
        });  
        // Rectangle wird dem Pane hinzugefügt
        panePlayGround.getChildren().addAll(tempRectangleOuts, tempRectangleIns);
    }
    
//    Task<Void> sleeper = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                }
//                return null;
//            }
//        };
    
    // Funktion zur Erstellung des "btnBack"
//    public void createButtonBack(){
//        
//        Button buttonBack = new Button();
//        buttonBack.setText("Zurück");
//        buttonBack.setId("btnBack");
//        
//        buttonBack.setOnAction((event) -> {
//            controllerMain.stagePlayGround.hide();
//            controllerMain.stageStart.show();
//        });
//        buttonBack.setLayoutY(y + 30);
//        buttonBack.setLayoutX(panePlayGround.getPrefWidth() / 2 - 30);
//        panePlayGround.getChildren().add(buttonBack);
//    }
    
    //Funktion zur Bestimmung der Minenfelder
    
    public void createMineFields(int zaehlerX, int zaehlerY, int minesCount, int posClicked){
        
        for(int z = 0; z < minesCount; z ++){
            
            Random randomChoice = new Random();
            int nextMine = randomChoice.nextInt((zaehlerX*zaehlerY));
          
            System.out.println(z + ". Mine is Field: " + nextMine + " This Field is already mine: " + listFields.get(nextMine).isMineField());
            Field actualField = listFields.get(nextMine);
            if(actualField.isMineField() || nextMine == posClicked){
                z -= 1;
            }
            else
            {
                actualField.setMineField(true);                
            }
        }
    }
    
    // Funktion zur Erstellung eines Labels mit übergebenem Text und x/y Werten
    public void createLabel(int xVal, int yVal, String letterVal){
        
        Label tempLabel = new Label();
        String id = String.valueOf(xVal) + String.valueOf(yVal);
        
        tempLabel.setId(id);
        
        

        
        
        tempLabel.setFont(Font.font("Ancient", 25));
        
       
        if(Integer.parseInt(letterVal) > 0){
            tempLabel.setText(letterVal);
            tempLabel.setTextFill(Paint.valueOf("BLUE"));
            tempLabel.setLayoutX(xVal + 14);
            tempLabel.setLayoutY(yVal + 2);
            // Farbe der Schrift wird je nach Text bestimmt
            if(Integer.parseInt(letterVal) > 1){
                tempLabel.setTextFill(Paint.valueOf("green"));
                tempLabel.setLayoutX(xVal + 12);
                if(Integer.parseInt(letterVal) > 2){
                    tempLabel.setTextFill(Paint.valueOf("BLACK"));
                    if(Integer.parseInt(letterVal) > 3){
                        tempLabel.setTextFill(Paint.valueOf("orange"));
                        if(Integer.parseInt(letterVal) > 4){
                            tempLabel.setTextFill(Paint.valueOf("red"));
                            if(Integer.parseInt(letterVal) > 5){
                                tempLabel.setTextFill(Paint.valueOf("#710a93"));
                                    if(Integer.parseInt(letterVal) > 6){
                                        tempLabel.setTextFill(Paint.valueOf("#8bd343"));
                                            if(Integer.parseInt(letterVal) > 7){
                                                tempLabel.setTextFill(Paint.valueOf("black"));                                
                                            } 
                                    }                                
                            }
                            
                        }
                    }
                }
            }
        }
        
        
        
        panePlayGround.getChildren().add(0, tempLabel);
    }
    @FXML
    private void handleButtonRestart(){
        controllerMain.stagePlayGround.close();
        controllerMain.getControllerFieldSize().startGame();
    }
    public void constructWinWindow(int minesCount){
        controllerMain.initWinWindow();
        controllerMain.getWinController().setLblWinLoose(winLoose);
        controllerMain.getWinController().setLblTime(counterTime.getText());
        controllerMain.getWinController().setLblMines(String.valueOf(defFlagCounter), String.valueOf(minesCount));
  
        controllerMain.getStageWin().show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
