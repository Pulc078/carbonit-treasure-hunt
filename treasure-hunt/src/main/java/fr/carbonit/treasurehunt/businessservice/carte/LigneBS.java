package fr.carbonit.treasurehunt.businessservice.carte;

import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;

import java.util.List;

public interface LigneBS {

    /**
     * Lis le fichier d'entré afin de déterminer les types de lignes fournis
     *
     * @param lignes : liste de lignes composant le fichier
     * @return : une liste d'objet LigneBO
     * @throws TreasureHuntException en cas d'erreur lors de la lecture
     */
    List<LigneBO> getLignes(List<String> lignes) throws TreasureHuntException;

    /**
     * @param ligne la ligne a tester
     * @return true si c'est une ligne de type MAP
     */
    boolean isLigneTypeMap(LigneBO ligne);

    /**
     * @param ligne la ligne a tester
     * @return true si c'est une ligne de type MONTAGNE
     */
    boolean isLigneTypeMontagne(LigneBO ligne);

    /**
     * @param ligne la ligne a tester
     * @return true si c'est une ligne de type TRESOR
     */
    boolean isLigneTypeTresor(LigneBO ligne);

    /**
     * @param ligne la ligne a tester
     * @return true si c'est une ligne de type AVENTURIER
     */
    boolean isLigneTypeAventurier(LigneBO ligne);

    /**
     * Remonte les coordonnées X et Y de la ligne
     *
     * @param ligne la ligne à lire
     * @return un objet avec les coordonnées
     */
    PositionBO trouverPosition(String ligne);

    /**
     * Remonte les coordonnées X et Y de l'aventurier de la ligne
     *
     * @param ligne la ligne à lire
     * @return un objet avec les coordonnées
     */
    PositionBO trouverPositionAventurier(String ligne);

    /**
     * Initialise le nombre de trésors pour les coordonées
     *
     * @param ligne : la ligne de trésors
     * @return : le nombre de trésors
     */
    int setNombreTresors(String ligne);
}
