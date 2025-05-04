package Calculateurs;

import com.kerware.simulateur.SituationFamiliale;

public class CalculateurAbattements {
    /**
     * Calcule l'abattement en prenant en compte les revenus nets des déclarants.
     * Prend en compte la situation du couple, et abattement borné entre
     * une limiteminimale et une limite maximale.
     *
     * @param revenuNetDeclarant1 revenu net du premier déclarant
     * @param revenuNetDeclarant2 revenu net du second déclarant (s'il y en a un)
     * @param limiteMinAbattement valeur minimale de l'abattement
     * @param limiteMaxAbattement valeur maximale de l'abattement
     * @param situationFamiliale  la situation familiale (marié, célibataire...)
     * @return le revenu fiscal de référence après abattement,
     * ou 0 si le calcul donne un résultat négatif
     */
    public double calculerAbattement(
            double revenuNetDeclarant1,
            double revenuNetDeclarant2,
            double tauxAbattement,
            int limiteMinAbattement,
            int limiteMaxAbattement,
            SituationFamiliale situationFamiliale) {

        boolean estCouple = situationFamiliale == SituationFamiliale.MARIE
                || situationFamiliale == SituationFamiliale.PACSE;

        double abattement1 = calculerAbattementIndividuel(revenuNetDeclarant1, tauxAbattement,
                limiteMinAbattement, limiteMaxAbattement);
        double abattement2 = 0;
        if (estCouple) {
            abattement2 = calculerAbattementIndividuel(
                    revenuNetDeclarant2,
                    tauxAbattement,
                    limiteMinAbattement,
                    limiteMaxAbattement
            );
        }

        return abattement1 + abattement2;
    }

    /**
     * Calcule l'abattement sur le revenu net en fonction d'un taux donné,
     * tout en le bornant entre une valeur minimale et maximale
     *
     * @param revenuNet           le revenu net sur lequel appliquer l'abattement
     * @param tauxAbattement      le taux d'abattement à appliquer
     * @param limiteMinAbattement la valeur minimale de l'abattement autorisé
     * @param limiteMaxAbattement la valeur maximale de l'abattement autorisé
     * @return l'abattement calculé, borné entre min et max
     */
    private double calculerAbattementIndividuel(
            double revenuNet,
            double tauxAbattement,
            int limiteMinAbattement,
            int limiteMaxAbattement) {

        double abattement = Math.round(revenuNet * tauxAbattement);
        abattement = Math.min(abattement, limiteMaxAbattement);
        abattement = Math.max(abattement, limiteMinAbattement);
        return abattement;
    }
}


