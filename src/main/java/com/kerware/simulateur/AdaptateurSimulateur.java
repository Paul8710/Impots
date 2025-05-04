package com.kerware.simulateur;

public class AdaptateurSimulateur implements ICalculateurImpot {

    private Simulateur simulateur = new Simulateur();

    private int revenusNetDeclarant1 = 0;
    private int revenusNetDeclarant2 = 0;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    /**
     * @param revenusNetDecl1 le revenu du déclarant 2.
     */
    @Override
    public void setRevenusNetDeclarant1(int revenusNetDecl1) {
        this.revenusNetDeclarant1 = revenusNetDecl1;
    }

    /**
     * @param revenusNetDecl2 le revenu du déclarant 2.
     */
    @Override
    public void setRevenusNetDeclarant2(int revenusNetDecl2) {
        this.revenusNetDeclarant2 = revenusNetDecl2;
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

    /**
     * Appel la fonction permettant de calculer les impots sur le revenu net
     */
    @Override
    public void calculImpotSurRevenuNet() {
        simulateur.calculImpot(revenusNetDeclarant1, revenusNetDeclarant2, situationFamiliale,
                nbEnfantsACharge, nbEnfantsSituationHandicap, parentIsole);
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
     * @return les impots vis à vis du revenu net.
     */
    @Override
    public int getImpotSurRevenuNet() {
        return (int) simulateur.getImpotNet();
    }
}
