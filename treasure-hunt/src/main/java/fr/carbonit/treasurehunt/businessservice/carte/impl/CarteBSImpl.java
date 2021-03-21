package fr.carbonit.treasurehunt.businessservice.carte.impl;

import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("fr.carbonit.treasurehunt.businessservice.carte.CarteBS.java")
public class CarteBSImpl extends BaseBS implements CarteBS {


    private static final Logger log = LogManager.getLogger(CarteBSImpl.class);
    private final  LigneBS ligneBS = getBS(LigneBS.class);


    @Override
    public CaseBO[][] creerCarte(List<LigneBO> lignes) {

        PositionBO dimension = trouverDimension(lignes);
        CaseBO[][] carte = new CaseBO[dimension.getPositionY()][dimension.getPositionX()];

        log.info("Construction de la carte");
        construireCarte(carte, dimension);

        log.info("Ajout des Montagnes");
        placerMontagne(carte, lignes);

        log.info("Ajout des trésors");
        placerTresor(carte, lignes);


        return carte;
    }

    @Override
    public void placerAventurier(CaseBO[][] carte, List<AventurierBO> aventuriers) throws TreasureHuntException {
        for (AventurierBO aventurier : aventuriers) {
            CaseBO caseBo = carte[aventurier.getPosition().getPositionY()][aventurier.getPosition().getPositionX()];
            if (estAccesible(caseBo)) {
                carte[aventurier.getPosition().getPositionY()][aventurier.getPosition().getPositionX()].setAventurier(aventurier);
            } else {
                throw new TreasureHuntException("Impossible de placer l'aventurier " + aventurier.getName() + "sur ça case de départ");
            }
        }

    }

    /**
     * On utilise la ligne Carte pour déterminer les dimensions
     *
     * @param lignes : ensemble de ligne fournis par le fichier
     * @return : Un objet Position contenant les dimensions X et Y de la carte
     */
    private PositionBO trouverDimension(List<LigneBO> lignes) {
        List<LigneBO> lignePosition =
                lignes.stream().filter(ligneBS::isLigneTypeMap)
                        .collect(Collectors.toList());

        return new PositionBO(lignePosition.get(0));
    }


    @Override
    public PositionBO trouverDimension(CaseBO[][] map) {
        PositionBO position = new PositionBO();
        position.setPositionY(map.length);
        position.setPositionX(map[0].length);

        return position;
    }

    @Override
    public boolean isInsideMap(PositionBO dimension, PositionBO prochainePosition) {
        return (
                dimension.getPositionY() > prochainePosition.getPositionY() &&
                        dimension.getPositionX() > prochainePosition.getPositionX() &&
                        prochainePosition.getPositionY() >= 0 &&
                        prochainePosition.getPositionX() >= 0
        );
    }

    @Override
    public boolean estCaseAccessible(CaseBO prochaineCase) {
        return (!prochaineCase.isMontagne() && prochaineCase.getAventurier() == null);
    }

    @Override
    public CaseBO trouverCase(CaseBO[][] carte, PositionBO position) {
        if (isInsideMap(trouverDimension(carte), position)) {
            return carte[position.getPositionY()][position.getPositionX()];
        }
        return null;
    }


    /**
     * On remplie la carte selon les dimension fournis par la ligne carte
     *
     * @param map       la carte à remplir
     * @param dimension les dimensions limites de la carte
     */
    private void construireCarte(CaseBO[][] map, PositionBO dimension) {
        for (int i = 0; i < dimension.getPositionY(); i++) {
            for (int j = 0; j < dimension.getPositionX(); j++) {
                map[i][j] = new CaseBO(i, j);
            }
        }
    }

    /**
     * Place les montagnes, en fonction des lignes M fournis par le fichier (elles sont optionnel)
     *
     * @param carte  : carte vide de montagne
     * @param lignes l'ensemble des lignes fournis par le fichier
     */
    private void placerMontagne(CaseBO[][] carte, List<LigneBO> lignes) {

        List<LigneBO> listeMontagne =
                lignes.stream().filter(ligneBS::isLigneTypeMontagne).collect(Collectors.toList());

        if (!listeMontagne.isEmpty()) {
            listeMontagne.stream()
                    .map(montagne -> ligneBS.trouverPosition(montagne.getLigne()))
                    .forEach(position -> carte[position.getPositionY()][position.getPositionX()].setMontagne(true));
        }
    }

    private void placerTresor(CaseBO[][] carte, List<LigneBO> lignes) {
        List<LigneBO> listeTresors =
                lignes.stream().filter(ligneBS::isLigneTypeTresor).collect(Collectors.toList());

        if (!listeTresors.isEmpty()) {
            listeTresors.forEach(ligne -> {
                PositionBO position = ligneBS.trouverPosition(ligne.getLigne());
                CaseBO caseBO = carte[position.getPositionY()][position.getPositionX()];
                if (!caseBO.isMontagne()) {
                    caseBO.setTresors(ligneBS.setNombreTresors(ligne.getLigne()));
                } else {
                    log.error("la ligne tésor avec les coordonnées [{},{}] a été ignorée car la position pointe sur une montagne", position.getPositionY(), position.getPositionX());
                }
            });
        }
    }

    /**
     * @param caseBo la case à tester
     * @return true si la case n'est pas une montagne et si aucun autre aventurier n'est présent sur la case
     */
    private boolean estAccesible(CaseBO caseBo) {
        return !caseBo.isMontagne() && caseBo.getAventurier() == null;
    }


}
