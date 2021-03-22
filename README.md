# carbonit-treasure-hunt
#Exercice technique pour CARBON-IT
Lancer le projet
    Pour lancer le projet, cloner ou télécharger l'archive de ce repo.\
    Passer par son IDE favoris. Par exemple Intellij ou Eclipse\
    Lancer une commande Maven clean install -U \
    Lancer le projet depuis la classe TreasurHunt\
    Propriété du projet : D:\Dev_projet\treasure-hunt\src\main\resources\application.properties, dans le fichier application.properties vous pouvez modifier le chemin du fichier d'entrée (fileOutputName), le nom du fichier de sortie("fileOutputName") et son chemin de sortie ("fileOutputPath");
    
    
#Architecture

Le projet est constitué des parties suivante :

    Main.java : classe principale qui permet de lancer le programmme
    buisnessobject : Il contient les objets métiers associé au projet
    buisnessservice : contient les services associé aux objets pour le traitement de la logique, chaque service et divisé en une interface (monServiceBS) et son implémentation (monServiceBSImpl)

#Environnement

    Java 8 (limité par la version de mon ordianteur de travail)
    Maven 3
    Junit 5
    Intellij Idea
