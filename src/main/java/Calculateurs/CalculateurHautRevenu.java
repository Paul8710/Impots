package Calculateurs;

import com.kerware.simulateur.SituationFamiliale;

import static FoyerFiscal.Parametres2024.TAUX_CEHR_CELIBATAIRE;
import static FoyerFiscal.Parametres2024.TAUX_CEHR_COUPLE;
import static FoyerFiscal.Parametres2024.LIMITES_CEHR;


public class CalculateurHautRevenu {
    /**
     * Calcule la contribution exceptionnelle sur les hauts revenus
     *
     * @param revenuFiscalReference revenu après abattement
     * @param situation             situation familiale (célibataire ou couple)
     * @return montant de la contribution exceptionnelle, arrondi
     */
    public double calculerCEHR(double revenuFiscalReference, SituationFamiliale situation) {
        double cehr = 0.0;
        double[] taux;
        if (estCelibataire(situation)) {
            taux = TAUX_CEHR_CELIBATAIRE;
        } else {
            taux = TAUX_CEHR_COUPLE;
        }

        for (int i = 0; i < taux.length; i++) {
            if (revenuFiscalReference > LIMITES_CEHR[i]) {
                double plafond = Math.min(revenuFiscalReference, LIMITES_CEHR[i + 1]);
                cehr += (plafond - LIMITES_CEHR[i]) * taux[i];
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

