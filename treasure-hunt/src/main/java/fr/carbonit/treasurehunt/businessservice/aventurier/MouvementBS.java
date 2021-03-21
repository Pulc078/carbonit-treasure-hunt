package fr.carbonit.treasurehunt.businessservice.aventurier;

import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;

import java.util.List;

public interface MouvementBS {
    /**
     * Résolution des déplacements des aventuriers
     *
     * @param carte : carte sur lequel se déplace les aventuriers
     * @param aventurier la liste d'aventurier à déplacer
     */
    void deplacerAventuriers(CaseBO[][] carte, List<AventurierBO> aventurier, int counter);
}
