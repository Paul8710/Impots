package Calculateurs;

import static FoyerFiscal.Parametres2024.*;

public class CalculateurBarèmeProgressif {
    /**
     * Cette méthode calcule l'impôt sur le revenu basé sur le barème progressif,
     * en fonction du revenu total et du nombre de parts fiscales.
     *
     * @param revenu       Le revenu total du foyer fiscal
     * @param nombreParts Le nombre de parts fiscales
     * @return L'impôt total arrondi à l'euro près
     */
    public double calculerImpot(double revenu, double nombreParts) {
        if (revenu < 0 || nombreParts <= 0) {
            throw new IllegalArgumentException("Revenu ou nombre de parts invalide.");
        }

        double revenuParPart = revenu / nombreParts;
        double impotParPart = 0;

        System.out.println("Revenu par part: " + revenuParPart);

        // Parcours de chaque tranche fiscale
        for (int i = 0; i < TRANCHES.length - 1; i++) {
            int borneInf = TRANCHES[i];
            int borneSup = TRANCHES[i + 1];
            double taux = TAUX[i];

            // Affichage des tranches et taux
            System.out.println("Tranche " + (i + 1) + ": [" + borneInf + ", " + borneSup + "] avec taux " + taux);

            if (revenuParPart > borneSup) {
                // Tranche complète
                impotParPart += (borneSup - borneInf) * taux;
                System.out.println("Impot par part (tranche complète): " + impotParPart);
            } else if (revenuParPart > borneInf) {
                // Tranche partielle
                impotParPart += (revenuParPart - borneInf) * taux;
                System.out.println("Impot par part (tranche partielle): " + impotParPart);
                break;
            }
        }

        // Calcul total pour toutes les parts
        double impotTotal = Math.round(impotParPart * nombreParts);
        System.out.println("Impôt total (arrondi): " + impotTotal);

        return impotTotal;
    }
}
