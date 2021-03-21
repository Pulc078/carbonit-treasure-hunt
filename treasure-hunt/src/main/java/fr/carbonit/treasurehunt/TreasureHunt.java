package fr.carbonit.treasurehunt;

import fr.carbonit.treasurehunt.businessservice.aventurier.AventurierBS;
import fr.carbonit.treasurehunt.businessservice.aventurier.MouvementBS;
import fr.carbonit.treasurehunt.businessservice.base.BaseBS;
import fr.carbonit.treasurehunt.businessservice.carte.CarteBS;
import fr.carbonit.treasurehunt.businessservice.carte.LigneBS;
import fr.carbonit.treasurehunt.businessobjet.aventurier.AventurierBO;
import fr.carbonit.treasurehunt.businessobjet.carte.CaseBO;
import fr.carbonit.treasurehunt.businessobjet.carte.LigneBO;
import fr.carbonit.treasurehunt.transverse.exception.TreasureHuntException;
import fr.carbonit.treasurehunt.transverse.file.GestionFichier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"fr"}, lazyInit = true)
public class TreasureHunt implements EnvironmentAware {

    @Autowired
    private static Environment env;
    private static final Logger logger = LogManager.getLogger(TreasureHunt.class);

    @PostConstruct
    public void log() {
        logger.info("Application config loaded!");  // Displays as expected
        logger.info("fileInputArgDescription: {}", env.getProperty("fileInputPath"));
        logger.info("fileOutputArgDescription: {}", env.getProperty("fileOutputPath"));
    }


    public static void main(String[] args) throws TreasureHuntException, IOException {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(TreasureHunt.class);
        ApplicationContext contexte = springApplicationBuilder.run();
        BaseBS.setContexte(contexte);

        LigneBS ligneBS = contexte.getBean(LigneBS.class);
        CarteBS carteBS = contexte.getBean(CarteBS.class);
        MouvementBS mouvementBS = contexte.getBean(MouvementBS.class);
        AventurierBS aventurierBS = contexte.getBean(AventurierBS.class);

        SpringApplication.run(TreasureHunt.class, args);

        logger.info("Lecture du fichier d'entrée");
        List<LigneBO> ligneBOList = ligneBS.getLignes(GestionFichier.getFileLines(env.getProperty("fileInputPath")));

        logger.info("Création de la carte de la Madre de Dios.");
        CaseBO[][] carte = carteBS.creerCarte(ligneBOList);

        logger.info("Création des valeureux explorateurs");
        List<AventurierBO> aventuriers = aventurierBS.getAventuriers(ligneBOList);

        logger.info("Placement des explorateurs sur leur point de départs");
        carteBS.placerAventurier(carte, aventuriers);

        logger.info("Résolution des mouvements des aventuriers. May the odd be ever in their favour");
        mouvementBS.deplacerAventuriers(carte, aventuriers, 0);

        String outPutPath = env.getProperty("fileOutputPath") + "/" + env.getProperty("fileOutputName");

        GestionFichier.ecrireFichierDeSortie(carte, aventuriers, ligneBOList, outPutPath);
    }

    public void setEnvironment(final Environment environment) {
        env = environment;
    }


    public String load(String propertyName)
    {
        return env.getRequiredProperty(propertyName);
    }

}
