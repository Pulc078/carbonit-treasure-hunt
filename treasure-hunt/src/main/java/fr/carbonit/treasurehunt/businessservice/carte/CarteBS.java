package fr.carbonit.treasurehunt.businessservice.carte;

import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;

import java.util.List;

public interface CarteBS {

    /**
     * Permet de créér la carte à partir des lignes données en entré
     *
     * @param lignes : Liste de lignes issue de la lecture du fichier d'entrée
     * @return la carte sous forme de tableau 2D
     */
    CaseBO[][] creerCarte(List<LigneBO> lignes);

    /**
     * Place les aventurier sur leur point de départ
     *
     * @param carte : carte sur lequel placer les aventurier
     * @param aventurier aventurier à placer
     */
    void placerAventurier(CaseBO[][] carte, List<AventurierBO> aventurier) throws TreasureHuntException;

    /**
     * remonte les dimensions de la carte
     * @param carte
     * @return
     */
    PositionBO trouverDimension(CaseBO[][] carte);


    /**
     * @param dimension : dimensions de la carte
     * @param prochainePosition : position à tester
     * @return true si la position est dans les dimension de la carte
     */
    boolean isInsideMap(PositionBO dimension, PositionBO prochainePosition);

    /**
     * @param prochaineCase : case à tester
     * @return true si la prochaine case est accesible pour l'aventurier
     */
    boolean estCaseAccessible(CaseBO prochaineCase);

    /**
     * @param carte sur lequel on cherche la position
     * @param position : position fournis
     * @return la carte en fonction des coordonnées
     */
    CaseBO trouverCase(CaseBO[][] carte, PositionBO position);
}
