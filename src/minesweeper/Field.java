/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

/**
 *
 * @author Nicolas Vondru
 */
public class Field {
    private boolean mineField;
    private boolean turned;
    private boolean flagged;
    private int coordX;
    private int coordY;
    private int neighs;
    private boolean hasUpperLine;
    private boolean hasUnderLine;
    private boolean isLeft;
    private boolean isRight;
    
    public Field(boolean mineField, boolean turned, boolean flagged, int coordX, int coordY) {
        this.mineField = mineField;
        this.turned = turned;
        this.flagged = flagged;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public boolean isFlagged() {
        return flagged;
    }
        
    public boolean isMineField() {
        return mineField;
    }

    public boolean isTurned() {
        return turned;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getNeighs(){
        return neighs;
    }

    public boolean isHasUpperLine() {
        return hasUpperLine;
    }

    public boolean isHasUnderLine() {
        return hasUnderLine;
    }

    public boolean isIsLeft() {
        return isLeft;
    }

    public boolean isIsRight() {
        return isRight;
    }
    
    
    public void setMineField(boolean mineField) {
        this.mineField = mineField;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
    
    public void setNeighs(int neighs){
        this.neighs = neighs;
    }
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setHasUpperLine(boolean hasUpperLine) {
        this.hasUpperLine = hasUpperLine;
    }

    public void setHasUnderLine(boolean hasUnderLine) {
        this.hasUnderLine = hasUnderLine;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public void setIsRight(boolean isRight) {
        this.isRight = isRight;
    }
    
    public void setLayout(boolean upperLine, boolean underLine, boolean right, boolean left){
        this.hasUpperLine = upperLine;
        this.hasUnderLine = underLine;
        this.isRight = right;
        this.isLeft = left;
    }

}
