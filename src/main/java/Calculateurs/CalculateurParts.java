package Calculateurs;

import com.kerware.simulateur.SituationFamiliale;

public class CalculateurParts {
    private static final double VALEUR_DEMI_PART = 0.5;

    /**
     * Calcul le nombre de parts à l'aide du FoyerFiscal
     *
     * @param situationFamiliale      la situation familiale (marié, célibataire...)
     * @param nombreEnfants           le nombre d'enfant à charge
     * @param nombreEnfantsHandicapes le nombre d'enfant handicapé à charge
     * @param parentIsole             si le parent est isolé
     * @return Le nombre de parts du foyer
     */
    public double calculerParts(SituationFamiliale situationFamiliale, int nombreEnfants,
                                int nombreEnfantsHandicapes, boolean parentIsole) {
        double nombreParts = this.nombrePartsSituationFamiliale(situationFamiliale);

        if (nombreEnfants <= 2) {
            nombreParts += nombreEnfants * VALEUR_DEMI_PART;
        } else {
            nombreParts += 1.0 + (nombreEnfants - 2);
        }

        if (parentIsole && nombreEnfants > 0) {
            nombreParts = nombreParts + VALEUR_DEMI_PART;
        }

        // Veuf avec enfant
        if (situationFamiliale == SituationFamiliale.VEUF && nombreEnfants > 0) {
            nombreParts = nombreParts + 1;
        }

        // enfant handicapé
        nombreParts = nombreParts + nombreEnfantsHandicapes * VALEUR_DEMI_PART;

        return nombreParts;
    }

    /**
     * Calcul le nombre de parts à l'aide du FoyerFiscal
     *
     * @param situationFamiliale la situation familiale (marié, célibataire...)
     * @return Le nombre de parts déclaré du foyer
     */
    public double nombrePartsSituationFamiliale(SituationFamiliale situationFamiliale) {
        double nombreParts = 1.0;

        switch (situationFamiliale) {
            case MARIE, PACSE:
                nombreParts = 2.0;
                break;
            case CELIBATAIRE, DIVORCE, VEUF:
                nombreParts = 1.0;
                break;
            default:
                throw new IllegalArgumentException("Situation familiale inconnue : "
                        + situationFamiliale);
        }
        return nombreParts;
    }
}