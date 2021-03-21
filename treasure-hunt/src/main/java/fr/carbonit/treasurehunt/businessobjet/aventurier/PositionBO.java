package fr.carbonit.treasurehunt.businessobjet.aventurier;

import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;

public class PositionBO {


    private int positionY;
    private int positionX;

    public PositionBO(LigneBO ligne) {
        String[] contents = ligne.getLigne().split(" - ");
        this.positionY = Integer.parseInt(contents[2]);
        this.positionX = Integer.parseInt(contents[1]);
    }

    public PositionBO() {
    }

    public PositionBO(int positionY, int positionX) {
        this.positionY = positionY;
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
}
