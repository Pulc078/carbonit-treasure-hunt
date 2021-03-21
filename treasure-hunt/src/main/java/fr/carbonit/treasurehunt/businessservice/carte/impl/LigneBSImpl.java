package fr.carbonit.treasurehunt.businessservice.carte.impl;

import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.constantes.FichierConstante;
import fr.carbonit.treasurehunt.transverse.constantes.LignesConstantes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("fr.carbonit.treasurehunt.businessservice.carte.LigneBS.java")
public class LigneBSImpl   extends BaseBS implements LigneBS {

    private static final String splitOn = " - ";

    @Override
    public List<LigneBO> getLignes(List<String> lignes) throws TreasureHuntException {

        List<LigneBO> linesTypes = new ArrayList<>();
        for (String s : lignes) {
            linesTypes.add(getTypeFromString(s));
        }

        linesTypes.removeIf(Objects::isNull);
        return linesTypes;
    }


    private LigneBO getTypeFromString(String line) throws TreasureHuntException {
        LigneBO ligne = new LigneBO();
        ligne.setLigne(line);

        if (line.startsWith(FichierConstante.LINE_START_WITH_C)) {
            ligne.setType(LignesConstantes.MAP);
        } else if (line.startsWith(FichierConstante.LINE_START_WITH_M)) {
            ligne.setType(LignesConstantes.MONTAGNE);
        } else if (line.startsWith(FichierConstante.LINE_START_WITH_T)) {
            ligne.setType(LignesConstantes.TRESOR);
        } else if (line.startsWith(FichierConstante.LINE_START_WITH_A)) {
            ligne.setType(LignesConstantes.AVENTURIER);
        } else if (line.startsWith(FichierConstante.LINE_START_WITH_HASH)) {
            return null;
        } else {
            throw new TreasureHuntException("Impossible de définir le type de la ligne fournie : " + line);
        }
        return ligne;
    }

    @Override
    public boolean isLigneTypeMap(LigneBO ligne) {
        return ligne.getType().equals(LignesConstantes.MAP);
    }

    @Override
    public boolean isLigneTypeMontagne(LigneBO ligne) {
        return ligne.getType().equals(LignesConstantes.MONTAGNE);
    }

    @Override
    public boolean isLigneTypeTresor(LigneBO ligne) {
        return ligne.getType().equals(LignesConstantes.TRESOR);
    }

    @Override
    public boolean isLigneTypeAventurier(LigneBO ligne) {
        return ligne.getType().equals(LignesConstantes.AVENTURIER);
    }

    @Override
    public PositionBO trouverPosition(String ligne) {
        String[] split = ligne.split(splitOn);
        return new PositionBO(Integer.parseInt(split[2]), Integer.parseInt(split[1]));
    }

    @Override
    public PositionBO trouverPositionAventurier(String ligne) {
        String[] split = ligne.split(splitOn);
        return new PositionBO(Integer.parseInt(split[3]), Integer.parseInt(split[2]));
    }

    @Override
    public int setNombreTresors(String ligne) {
        String[] split = ligne.split(splitOn);
        return Integer.parseInt(split[3]);
    }

}
