package fr.carbonit.treasurehunt.businessservice.aventurier;

import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.MouvementBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;

import java.util.List;

public interface AventurierBS {


    /**
     * Retourne la liste d'aventurier donner en paramètre d'entrée
     *
     * @param ligneBOList : la liste de ligne du fichier d'entré
     * @return une liste d'aventurier
     */
    List<AventurierBO> getAventuriers(List<LigneBO> ligneBOList) throws TreasureHuntException;

    /**
     * Récupère tout les mouvements des aventuriers dans une même liste
     *
     * @param aventuriers la liste des aventuriers en jeux
     * @return la liste des mouvements de tout les aventuriers
     */
    List<MouvementBO> extraireToutLesMouvements(List<AventurierBO> aventuriers);

    /**
     * Mise à jours de l'orientation de l'aventurier tournant à gauche
     *
     * @param aventurier
     */
    void tourneAGauche(AventurierBO aventurier);

    /**
     * Mise à jours de l'orientation de l'aventurier tournant à droite
     *
     * @param aventurier
     */
    void tourneADroite(AventurierBO aventurier);

    /**
     * Trouve la prochaine position de l'aventurier
     *
     * @param aventurier : aventiruer en cours de mouvement
     * @return la prochaine position de l'aventurier
     */
    PositionBO getProchainePosition(AventurierBO aventurier);

    /**
     * On avance l'aventurier sur la carte
     * @param carte : carte en cours
     * @param aventurier : aventurier en mouvement
     */
    void avancerAventurier(CaseBO[][] carte, AventurierBO aventurier);
}
