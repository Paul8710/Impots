package com.kerware.simulateur;

import Calculateurs.CalculateurAbattements;
import Calculateurs.CalculateurDecote;
import Calculateurs.CalculateurParts;
import FoyerFiscal.FoyerFiscal;

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


    // Les limites des tranches de revenus imposables
    /*
    TODO: Peut être faire des constantes facilelement modifiable dans param2024 ?
     */
    private int l00 = 0 ;
    private int l01 = 11294;
    private int l02 = 28797;
    private int l03 = 82341;
    private int l04 = 177106;
    private int l05 = Integer.MAX_VALUE;

    private int[] limitesTranchesFiscales = { l00, l01, l02, l03, l04, l05 };

    // Les taux d'imposition par tranche
    private double t00 = 0.0;
    private double t01 = 0.11;
    private double t02 = 0.3;
    private double t03 = 0.41;
    private double t04 = 0.45;

    private double[] tauxImpositionTranches = { t00, t01, t02, t03, t04 };


    // Les limites des tranches pour la contribution exceptionnelle sur les hauts revenus
    private int lce00 = 0;
    private int lce01 = 250000;
    private int lce02 = 500000;
    private int lce03 = 1000000;
    private int lce04 = Integer.MAX_VALUE;

    private int[] limitesCEHR = new int[5];

    // Les taux de la contribution exceptionnelle sur les hauts revenus pour les celibataires
    private double tce00 = 0.0;
    private double tce01 = 0.03;
    private double tce02 = 0.04;
    private double tce03 = 0.04;

    private double[] tauxCEHRCelibataire = new double[4];

    // Les taux de la contribution exceptionnelle sur les hauts revenus pour les couples
    private double tce00C = 0.0;
    private double tce01C = 0.0;
    private double tce02C = 0.03;
    private double tce03C = 0.04;

    private double[] tauxCEHRCouple = new double[4];

    // Abattement
    private  int lmontantAbattementMax = 14171;
    private  int lmontantAbattementMin = 495;
    private double tmontantAbattement = 0.1;

    // Plafond de baisse maximal par demi part
    private double plafDemiPart = 1759;

    // revenu fiscal de référence
    private double revenuFiscalReference = 0;

    // revenu imposable
    private double rImposable = 0;

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
    // parent isolé
    private boolean parentIsole = false;
    // Contribution exceptionnelle sur les hauts revenus
    private double contributionExceptionnelle = 0;

    // Getters pour adapter le code legacy pour les tests unitaires

    public double getRevenuReference() {
        return revenuFiscalReference;
    }

    public double getDecote() {
        return decote;
    }


    public double getAbattement() {
        return montantAbattement;
    }

    public double getNbParts() {
        return nombreParts;
    }

    public double getImpotAvantDecote() {
        return montantImpotAvantDecote;
    }

    public double getImpotNet() {
        return montantImpot;
    }

    public double getContribExceptionnelle() {
        return contributionExceptionnelle;
    }


    // Fonction de calcul de l'impôt sur le revenu net en France en 2024 sur les revenu 2023

    public int calculImpot( int revenuNetDeclarant1, int revenuNetDeclarant2, SituationFamiliale situationFamiliale, int nombreEnfants, int nombreEnfantsHandicapes, boolean parentIsole) {

        // Préconditions
        if ( revenuNetDeclarant1  < 0 || revenuNetDeclarant2 < 0 ) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif");
        }

        if ( nombreEnfants < 0 ) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif");
        }

        if ( nombreEnfantsHandicapes < 0 ) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être négatif");
        }

        if ( situationFamiliale == null ) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être null");
        }

        if ( nombreEnfantsHandicapes > nombreEnfants ) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être supérieur au nombre d'enfants");
        }

        if ( nombreEnfants > 7 ) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être supérieur à 7");
        }

        if ( parentIsole && ( situationFamiliale == SituationFamiliale.MARIE || situationFamiliale == SituationFamiliale.PACSE ) ) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé");
        }

        boolean seul = situationFamiliale == SituationFamiliale.CELIBATAIRE || situationFamiliale == SituationFamiliale.DIVORCE || situationFamiliale == SituationFamiliale.VEUF;
        if (  seul && revenuNetDeclarant2 > 0 ) {
            throw new IllegalArgumentException("Un célibataire, un divorcé ou un veuf ne peut pas avoir de revenu pour le déclarant 2");
        }

        // Initialisation des variables
        limitesCEHR[0] = lce00;
        limitesCEHR[1] = lce01;
        limitesCEHR[2] = lce02;
        limitesCEHR[3] = lce03;
        limitesCEHR[4] = lce04;

        tauxCEHRCelibataire[0] = tce00;
        tauxCEHRCelibataire[1] = tce01;
        tauxCEHRCelibataire[2] = tce02;
        tauxCEHRCelibataire[3] = tce03;

        tauxCEHRCouple[0] = tce00C;
        tauxCEHRCouple[1] = tce01C;
        tauxCEHRCouple[2] = tce02C;
        tauxCEHRCouple[3] = tce03C;

        System.out.println("--------------------------------------------------");
        System.out.println( "Revenu net declarant1 : " + revenuNetDeclarant1 );
        System.out.println( "Revenu net declarant2 : " + revenuNetDeclarant2 );
        System.out.println( "Situation familiale : " + situationFamiliale.name() );

        // Abattement
        // EXIGENCE : EXG_IMPOT_02
        CalculateurAbattements calculateurAbattements = new CalculateurAbattements();
        montantAbattement = calculateurAbattements.calculerAbattement(revenuNetDeclarant1, revenuNetDeclarant2, tmontantAbattement, lmontantAbattementMin, lmontantAbattementMax, situationFamiliale);
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

        nombreParts = calculateurParts.calculerParts(situationFamiliale, nombreEnfants, nombreEnfantsHandicapes, parentIsole);

        System.out.println( "Nombre d'enfants  : " + nombreEnfants );
        System.out.println( "Nombre d'enfants handicapés : " + nombreEnfantsHandicapes );
        System.out.println( "Nombre de parts : " + nombreParts );



        // EXIGENCE : EXG_IMPOT_07:
        // Contribution exceptionnelle sur les hauts revenus
        contributionExceptionnelle = 0;
        int i = 0;
        do {
            if ( revenuFiscalReference >= limitesCEHR[i] && revenuFiscalReference < limitesCEHR[i+1] ) {
                if ( nombrePartsDeclarants== 1 ) {
                    contributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * tauxCEHRCelibataire[i];
                } else {
                    contributionExceptionnelle += ( revenuFiscalReference - limitesCEHR[i] ) * tauxCEHRCouple[i];
                }
                break;
            } else {
                if ( nombrePartsDeclarants== 1 ) {
                    contributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * tauxCEHRCelibataire[i];
                } else {
                    contributionExceptionnelle += ( limitesCEHR[i+1] - limitesCEHR[i] ) * tauxCEHRCouple[i];
                }
            }
            i++;
        } while( i < 5);

        contributionExceptionnelle = Math.round( contributionExceptionnelle );
        System.out.println( "Contribution exceptionnelle sur les hauts revenus : " + contributionExceptionnelle );

        // Calcul impôt des declarants
        // EXIGENCE : EXG_IMPOT_04
        rImposable = revenuFiscalReference / nombrePartsDeclarants;

        montantImpotDeclarants = 0;

        i = 0;
        do {
            if ( rImposable >= limitesTranchesFiscales[i] && rImposable < limitesTranchesFiscales[i+1] ) {
                montantImpotDeclarants += ( rImposable - limitesTranchesFiscales[i] ) * tauxImpositionTranches[i];
                break;
            } else {
                montantImpotDeclarants += ( limitesTranchesFiscales[i+1] - limitesTranchesFiscales[i] ) * tauxImpositionTranches[i];
            }
            i++;
        } while( i < 5);

        montantImpotDeclarants = montantImpotDeclarants * nombrePartsDeclarants;
        montantImpotDeclarants = Math.round( montantImpotDeclarants );

        System.out.println( "Impôt brut des déclarants : " + montantImpotDeclarants );

        // Calcul impôt foyer fiscal complet
        // EXIGENCE : EXG_IMPOT_04
        rImposable =  revenuFiscalReference / nombreParts;
        montantImpot = 0;
        i = 0;

        do {
            if ( rImposable >= limitesTranchesFiscales[i] && rImposable < limitesTranchesFiscales[i+1] ) {
                montantImpot += ( rImposable - limitesTranchesFiscales[i] ) * tauxImpositionTranches[i];
                break;
            } else {
                montantImpot += ( limitesTranchesFiscales[i+1] - limitesTranchesFiscales[i] ) * tauxImpositionTranches[i];
            }
            i++;
        } while( i < 5);

        montantImpot = montantImpot * nombreParts;
        montantImpot = Math.round( montantImpot );

        System.out.println( "Impôt brut du foyer fiscal complet : " + montantImpot );

        // Vérification de la baisse d'impôt autorisée
        // EXIGENCE : EXG_IMPOT_05
        // baisse impot

        double baisseImpot = montantImpotDeclarants - montantImpot;

        System.out.println( "Baisse d'impôt : " + baisseImpot );

        // dépassement plafond
        double ecartPts = nombreParts - nombrePartsDeclarants;

        double plafond = (ecartPts / 0.5) * plafDemiPart;

        System.out.println( "Plafond de baisse autorisée " + plafond );

        if ( baisseImpot >= plafond ) {
            montantImpot = montantImpotDeclarants - plafond;
        }

        System.out.println( "Impôt brut après plafonnement avant decote : " + montantImpot );
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
