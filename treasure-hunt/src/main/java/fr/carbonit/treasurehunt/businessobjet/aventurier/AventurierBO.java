package fr.carbonit.treasurehunt.businessobjet.aventurier;

import fr.carbonit.treasurehunt.transverse.constantes.OrientationEnum;

import java.util.List;

public class AventurierBO {

    private String name;
    private PositionBO position;
    private OrientationEnum orientation;
    private List<MouvementBO> mouvements;
    private int tresors;

    public AventurierBO() {
    }

    public AventurierBO(String name,  PositionBO coordinates) {
        this.name = name;
        this.position = coordinates;
    }

    public AventurierBO(String name, PositionBO coordinates, OrientationEnum orientation,
                        List<MouvementBO> movements, int discoveredTreasures) {
        this.name = name;
        this.position = coordinates;
        this.orientation = orientation        ;
        this.mouvements = movements;
        this.tresors = discoveredTreasures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionBO getPosition() {
        return position;
    }

    public void setPosition(PositionBO position) {
        this.position = position;
    }

    public OrientationEnum getOrientation() {
        return orientation;
    }

    public void setOrientation(OrientationEnum orientation) {
        this.orientation = orientation;
    }

    public List<MouvementBO> getMouvements() {
        return mouvements;
    }

    public void setMouvements(List<MouvementBO> mouvements) {
        this.mouvements = mouvements;
    }

    public int getTresors() {
        return tresors;
    }

    public void setTresors(int tresors) {
        this.tresors = tresors;
    }
}
