package fr.carbonit.treasurehunt.transverse.constantes;

public enum OrientationEnum {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("O");

    private String value;

    OrientationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
