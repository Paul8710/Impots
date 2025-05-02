package Calculateurs;

import FoyerFiscal.Parametres2024;
import com.kerware.simulateur.SituationFamiliale;

public class CalculateurImpotBrut {

    /**
     *
     */
    public double calculerImpotBrut(double revenuFiscalReference, SituationFamiliale situationFamiliale, double nombrePartsDeclarants) {
        double revenuParPart = revenuFiscalReference / nombrePartsDeclarants;
        double montantImpotFoyer = 0;
        int i = 0;

        int[] limitesTranchesFiscales = Parametres2024.LIMITES_TRANCHES_IMPOT;
        double[] tauxImpositionTranches = Parametres2024.TRANCHES_TAUX_IMPOSITION;

        while (i < tauxImpositionTranches.length) {
            if (revenuParPart >= limitesTranchesFiscales[i] && revenuParPart < limitesTranchesFiscales[i + 1]) {
                montantImpotFoyer += (revenuParPart - limitesTranchesFiscales[i]) * tauxImpositionTranches[i];
                break;
            } else {
                montantImpotFoyer += (limitesTranchesFiscales[i + 1] - limitesTranchesFiscales[i]) * tauxImpositionTranches[i];
            }
            i++;
        }

        return Math.round(montantImpotFoyer*nombrePartsDeclarants);
    }
}
