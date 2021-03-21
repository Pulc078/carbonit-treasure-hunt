package fr.carbonit.treasurehunt.transverse.file;

import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.aventurier.PositionBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.constantes.FichierConstante;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GestionFichier extends BaseBS implements ApplicationContextAware {


    @Autowired
    private static ApplicationContext contexte;

    private static final Logger logger = LogManager.getLogger(GestionFichier.class);

    public static List<String> getFileLines(String inputFiles) throws TreasureHuntException {

        List<String> fileLine = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(inputFiles))) {
            stream.forEach(fileLine::add);
        } catch (IOException ioe) {
            throw new TreasureHuntException("Impossible de lire le fichier fournit.");
        }

        return fileLine;
    }


    public static void ecrireFichierDeSortie(CaseBO[][] carte, List<AventurierBO> aventuriers, List<LigneBO> lignes, String output) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            ecrireFichier(printWriter, carte, aventuriers, lignes);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Ecriture du fichier
     *
     * @param printWriter objet d'écriture
     * @param carte       : carte de la Madre Dio
     * @param aventuriers la liste des aevnturiers
     * @param lignes      : les lignes fournis en entrée
     * @throws IOException en cas d'erreur sur l'écriture du fichier
     */
    private static void ecrireFichier(PrintWriter printWriter, CaseBO[][] carte, List<AventurierBO> aventuriers, List<LigneBO> lignes) {
        LigneBS ligneBS = getBS(LigneBS.class);
        CarteBS carteBS = getBS(CarteBS.class);

        logger.info("écriture du fichier de sortie");
        lignes.forEach(l -> {
            if (ligneBS.isLigneTypeMap(l)) {
                ecrireLigneCarte(printWriter, l);
            } else if (ligneBS.isLigneTypeMontagne(l)) {
                CaseBO caseMontagne = carteBS.trouverCase(carte, ligneBS.trouverPosition(l.getLigne()));
                if (caseMontagne != null) {
                    ecrireLigneMontagne(printWriter, l);
                }
            } else if (ligneBS.isLigneTypeTresor(l)) {
                CaseBO caseTresor = carteBS.trouverCase(carte, ligneBS.trouverPosition(l.getLigne()));
                if (caseTresor != null) {

                    ecrireLigneTresors(printWriter, caseTresor);
                }
            }
        });

        aventuriers.forEach(a -> ecrireLigneAventurier(printWriter, a));

        printWriter.close();
        logger.info("écriture du fichier de sortie terminée");
    }

    /**
     * Ecris les lignes aventurier de la carte
     *
     * @param printWriter : objet d'écriture
     * @param a           : ligne a écrire
     */
    private static void ecrireLigneAventurier(PrintWriter printWriter, AventurierBO a) {
        logger.info("écriture d'une ligne aventurier");
        printWriter.println(FichierConstante.LIGNE_AVENTURIER +
                a.getName() + FichierConstante.DELIMITER + a.getPosition().getPositionX() + FichierConstante.DELIMITER + a.getPosition().getPositionY() + FichierConstante.DELIMITER +
                a.getOrientation().getValue() + FichierConstante.DELIMITER + a.getTresors()
        );
    }


    /**
     * Ecris les lignes tresors de la carte
     *
     * @param printWriter : objet d'écriture
     * @param caseTresor  : ligne a écrire
     */
    private static void ecrireLigneTresors(PrintWriter printWriter, CaseBO caseTresor) {
        logger.info("écriture d'une ligne de tresors");
        if (caseTresor.getTresors() > 0) {
            printWriter.println(FichierConstante.LIGNE_TRESORS +
                    caseTresor.getY() + FichierConstante.DELIMITER + caseTresor.getX() + FichierConstante.DELIMITER + caseTresor.getTresors()
            );
        }
    }

    /**
     * Ecris les lignes montagne de la carte
     *
     * @param printWriter : objet d'écriture
     * @param ligne       : ligne a écrire
     */
    private static void ecrireLigneMontagne(PrintWriter printWriter, LigneBO ligne) {
        logger.info("écriture d'une ligne de montagnes");
        PositionBO mountainCoordinates = new PositionBO(ligne);
        printWriter.println(FichierConstante.LIGNE_MONTAGNE + mountainCoordinates.getPositionX() + FichierConstante.DELIMITER + mountainCoordinates.getPositionY()
        );
    }


    /**
     * Ecris les dimensions de la carte
     *
     * @param printWriter : objet d'écriture
     * @param ligne       : ligne a écrire
     */
    private static void ecrireLigneCarte(PrintWriter printWriter, LigneBO ligne) {
        logger.info("écriture de la ligne de dimension de la carte");
        PositionBO dimensions = new PositionBO(ligne);
        printWriter.write(FichierConstante.LIGNE_CARTE +
                dimensions.getPositionX() + FichierConstante.DELIMITER + dimensions.getPositionY()
        );
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        contexte = applicationContext;
    }
}
