package com.kerware.simulateur;

public interface ICalculateurImpot {

    public void setRevenusNetDeclarant1( int revenusNetDeclarant1 );
    public void setRevenusNetDeclarant2( int revenusNetDeclarant2 );
    public void setSituationFamiliale( SituationFamiliale situationFamiliale );
    public void setNbEnfantsACharge( int nbEnfantsACharge );
    public void setNbEnfantsSituationHandicap( int nbEnfantsSituationHandicap );
    public void setParentIsole( boolean parentIsole );

    public void calculImpotSurRevenuNet();
    public int getAbattement();
    public double getNbPartsFoyerFiscal();
    public int getImpotSurRevenuNet();

}
