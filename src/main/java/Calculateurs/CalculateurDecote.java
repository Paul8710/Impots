package Calculateurs;

import FoyerFiscal.Parametres2024;
import com.kerware.simulateur.SituationFamiliale;

public class CalculateurDecote {

    /**
     * Calcule la décote de l'impôt sur le revenu en fonction des revenus nets et de la situation familiale.
     * La décote est bornée entre un min et un max
     *
     * @param impotBrut le montant brut de l'impôt avant la décote
     * @param situationFamiliale la situation familiale (marié, célibataire...)
     * @return le montant de la décote appliquée s'il y en a une, sinon 0
     */
    public double calculerDecote(double impotBrut, SituationFamiliale situationFamiliale) {
        double decote = 0;

        if (situationFamiliale == SituationFamiliale.MARIE || situationFamiliale == SituationFamiliale.PACSE) {
            if (impotBrut < Parametres2024.SEUIL_DECOTE_COUPLE) {
                decote = Parametres2024.DECOTE_MAX_COUPLE - (impotBrut * Parametres2024.TAUX_DECOTE);
            }
        } else {
            if (impotBrut < Parametres2024.SEUIL_DECOTE_CELIBATAIRE) {
                decote = Parametres2024.DECOTE_MAX_CELIBATAIRE - (impotBrut * Parametres2024.TAUX_DECOTE);
            }
        }

        decote = Math.round(decote);

        if (impotBrut <= decote) {
            decote = impotBrut;
        }

        return decote;
    }
}
