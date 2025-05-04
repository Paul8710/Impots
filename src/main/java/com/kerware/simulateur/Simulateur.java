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


    /**
     * Calcule l'impôt sur le revenu net du foyer en fonction des paramètres fournis,
     * en France pour l'année 2024 sur les revenus de 2023.
     *
     * @param revenuNetDeclarant1 Revenu net imposable du déclarant 1
     * @param revenuNetDeclarant2 Revenu net imposable du déclarant 2
     *                            (0 si pas de déclarant 2)
     * @param situationFamiliale Situation familiale du foyer fiscal (célibataire, marié ...)
     * @param nombreEnfants Nombre d’enfants au sein du foyer
     * @param nombreEnfantsHandicapes Nombre d’enfants en situation de handicap au sein du foyer
     * @param parentIsole Indique si le foyer est constitué d’un parent isolé
     * @return Le montant d’impôt net dû par le foyer fiscal (arrondi à l’euro le plus proche)
     * @throws IllegalArgumentException si une incohérence est détectée dans les paramètres
     */
    public int calculImpot( int revenuNetDeclarant1, int revenuNetDeclarant2,
                            SituationFamiliale situationFamiliale, int nombreEnfants,
                            int nombreEnfantsHandicapes, boolean parentIsole) {

        // Préconditions
        new ValidateurDonneesCalculImpot().validerDonnees(
                revenuNetDeclarant1, revenuNetDeclarant2, situationFamiliale,
                nombreEnfants, nombreEnfantsHandicapes, parentIsole
        );

        // Abattement - EXIGENCE : EXG_IMPOT_02
        montantAbattement = new CalculateurAbattements().calculerAbattement(
                revenuNetDeclarant1, revenuNetDeclarant2, TAUX_MONTANT_ABATTEMENT,
                LIMITE_MONTANT_ABATTEMENT_MIN, LIMITE_MONTANT_ABATTEMENT_MAX, situationFamiliale);

        revenuFiscalReference = Math.max(0, revenuNetDeclarant1 + revenuNetDeclarant2
                - montantAbattement);

        // parts déclarants - EXIG  : EXG_IMPOT_03
        nombrePartsDeclarants= new CalculateurParts()
                .nombrePartsSituationFamiliale(situationFamiliale);

        nombreParts = new CalculateurParts().calculerParts(situationFamiliale, nombreEnfants,
                nombreEnfantsHandicapes, parentIsole);


        // Contribution exceptionnelle sur les hauts revenus - EXIGENCE : EXG_IMPOT_07:
        contributionExceptionnelle = new CalculateurHautRevenu().calculerCEHR(
                revenuFiscalReference, situationFamiliale);

        // EXIGENCE : EXG_IMPOT_04 - Calcul impôt des declarants
        montantImpotDeclarants = new CalculateurBaremeProgressif().calculerImpot(
                revenuFiscalReference, nombrePartsDeclarants);

        // EXIGENCE : EXG_IMPOT_04 - Calcul impôt foyer fiscal complet
        montantImpot = new CalculateurBaremeProgressif()
                .calculerImpot(revenuFiscalReference, nombreParts);

        // EXIGENCE : EXG_IMPOT_05 - Vérification de la baisse d'impôt autorisée
        montantImpot = new CalculateurBaisseImpot().calculerBaisseImpot(
                montantImpotDeclarants, montantImpot, nombrePartsDeclarants, nombreParts);
        montantImpotAvantDecote = montantImpot;

        // EXIGENCE : EXG_IMPOT_06 - Calcul de la decote
        decote = new CalculateurDecote().calculerDecote(montantImpot, situationFamiliale);

        montantImpot = Math.round( (montantImpot - decote) + contributionExceptionnelle );

        return  (int)montantImpot;
    }
}
