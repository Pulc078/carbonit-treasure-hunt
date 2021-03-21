package fr.carbonit.treasurehunt.businessservice.aventurier.impl;

import fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS;
import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.MouvementBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.constantes.ActionEnum;
import fr.carbonit.treasurehunt.transverse.constantes.FichierConstante;
import fr.carbonit.treasurehunt.transverse.constantes.OrientationEnum;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS")
public class AventurierBSImpl extends BaseBS implements AventurierBS {

    public static final Logger log = LogManager.getLogger(AventurierBSImpl.class);
    private final CarteBS carteBS = getBS(CarteBS.class);
    LigneBS ligneBS = getBS(LigneBS.class);

    @Override
    public List<AventurierBO> getAventuriers(List<LigneBO> ligneBOList) throws TreasureHuntException {
        List<AventurierBO> aventuriers = new ArrayList<>();

        for (LigneBO ligne : ligneBOList) {
            if (ligneBS.isLigneTypeAventurier(ligne)) {
                AventurierBO aventurierBO = getAventurier(ligne);
                aventuriers.add(aventurierBO);
            }
        }

        if (aventuriers.isEmpty()) {
            throw new TreasureHuntException("Aucun aventurier n'a été trouvé dans le fichier donné. Impossible de lancer le programme !");
        }
        return aventuriers;
    }

    @Override
    public List<MouvementBO> extraireToutLesMouvements(List<AventurierBO> aventuriers) {
        return aventuriers.stream()
                .flatMap(adventurer -> adventurer.getMouvements().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void tourneAGauche(AventurierBO aventurier) {
        switch (aventurier.getOrientation()) {
            case NORTH:
                log.info("{} regarde le nord. Il tourne a gauche et regarde maintenant l'ouest", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.WEST);
                break;
            case SOUTH:
                log.info("{} regarde le sud. Il tourne a gauche et regarde maintenant l'est", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.EAST);
                break;
            case EAST:
                log.info("{} regarde l'est. Il tourne a gauche et regarde maintenant le nord", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.NORTH);
                break;
            case WEST:
                log.info("{} regarde l'ouest. Il tourne a gauche et regarde maintenant le sud", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.SOUTH);
                break;
        }
    }

    @Override
    public void tourneADroite(AventurierBO aventurier) {
        switch (aventurier.getOrientation()) {
            case NORTH:
                log.info("{} regarde le nord. Il tourne a droite et regarde maintenant l'est", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.EAST);
                break;
            case SOUTH:
                log.info("{} regarde le sud. Il tourne a droite et regarde maintenant l'ouest", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.WEST);
                break;
            case EAST:
                log.info("{} regarde l'est. Il tourne a droite et regarde maintenant le sud", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.SOUTH);
                break;
            case WEST:
                log.info("{} regarde l'ouest. Il tourne a droite et regarde maintenant le nord", aventurier.getName());
                aventurier.setOrientation(OrientationEnum.NORTH);
                break;
        }
    }

    @Override
    public PositionBO getProchainePosition(AventurierBO aventurier) {
        PositionBO prochainePosition = new PositionBO();
        PositionBO positionActuelle = aventurier.getPosition();
        switch (aventurier.getOrientation()) {
            case NORTH:
                prochainePosition.setPositionY(positionActuelle.getPositionY() - 1);
                prochainePosition.setPositionX(positionActuelle.getPositionX());
                break;
            case SOUTH:
                prochainePosition.setPositionY(positionActuelle.getPositionY() + 1);
                prochainePosition.setPositionX(positionActuelle.getPositionX());
                break;
            case EAST:
                prochainePosition.setPositionY(positionActuelle.getPositionY());
                prochainePosition.setPositionX(positionActuelle.getPositionX() + 1);
                break;
            case WEST:
                prochainePosition.setPositionY(positionActuelle.getPositionY());
                prochainePosition.setPositionX(positionActuelle.getPositionX() - 1);
                break;
        }
        return prochainePosition;
    }

    @Override
    public void avancerAventurier(CaseBO[][] carte, AventurierBO aventurier) {
        PositionBO prochainePosition = getProchainePosition(aventurier);

        // On regarde si l'aventurier ne sors pas de la carte
        if (carteBS.isInsideMap(carteBS.trouverDimension(carte), prochainePosition)) {
            CaseBO caseActuelle = carte[aventurier.getPosition().getPositionY()][aventurier.getPosition().getPositionX()];
            CaseBO prochaineCase = carte[prochainePosition.getPositionY()][prochainePosition.getPositionX()];

            //On regarde que l'aventurier peut acceder à la case suivante
            if (carteBS.estCaseAccessible(prochaineCase)) {
                caseActuelle.setAventurier(null);
                aventurier.setPosition(prochainePosition);
                prochaineCase.setAventurier(aventurier);
                log.info("{} vient d'avancer d'une case.", aventurier.getName());
                if (prochaineCase.getTresors() > 0) {
                    aventurier.setTresors(aventurier.getTresors() + 1);
                    prochaineCase.setTresors(prochaineCase.getTresors() - 1);
                    log.info("{} vient de trouver un nouveau trésors. Il en a maintenant {}", aventurier.getName(), aventurier.getTresors());
                }
            } else {
                log.warn("{} souhaite avancer sur une position non accessible. Ce mouvement est ignoré.", aventurier.getName());
            }
        } else {
            log.error("Impossible de faire avancer {}. S'il avance encore il sortira des limites de la carte. Ce mouvement est ignoré.", aventurier.getName());
        }
    }

    /**
     * Creation de l'aventurier à partir d'une ligne du ficheir
     *
     * @param ligne ligne de ficheir
     * @return un objet AventurierBO
     * @throws TreasureHuntException en cas d'erreur sur la génération
     */
    private AventurierBO getAventurier(LigneBO ligne) throws TreasureHuntException {
        String[] split = ligne.getLigne().split(FichierConstante.DELIMITER);

        List<MouvementBO> movements = new ArrayList<>();

        for (char c : split[5].toCharArray()) {
            movements.add(new MouvementBO(getActionFromChar(c), false));
        }

        PositionBO position = ligneBS.trouverPositionAventurier(ligne.getLigne());

        return new AventurierBO(
                split[1],
                position,
                getAdventurerDirectionFromString(split[4]),
                movements,
                0
        );
    }

    /**
     * Renseigne l'orientation de l'aventurier
     *
     * @param s : charactère de l'oriebtation
     * @return : return le point cardinale de l'orientation de l'aventurier
     * @throws TreasureHuntException en cas de caractère inconnue
     */
    private OrientationEnum getAdventurerDirectionFromString(String s) throws TreasureHuntException {
        switch (s) {
            case "N":
                return OrientationEnum.NORTH;
            case "S":
                return OrientationEnum.SOUTH;
            case "E":
                return OrientationEnum.EAST;
            case "O":
                return OrientationEnum.WEST;
        }
        throw new TreasureHuntException("Impossible de définir la direction d'un aventurier.");
    }

    /**
     * Renseigne la liste d'action que l'aventurier va effectuer
     *
     * @param c : liste d'action
     * @return : les action sous formes d'enum
     * @throws TreasureHuntException en cas d'action inconnue
     */
    private ActionEnum getActionFromChar(char c) throws TreasureHuntException {
        switch (c) {
            case 'A':
                return ActionEnum.MOVE_FORWARD;
            case 'G':
                return ActionEnum.MOVE_LEFT;
            case 'D':
                return ActionEnum.MOVE_RIGHT;
        }
        throw new TreasureHuntException("Un mouvement d'un aventurier n'est pas correctement renseigné.");

    }

}
