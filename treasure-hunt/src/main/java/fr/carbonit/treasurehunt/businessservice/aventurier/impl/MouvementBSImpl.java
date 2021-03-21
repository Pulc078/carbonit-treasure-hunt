package fr.carbonit.treasurehunt.businessservice.aventurier.impl;

import fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS;
import fr.carbonit.treasurehunt.businessservice.aventurier.MouvementBS;
import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.MouvementBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.transverse.constantes.ActionEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fr.carbonit.treasurehunt.businessservice.aventurier.MouvementBS")
public class MouvementBSImpl extends BaseBS implements MouvementBS {

    private static final Logger log = LogManager.getLogger(MouvementBSImpl.class);

    @Override
    public void deplacerAventuriers(CaseBO[][] carte, List<AventurierBO> aventuriers, int counter) {
        AventurierBS aventurierBS = getBS(AventurierBS.class);
        List<MouvementBO> mouvements = aventurierBS.extraireToutLesMouvements(aventuriers);

        //Tant qu'il qu'il y a des mouvements non joués chez les aventuriers
        if (mouvements.stream().anyMatch(a -> !a.isAlreadyExecuted())) {
            log.info("Les aventuriers continue leur exploration, nous sommes au tour {}", counter);
            //On filtre uniquement les aventurier ayant encore des mouvements à jouer ?
            aventuriers.stream().filter(a -> counter < a.getMouvements().size() && !a.getMouvements().get(counter).isAlreadyExecuted()).forEach(a -> {
                MouvementBO movement = a.getMouvements().get(counter); //Récupération du prochain mouvement à jouer
                mouvementAventurier(carte, a, movement); //Execution du mouvement
                movement.setAlreadyExecuted(true); //Définir le mouvement comme joué
            });
            deplacerAventuriers(carte, aventuriers, counter + 1);
        } else log.info("Les aventuriers ont terminés leur exploration");
    }

    private void mouvementAventurier(CaseBO[][] carte, AventurierBO aventurier, MouvementBO mouvementBO) {
        AventurierBS aventurierBS = getBS(AventurierBS.class);

        if (mouvementBO.getAction().equals(ActionEnum.MOVE_FORWARD)) {
            aventurierBS.avancerAventurier(carte, aventurier);
        } else if (mouvementBO.getAction().equals(ActionEnum.MOVE_LEFT)) {
            aventurierBS.tourneAGauche(aventurier);
        } else if (mouvementBO.getAction().equals(ActionEnum.MOVE_RIGHT)) {
            aventurierBS.tourneADroite(aventurier);
        }
    }

}
