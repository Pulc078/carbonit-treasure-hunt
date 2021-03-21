package fr.carbonit.treasurehunt.businessobjet.carte;

import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;

public class CaseBO {


    private int X;
    private int Y;
    private boolean isMontagne;
    private int tresors;
    private AventurierBO aventurier;

    public CaseBO(int x, int y) {
        X = x;
        Y = y;
        this.isMontagne = false;
        this.tresors = 0;
        this.aventurier = null;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public boolean isMontagne() {
        return isMontagne;
    }

    public void setMontagne(boolean montagne) {
        isMontagne = montagne;
    }

    public int getTresors() {
        return tresors;
    }

    public void setTresors(int tresors) {
        this.tresors = tresors;
    }

    public AventurierBO getAventurier() {
        return aventurier;
    }

    public void setAventurier(AventurierBO aventurier) {
        this.aventurier = aventurier;
    }
}
