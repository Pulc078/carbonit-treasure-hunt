package fr.carbonit.treasurehunt.businessservice.carte.impl;

import fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS;
import fr.carbonit.treasurehunt.businessservice.aventurier.impl.AventurierBSImpl;
import fr.carbonit.treasurehunt.businessservice.base.BaseBSTest;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarteBSImplTest extends BaseBSTest {

    CarteBS carteBS;
    AventurierBS aventurierBS;
    LigneBS ligneBS;

    @BeforeEach
    void setup() {
        this.aventurierBS = new AventurierBSImpl();
        this.carteBS = new CarteBSImpl();
        this.ligneBS = new LigneBSImpl();
    }

    @Test
    void placerAventurier() throws TreasureHuntException {
        CaseBO[][] map = new CaseBO[2][2];
        map[1][1] = new CaseBO(1,1);
        List<AventurierBO> liste = Collections.singletonList(new AventurierBO("Marco Polo", new PositionBO(1, 1)));
        carteBS.placerAventurier(map, liste);

        assertEquals("Marco Polo", map[1][1].getAventurier().getName());
    }

    @Test
    void trouverDimension() {
        CaseBO[][] map = new CaseBO[2][2];
        assertEquals(2, carteBS.trouverDimension(map).getPositionX());
        assertEquals(2, carteBS.trouverDimension(map).getPositionY());
    }

    @Test
    void isInsideMap_true() {
        PositionBO mapLimits = new PositionBO(1, 1);
        PositionBO coordinates = new PositionBO(0, 0);

        assertTrue(carteBS.isInsideMap(mapLimits, coordinates));
    }

    @Test
    void isInsideMap_false() {
        PositionBO mapLimits = new PositionBO(1, 1);
        PositionBO coordinates = new PositionBO(3, 2);

        assertFalse(carteBS.isInsideMap(mapLimits, coordinates));
    }

    @Test
    void estCaseAccessible_true() {
        CaseBO caseBO = new CaseBO(0, 0);
        assertTrue(carteBS.estCaseAccessible(caseBO));
    }

    @Test
    void estCaseAccessible_false_montagne() {
        CaseBO caseBO = new CaseBO(0, 0);
        caseBO.setMontagne(true);
        assertFalse(carteBS.estCaseAccessible(caseBO));
    }

    @Test
    void estCaseAccessible_false_Aventurier() {
        CaseBO caseBO = new CaseBO(0, 0);
        caseBO.setAventurier(new AventurierBO());
        assertFalse(carteBS.estCaseAccessible(caseBO));
    }
}