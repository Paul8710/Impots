package com.kerware.simulateur;

public class AdaptateurSimulateur implements ICalculateurImpot {

    private Simulateur simulateur = new Simulateur();

    private int revenusNetDecl1 = 0;
    private int revenusNetDecl2 = 0;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    /**
     * @param revenusNetDecl1 le revenu du déclarant 2.
     */
    @Override
    public void setRevenusNetDeclarant1(int revenusNetDecl1) {
        this.revenusNetDecl1 = revenusNetDecl1;
    }

    /**
     * @param revenusNetDecl2 le revenu du déclarant 2.
     */
    @Override
    public void setRevenusNetDeclarant2(int revenusNetDecl2) {
        this.revenusNetDecl2 = revenusNetDecl2;
    }

    /**
     * @param situationFam la situation familiale.
     */
    @Override
    public void setSituationFamiliale(SituationFamiliale situationFam) {
        this.situationFamiliale = situationFam;
    }

    /**
     * @param nombreEnfantsACharge le nombre d'enfants à charge.
     */
    @Override
    public void setNbEnfantsACharge(int nombreEnfantsACharge) {
        this.nbEnfantsACharge = nombreEnfantsACharge;
    }

    /**
     * @param nombreEnfantsAChargeSituationHandicap le nombre d'enfants
     * en situation de handicap à charge.
     */
    @Override
    public void setNbEnfantsSituationHandicap(int nombreEnfantsAChargeSituationHandicap) {
        this.nbEnfantsSituationHandicap = nombreEnfantsAChargeSituationHandicap;
    }

    /**
     * @param parentEstIsole si le parents est isolé ou non.
     */
    @Override
    public void setParentIsole(boolean parentEstIsole) {
        this.parentIsole = parentEstIsole;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        simulateur.calculImpot(revenusNetDecl1, revenusNetDecl2, situationFamiliale,
                nbEnfantsACharge, nbEnfantsSituationHandicap, parentIsole);
    }

    /**
     * @return le revenu du déclarant 1.
     */
    @Override
    public int getRevenuNetDeclatant1() {
        return revenusNetDecl1;
    }

    /**
     * @return le revenu du déclarant 2.
     */
    @Override
    public int getRevenuNetDeclatant2() {
        return revenusNetDecl2;
    }

    /**
     * @return a contribution exceptionnelle.
     */
    @Override
    public double getContribExceptionnelle() {
        return simulateur.getContribExceptionnelle();
    }

    /**
     * @return le revenu fiscal de référence.
     */
    @Override
    public int getRevenuFiscalReference() {
        return (int) simulateur.getRevenuReference();
    }

    /**
     * @return l'abattement possible au sein d'un foyer
     */
    @Override
    public int getAbattement() {
        return (int) simulateur.getAbattement();
    }

    /**
     * @return le nombre de part au sein d'un foyer.
     */
    @Override
    public double getNbPartsFoyerFiscal() {
        return simulateur.getNbParts();
    }

    /**
     * @return le montant des impots avant la décote.
     */
    @Override
    public int getImpotAvantDecote() {
        return (int) simulateur.getImpotAvantDecote();
    }

    /**
     * @return la décote.
     */
    @Override
    public int getDecote() {
        return (int) simulateur.getDecote();
    }

    /**
     * @return les impots vis à vis du revenu net.
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}
