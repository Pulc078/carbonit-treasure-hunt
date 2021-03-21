package fr.carbonit.treasurehunt.businessobjet.carte;

public class LigneBO {

    private String ligne;
    private String type;

    public LigneBO(String line, String lineType) {
        this.ligne = line;
        this.type = lineType;
    }

    public LigneBO() {
    }

    public String getLigne() {
        return ligne;
    }

    public void setLigne(String ligne) {
        this.ligne = ligne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


