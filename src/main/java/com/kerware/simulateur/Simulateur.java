package com.kerware.simulateur;

import Calculateurs.CalculateurParts;
import Calculateurs.CalculateurDecote;
import Calculateurs.CalculateurBaisseImpot;
import Calculateurs.CalculateurBaremeProgressif;
import Calculateurs.CalculateurHautRevenu;
import Calculateurs.CalculateurAbattements;
import FoyerFiscal.ValidateurDonneesCalculImpot;

import static FoyerFiscal.Parametres2024.TAUX_MONTANT_ABATTEMENT;
import static FoyerFiscal.Parametres2024.LIMITE_MONTANT_ABATTEMENT_MAX;
import static FoyerFiscal.Parametres2024.LIMITE_MONTANT_ABATTEMENT_MIN;



/**
 *  Cette classe permet de simuler le calcul de l'impôt sur le revenu
 *  en France pour l'année 2024 sur les revenus de l'année 2023 pour
 *  des cas simples de contribuables célibataires, mariés, divorcés, veufs
 *  ou pacsés avec ou sans enfants à charge ou enfants en situation de handicap
 *  et parent isolé.
 *
 *  EXEMPLE DE CODE DE TRES MAUVAISE QUALITE FAIT PAR UN DEBUTANT
 *
 *  Pas de lisibilité, pas de commentaires, pas de tests
 *  Pas de documentation, pas de gestion des erreurs
 *  Pas de logique métier, pas de modularité
 *  Pas de gestion des exceptions, pas de gestion des logs
 *  Principe "Single Responsability" non respecté
 *  Pas de traçabilité vers les exigences métier
 *
 *  Pourtant ce code fonctionne correctement
 *  Il s'agit d'un "legacy" code qui est difficile à maintenir
 *  L'auteur n'a pas fourni de tests unitaires
 **/

public class Simulateur {
    // revenu fiscal de référence
    private double revenuFiscalReference = 0;


    // abattement
    private double montantAbattement = 0;

    // nombre de parts des  déclarants
    private double nombrePartsDeclarants= 0;
    // nombre de parts du foyer fiscal
    private double nombreParts = 0;

    // decote
    private double decote = 0;
    // impôt des déclarants
    private double montantImpotDeclarants = 0;
    // impôt du foyer fiscal
    private double montantImpot = 0;
    private double montantImpotAvantDecote = 0;


    // Contribution exceptionnelle sur les hauts revenus
    private double contributionExceptionnelle = 0;

    // Getters pour adapter le code legacy pour les tests unitaires

    /**
     * @return le revenu fiscal de référance.
     */
    public double getRevenuReference() {
        return revenuFiscalReference;
    }

    /**
     * @return le montant de la décote.
     */
    public double getDecote() {
        return decote;
    }

    /**
     * @return le montant de l'abattement.
     */
    public double getAbattement() {
        return montantAbattement;
    }

    /**
     * @return le nombre de parts.
     */
    public double getNbParts() {
        return nombreParts;
    }

    /**
     * @return le montant des impôts avant la décote.
     */
    public double getImpotAvantDecote() {
        return montantImpotAvantDecote;
    }

    /**
     * @return le montant des impôts net.
     */
    public double getImpotNet() {
        return montantImpot;
    }

    /**
     * @return le montant de la contribution exceptionnelle.
     */
    public double getContribExceptionnelle() {
        return contributionExceptionnelle;
    }


    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023

    public int calculImpot( int revenuNetDeclarant1, int revenuNetDeclarant2,
                            SituationFamiliale situationFamiliale, int nombreEnfants,
                            int nombreEnfantsHandicapes, boolean parentIsole) {

        // Préconditions
        new ValidateurDonneesCalculImpot().validerDonnees(
                revenuNetDeclarant1, revenuNetDeclarant2, situationFamiliale,
                nombreEnfants, nombreEnfantsHandicapes, parentIsole
        );
        // Initialisation des variables

        System.out.println("--------------------------------------------------");
        System.out.println( "Revenu net declarant1 : " + revenuNetDeclarant1 );
        System.out.println( "Revenu net declarant2 : " + revenuNetDeclarant2 );
        System.out.println( "Situation familiale : " + situationFamiliale.name() );

        // Abattement
        // EXIGENCE : EXG_IMPOT_02
        CalculateurAbattements calculateurAbattements = new CalculateurAbattements();
        montantAbattement = calculateurAbattements.calculerAbattement(
                revenuNetDeclarant1, revenuNetDeclarant2, TAUX_MONTANT_ABATTEMENT,
                LIMITE_MONTANT_ABATTEMENT_MIN, LIMITE_MONTANT_ABATTEMENT_MAX, situationFamiliale);
        System.out.println( "Abattement : " + montantAbattement );

        revenuFiscalReference = revenuNetDeclarant1 + revenuNetDeclarant2 - montantAbattement;
        if ( revenuFiscalReference < 0 ) {
            revenuFiscalReference = 0;
        }

        System.out.println( "Revenu fiscal de référence : " + revenuFiscalReference );


        // parts déclarants
        // EXIG  : EXG_IMPOT_03
        CalculateurParts calculateurParts = new CalculateurParts();

        nombrePartsDeclarants= calculateurParts.nombrePartsSituationFamiliale(situationFamiliale);

        nombreParts = calculateurParts.calculerParts(situationFamiliale, nombreEnfants,
                nombreEnfantsHandicapes, parentIsole);

        System.out.println( "Nombre d'enfants  : " + nombreEnfants );
        System.out.println( "Nombre d'enfants handicapés : " + nombreEnfantsHandicapes );
        System.out.println( "Nombre de parts : " + nombreParts );



        // EXIGENCE : EXG_IMPOT_07:
        // Contribution exceptionnelle sur les hauts revenus
        CalculateurHautRevenu calculateurHautRevenu = new CalculateurHautRevenu();
        contributionExceptionnelle = calculateurHautRevenu.calculerCEHR(
                revenuFiscalReference, situationFamiliale);
        System.out.println("Contribution exceptionnelle sur les hauts revenus : "
                + contributionExceptionnelle);

        // Calcul impôt des declarants
        // EXIGENCE : EXG_IMPOT_04
        CalculateurBaremeProgressif bareme = new CalculateurBaremeProgressif();
        montantImpotDeclarants = bareme.calculerImpot(
                revenuFiscalReference, nombrePartsDeclarants);
        // Calcul impôt foyer fiscal complet
        // EXIGENCE : EXG_IMPOT_04
        montantImpot = bareme.calculerImpot(revenuFiscalReference, nombreParts);

        // Vérification de la baisse d'impôt autorisée
        // EXIGENCE : EXG_IMPOT_05
        // baisse impot

        CalculateurBaisseImpot calculateurBaisseImpot = new CalculateurBaisseImpot();
        montantImpot = calculateurBaisseImpot.calculerBaisseImpot(
                montantImpotDeclarants, montantImpot, nombrePartsDeclarants, nombreParts);
        System.out.println("Impôt brut après plafonnement avant décote : " + montantImpot);
        montantImpotAvantDecote = montantImpot;

        // Calcul de la decote
        // EXIGENCE : EXG_IMPOT_06
        CalculateurDecote calculateurDecote = new CalculateurDecote();
        decote = calculateurDecote.calculerDecote(montantImpot, situationFamiliale);

        System.out.println( "Decote : " + decote );

        montantImpot = montantImpot - decote;

        montantImpot += contributionExceptionnelle;

        montantImpot = Math.round( montantImpot );

        System.out.println( "Impôt sur le revenu net final : " + montantImpot );
        return  (int)montantImpot;
    }
}
