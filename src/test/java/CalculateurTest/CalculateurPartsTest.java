package CalculateurTest;

import Calculateurs.CalculateurParts;
import FoyerFiscal.FoyerFiscal;
import com.kerware.simulateur.SituationFamiliale;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateurPartsTest {

    private final CalculateurParts calculateur = new CalculateurParts();

    @ParameterizedTest(name = "Situation = {0}  enfant(s)= {1} et part(s)Attendues = {2} ")
    @CsvSource({
            "CELIBATAIRE, 0, 1.0",
            "CELIBATAIRE, 1, 1.5",
            "CELIBATAIRE, 2, 2.0",
            "CELIBATAIRE, 3, 3.0",
            "MARIE, 0, 2.0",
            "MARIE, 2, 3.0",
            "MARIE, 4, 5.0",
            "PACSE, 1, 2.5",
            "DIVORCE, 2, 2.0",
            "VEUF, 3, 3.0"
    })
    public void testCalculerParts(String situation, int nbEnfants, double expectedParts) {
        SituationFamiliale situationFamiliale = SituationFamiliale.valueOf(situation);
        FoyerFiscal foyer = new FoyerFiscal(50000, situationFamiliale, nbEnfants);
        double result = calculateur.calculerParts(foyer.getSituationFamiliale(), foyer.getNombreEnfants(), 0, false);
        assertEquals(expectedParts, result, 0.001);
    }
}
