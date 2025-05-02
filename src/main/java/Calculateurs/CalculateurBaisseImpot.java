package Calculateurs;

import static FoyerFiscal.Parametres2024.PLAFOND_DEMI_PART;

public class CalculateurBaisseImpot {

    /**
     * Calcule l'impôt après application du quotient familial et plafonnement éventuel.
     *
     * @param montantImpotDeclarants Impôt sans parts supplémentaires
     * @param montantImpot Impôt avec parts totales
     * @param nombrePartsDeclarants Nombre de parts des seuls déclarants
     * @param nombreParts Nombre total de parts fiscales du foyer
     * @return Montant d'impôt après plafonnement
     */
    public double calculerBaisseImpot(double montantImpotDeclarants, double montantImpot,
                                      double nombrePartsDeclarants, double nombreParts) {
        double baisseImpot = montantImpotDeclarants - montantImpot;
        System.out.println("Baisse d'impôt : " + baisseImpot);

        double ecartPts = nombreParts - nombrePartsDeclarants;
        double plafond = (ecartPts / 0.5) * PLAFOND_DEMI_PART;
        System.out.println("Plafond de baisse autorisée : " + plafond);

        if (baisseImpot >= plafond) {
            montantImpot = montantImpotDeclarants - plafond;
        }

        System.out.println("Impôt brut après plafonnement avant décote : " + montantImpot);
        return montantImpot;
    }
}
