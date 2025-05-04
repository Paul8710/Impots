# Impots

## Objectif du projet

Ce projet a été réalisé dans le cadre des cours de BUT Informatique de deuxième année.
L’objectif était de partir d’un code legacy et de le réusiner afin de le rendre plus lisible et modulable, tout en s’assurant qu’il passe toujours les tests unitaires de base existants.
Il fallait également atteindre un taux de couverture supérieur à 90 % avec JaCoCo et donc en conséquence, nous avons du écrire de nouveaux tests unitaires pour améliorer la couverture du code.
Enfin, le tout devait respecter les règles du checkstyle sans générer de warning ni d’erreur.

## Nos modifications

Nous avons choisi de refactoriser la méthode calculImpot (dans la classe Simulateur.java) en séparant les différents calculs dans des classes dédiées, regroupées dans un package Calculateurs. Cela permet une meilleure lisibilité, modularité et maintenabilité du code. Nous appelons donc les différentes méthodes de nos classes du package Calculateurs dans notre classe calculImpot.
Pour tester ces nouvelles classes, nous avons donc créé un package CalculateurTest contenant des classes de test unitaire pour chaque calculateur. Ces tests permettent de vérifier le fonctionnement de chaque partie du code de manière isolée.

Les valeurs fixes qui permettent le calcul des impôts pour l'année 2024 ont été regroupées dans une classe Parametres2024, afin d’être facilement modifiables si besoin, sans toucher au reste du code.
Cela permet aussi, pour les prochaines années, de pouvoir réutiliser le code, en modifiant seulement les constantes dans la classe Parametres2024, ou bien en faisant une nouvelle classe Parametre2025, par exemple.

Nous avons fait le choix de faire une classe permettant de valider les données mises en paramètre de la fonction calculImpot. Cela permet de donner de la lisibilité au code et d'éviter de vérifier directement dans la classe destinée au calcul.

Nous avons renommé toutes les variables par des noms plus parlant, permettant de s'y retrouver plus facilement et aussi enlevé les affichages console afin de mieux s'y retrouver.

Nous avons utilisé Maven pour gérer la partie "test" du projet, afin de voir le rapport de couverture avec Jacoco, ainsi que Checkstyle, permettant de vérifier si le fichier des règles de l’IUT a été respecté.

