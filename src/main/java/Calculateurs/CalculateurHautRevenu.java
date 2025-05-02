package Calculateurs;

import FoyerFiscal.Parametres2024;
import com.kerware.simulateur.SituationFamiliale;

import static FoyerFiscal.Parametres2024.*;

public class CalculateurHautRevenu {
    /**
     * Calcule la contribution exceptionnelle sur les hauts revenus
     *
     * @param revenuFiscalReference revenu après abattement
     * @param situation situation familiale (célibataire ou couple)
     * @return montant de la contribution exceptionnelle, arrondi
     */
    public double calculerCEHR(double revenuFiscalReference, SituationFamiliale situation) {
        double cehr = 0.0;
        double[] taux;
        if(estCelibataire(situation)){
            taux = tauxCEHRCelibataire;
        }else{
            taux = tauxCEHRCouple;
        }

        for (int i = 0; i < taux.length; i++) {
            if (revenuFiscalReference > limitesCEHR[i]) {
                double plafond = Math.min(revenuFiscalReference, limitesCEHR[i + 1]);
                cehr += (plafond - limitesCEHR[i]) * taux[i];
            } else {
                break;
            }
        }

        return Math.round(cehr);
    }

    private boolean estCelibataire(SituationFamiliale sf) {
        return sf == SituationFamiliale.CELIBATAIRE
                || sf == SituationFamiliale.DIVORCE
                || sf == SituationFamiliale.VEUF;
    }
}

