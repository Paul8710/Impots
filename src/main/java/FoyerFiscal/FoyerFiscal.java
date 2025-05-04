package FoyerFiscal;

import com.kerware.simulateur.SituationFamiliale;

public class FoyerFiscal {

    private final double revenuNetImposable;
    private final SituationFamiliale situationFamiliale;
    private final int nombreEnfants;

    /**
     * Constructeur de la classe
     *
     * @param revenuImposableNet Revenut net Imposable
     * @param situationFam SituationFamiliale
     * @param nbEnfants      Le nombre d'enfants
     */
    public FoyerFiscal(double revenuImposableNet, SituationFamiliale situationFam,
                       int nbEnfants) {
        if (revenuImposableNet < 0) {
            throw new IllegalArgumentException("Le revenu net imposable ne peut pas être négatif.");
        }
        if (nbEnfants < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif.");
        }

        this.revenuNetImposable = revenuImposableNet;
        this.situationFamiliale = situationFam;
        this.nombreEnfants = nbEnfants;
    }

    /**
     * @return Le revenu net imposable
     */
    public double getRevenuNetImposable() {
        return revenuNetImposable;
    }

    /**
     * @return La situation familiale
     */
    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    /**
     * @return le nombre d'enfant
     */
    public int getNombreEnfants() {
        return nombreEnfants;
    }
}
