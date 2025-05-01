package Calculateurs;

import FoyerFiscal.FoyerFiscal;

public class CalculateurParts {

    /**
     * Calcul le nombre de parts à l'aide du FoyerFiscal
     * @param foyer Le FoyerFiscal à calculer
     * @return Le nombre de parts du foyer
     */
    public double calculerParts(FoyerFiscal foyer) {
        double parts = 1.0;

        switch (foyer.getSituationFamiliale()) {
            case MARIE:
            case PACSE:
                parts = 2.0;
                break;
            case CELIBATAIRE:
            case DIVORCE:
            case VEUF:
                parts = 1.0;
                break;
            default:
                throw new IllegalArgumentException("Situation familiale inconnue : " + foyer.getSituationFamiliale());
        }

        int nbEnfants = foyer.getNombreEnfants();

        if (nbEnfants <= 2) {
            parts += nbEnfants * 0.5;
        } else {
            parts += 1.0 + (nbEnfants - 2);
        }

        return parts;
    }
}