package fr.carbonit.treasurehunt.businessservice.carte.impl;

import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.constantes.LignesConstantes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LigneBSImplTest {

    LigneBS ligneBS;

    @BeforeEach
    void setup() {
        this.ligneBS = new LigneBSImpl();
    }

    @Test
    void isLigneTypeMap_false() {
        LigneBO ligneBO = new LigneBO();
        ligneBO.setType(LignesConstantes.AVENTURIER);

        assertFalse(ligneBS.isLigneTypeMap(ligneBO));
    }

    @Test
    void isLigneTypeMap_true() {
        LigneBO ligneBO = new LigneBO();
        ligneBO.setType(LignesConstantes.MAP);

        assertTrue(ligneBS.isLigneTypeMap(ligneBO));
    }

    @Test
    void isLigneTypeMontagne() {
        LigneBO ligneBO = new LigneBO();
        ligneBO.setType(LignesConstantes.MONTAGNE);

        assertTrue(ligneBS.isLigneTypeMontagne(ligneBO));
    }


    @Test
    void isLigneTypeTresor() {
        LigneBO ligneBO = new LigneBO();
        ligneBO.setType(LignesConstantes.TRESOR);

        assertTrue(ligneBS.isLigneTypeTresor(ligneBO));
    }

    @Test
    void isLigneTypeAventurier() {
        LigneBO ligneBO = new LigneBO();
        ligneBO.setType(LignesConstantes.AVENTURIER);

        assertTrue(ligneBS.isLigneTypeAventurier(ligneBO));
    }

    @Test
    void trouverPosition() {
        LigneBO ligne = new LigneBO("M - 0 - 1", LignesConstantes.MONTAGNE);
        assertEquals(0, ligneBS.trouverPosition(ligne.getLigne()).getPositionX());
        assertEquals(1, ligneBS.trouverPosition(ligne.getLigne()).getPositionY());

    }

    @Test
    void trouverPositionAventurier() {
        LigneBO ligne = new LigneBO("A - Léonidas - 0 - 1 - N - AA", LignesConstantes.AVENTURIER);
        assertEquals(0, ligneBS.trouverPositionAventurier(ligne.getLigne()).getPositionX());
        assertEquals(1, ligneBS.trouverPositionAventurier(ligne.getLigne()).getPositionY());
    }

}