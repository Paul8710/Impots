package FoyerFiscal;

import com.kerware.simulateur.SituationFamiliale;

public class FoyerFiscal {

    private final double revenuNetImposable;
    private final SituationFamiliale situationFamiliale;
    private final int nombreEnfants;

    /**
     * Constructeur de la classe
     * @param revenuNetImposable Revenut net Imposable
     * @param situationFamiliale SituationFamiliale
     * @param nombreEnfants Le nombre d'enfants
     */
    public FoyerFiscal(double revenuNetImposable, SituationFamiliale situationFamiliale, int nombreEnfants) {
        if (revenuNetImposable < 0) {
            throw new IllegalArgumentException("Le revenu net imposable ne peut pas être négatif.");
        }
        if (nombreEnfants < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif.");
        }

        this.revenuNetImposable = revenuNetImposable;
        this.situationFamiliale = situationFamiliale;
        this.nombreEnfants = nombreEnfants;
    }

    public double getRevenuNetImposable() {
        return revenuNetImposable;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }
}
