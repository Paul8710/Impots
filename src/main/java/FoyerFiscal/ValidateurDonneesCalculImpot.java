package FoyerFiscal;

import com.kerware.simulateur.SituationFamiliale;

public class ValidateurDonneesCalculImpot {


    /**
     * Valide les données passées en paramètre pour le calcul de l'impôt.
     * Lance une exception si une des conditions n'est pas respéctée.
     *
     * @param revenuNetDeclarant1 revenu du déclarant 1
     * @param revenuNetDeclarant2 revenu du déclarant 2
     * @param situationFamiliale situation familiale du foyer (marié, pacsé...)
     * @param nombreEnfants nombre total d'enfants
     * @param nombreEnfantsHandicapes nombre d'enfants total en situation de handicap
     * @param parentIsole si le foyer est un parent isolé
     */
    public void validerDonnees(int revenuNetDeclarant1, int revenuNetDeclarant2,
                                 SituationFamiliale situationFamiliale, int nombreEnfants,
                                 int nombreEnfantsHandicapes, boolean parentIsole) {

        // Préconditions
        if (revenuNetDeclarant1 < 0 || revenuNetDeclarant2 < 0) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif");
        }

        if (nombreEnfants < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants ne peut pas être négatif");
        }

        if (nombreEnfantsHandicapes < 0) {
            throw new IllegalArgumentException(
                    "Le nombre d'enfants handicapés ne peut pas être négatif"
            );
        }

        if (situationFamiliale == null) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être null");
        }

        if (nombreEnfantsHandicapes > nombreEnfants) {
            throw new IllegalArgumentException(
                    "Le nombre d'enfants handicapés ne peut pas être supérieur au nombre d'enfants"
            );
        }

        if (nombreEnfants > 7) {
            throw new IllegalArgumentException(
                    "Le nombre d'enfants ne peut pas être supérieur à 7"
            );
        }

        if (parentIsole && (situationFamiliale == SituationFamiliale.MARIE
                || situationFamiliale == SituationFamiliale.PACSE)) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé");
        }

        boolean seul = situationFamiliale == SituationFamiliale.CELIBATAIRE
                || situationFamiliale == SituationFamiliale.DIVORCE
                || situationFamiliale == SituationFamiliale.VEUF;
        if (seul && revenuNetDeclarant2 > 0) {
            throw new IllegalArgumentException(
                    "Un célibataire, un divorcé ou un veuf " +
                            "ne peut pas avoir de revenu pour le déclarant 2"
            );
        }
    }
}
