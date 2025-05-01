package Calculateurs;

import FoyerFiscal.FoyerFiscal;
import com.kerware.simulateur.SituationFamiliale;

public class CalculateurParts {

    /**
     * Calcul le nombre de parts à l'aide du FoyerFiscal
     * @param situationFamiliale la situation familiale (marié, célibataire...)
     * @return Le nombre de parts du foyer
     */
    public double calculerParts(SituationFamiliale situationFamiliale, int nombreEnfants, int nombreEnfantsHandicapes, boolean parentIsole) {
        double nombreParts = 1.0;

        switch (situationFamiliale) {
            case MARIE, PACSE:
                nombreParts = 2.0;
                break;
            case CELIBATAIRE, DIVORCE,VEUF:
                nombreParts = 1.0;
                break;
            default:
                throw new IllegalArgumentException("Situation familiale inconnue : " + situationFamiliale);
        }

        if (nombreEnfants <= 2) {
            nombreParts += nombreEnfants * 0.5;
        } else {
            nombreParts += 1.0 + (nombreEnfants - 2);
        }

        if ( parentIsole ) {
            if ( nombreEnfants > 0 ){
                nombreParts = nombreParts + 0.5;
            }
        }

        // Veuf avec enfant
        if ( situationFamiliale == SituationFamiliale.VEUF && nombreEnfants > 0 ) {
            nombreParts = nombreParts + 1;
        }

        // enfant handicapé
        nombreParts = nombreParts + nombreEnfantsHandicapes * 0.5;

        return nombreParts;
    }
}