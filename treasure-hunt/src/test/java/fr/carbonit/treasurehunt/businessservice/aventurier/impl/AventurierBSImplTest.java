package fr.carbonit.treasurehunt.businessservice.aventurier.impl;

import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.MouvementBO;
import fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS;
import fr.carbonit.treasurehunt.businessservice.base.BaseBSTest;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.transverse.constantes.ActionEnum;
import fr.carbonit.treasurehunt.transverse.constantes.OrientationEnum;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AventurierBSImplTest extends BaseBSTest {

   // CarteBS carteBS;

    AventurierBS aventurierBS;

    @BeforeEach
    void setup() {
        //this.carteBS = Mockito.spy(CarteBS.class);
        this.aventurierBS = new AventurierBSImpl();
    }


    @Test
    void extraireToutLesMouvements() {
        //Given
        List<AventurierBO> listAventuriers = new ArrayList<>();
        AventurierBO aventurier1 = new AventurierBO();
        AventurierBO aventurier2 = new AventurierBO();

        List<MouvementBO> mouv1 = new ArrayList<>();
        List<MouvementBO> mouv2 = new ArrayList<>();

        MouvementBO mouvementBO1 = new MouvementBO(ActionEnum.MOVE_FORWARD, false);
        MouvementBO mouvementBO2 = new MouvementBO(ActionEnum.MOVE_LEFT, false);
        MouvementBO mouvementBO3 = new MouvementBO(ActionEnum.MOVE_FORWARD, false);
        MouvementBO mouvementBO4 = new MouvementBO(ActionEnum.MOVE_RIGHT, false);
        mouv1.add(mouvementBO1);
        mouv1.add(mouvementBO2);
        mouv2.add(mouvementBO3);
        mouv2.add(mouvementBO4);

        aventurier1.setMouvements(mouv1);
        aventurier2.setMouvements(mouv2);
        listAventuriers.add(aventurier1);
        listAventuriers.add(aventurier2);
        List<MouvementBO> allMouv = new ArrayList<>(mouv1);
        allMouv.addAll(mouv2);

        assertEquals(allMouv, aventurierBS.extraireToutLesMouvements(listAventuriers));
    }

    @Test
    void tourneAGauche() {
        //Given
        AventurierBO aventurier = new AventurierBO();
        aventurier.setOrientation(OrientationEnum.NORTH);

        //when
        aventurierBS.tourneAGauche(aventurier);

        //then
        assertEquals(OrientationEnum.WEST, aventurier.getOrientation());
    }

    @Test
    void tourneADroite() {
        //given
        AventurierBO aventurier = new AventurierBO();
        aventurier.setOrientation(OrientationEnum.NORTH);

        //When
        aventurierBS.tourneADroite(aventurier);

        //Then
        assertEquals(OrientationEnum.EAST, aventurier.getOrientation());
    }

    //----------------------------------------------------------------------------------------------------//
    //----------LES TESTS CI-DESSOUS NE SONT PAS FONCTIONNEL----------------------------------------------//
    //---------Je n'arrive pas à Mocker/Injecter ou Spy les services appelé par la methodes---------------//


    @Test
    void avancerAventurier_true() {
        //  //GIVEN
        //  PositionBO dimensionCarte = new PositionBO(2, 2);
        //  CaseBO[][] fakeMap = new CaseBO[2][2];
//
        //  for (int i = 0; i < dimensionCarte.getPositionY(); i++) {
        //      for (int j = 0; j < dimensionCarte.getPositionX(); j++) {
        //          fakeMap[i][j] = new CaseBO(i, j);
        //      }
        //  }
//
        //  AventurierBO aventurier = new AventurierBO();
        //  aventurier.setOrientation(OrientationEnum.NORTH);
        //  aventurier.setPosition(new PositionBO(1, 1));
        //  fakeMap[1][1].setAventurier(aventurier);
//
        //  //WHEN
        //  doReturn(true).when(carteBS).isInsideMap(dimensionCarte, new PositionBO(1, 0));
        //  doReturn(true).when(carteBS).estCaseAccessible(new CaseBO(1,1));
        //  aventurierBS.avancerAventurier(fakeMap, aventurier);
//
        //  //THEN
        //  assertEquals(aventurier, fakeMap[0][1].getAventurier());
    }


    @Test
    void avancerAventurier_false_montagne() {
        //    //GIVEN
        //    PositionBO dimensionCarte = new PositionBO(2, 2);
        //    CaseBO[][] carte = new CaseBO[2][2];

        //    for (int i = 0; i < dimensionCarte.getPositionY(); i++) {
        //        for (int j = 0; j < dimensionCarte.getPositionX(); j++) {
        //            carte[i][j] = new CaseBO(i, j);
        //        }
        //    }

        //    AventurierBO aventurier = new AventurierBO();
        //    aventurier.setOrientation(OrientationEnum.EAST);
        //    aventurier.setPosition(new PositionBO(0, 0));
        //    carte[0][0].setAventurier(aventurier);
        //    carte[0][1].setMontagne(true);

        //    //WHEN
        //    doReturn(true).when(carteBS).isInsideMap(dimensionCarte, new PositionBO(1, 0));
        //    doReturn(false).when(carteBS).estCaseAccessible(carte[0][1]);
        //    aventurierBS.avancerAventurier(carte, aventurier);

        //    //THEN
        //    assertEquals(aventurier, carte[0][0].getAventurier());
    }

    @Test
    void avancerAventurier_false_sizeMap() {
        //  //GIVEN
        //  PositionBO dimensionCarte = new PositionBO(2, 2);
        //  CaseBO[][] carte = new CaseBO[2][2];

        //  for (int i = 0; i < dimensionCarte.getPositionY(); i++) {
        //      for (int j = 0; j < dimensionCarte.getPositionX(); j++) {
        //          carte[i][j] = new CaseBO(i, j);
        //      }
        //  }

        //  AventurierBO aventurier = new AventurierBO();
        //  aventurier.setOrientation(OrientationEnum.NORTH);
        //  aventurier.setPosition(new PositionBO(0, 0));
        //  carte[0][0].setAventurier(aventurier);

        //  //WHEN
        //  doReturn(false).when(carteBS).isInsideMap(dimensionCarte, new PositionBO(1, 0));
        //  aventurierBS.avancerAventurier(carte, aventurier);

        //  //THEN
        //  assertEquals(aventurier, carte[0][0].getAventurier());
    }

    @Test
    void getAventuriers() throws TreasureHuntException {
        //  //GIVEN
        //  LigneBO ligne = new LigneBO("A - Léonidas - 0 - 0 - N - AA", LignesConstantes.AVENTURIER);
        //  List<LigneBO> lines = Collections.singletonList(ligne);
        //  //WHEN
        //  List<AventurierBO> adventurers = aventurierBS.getAventuriers(lines);
        //  //THEN
        //  assertEquals(1, adventurers.size());
        //  assertEquals("Léonidas", adventurers.get(0).getName());
    }


}