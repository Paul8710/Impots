package CalculateurTest;

import Calculateurs.CalculateurHautRevenu;
import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateurHautRevenuTest {

    private final CalculateurHautRevenu calculateur = new CalculateurHautRevenu();

    @ParameterizedTest(name = "CEHR pour {1} avec RFR = {0}")
    @CsvSource({
            // Célibataire
            "200000, CELIBATAIRE, 0.0",
            "300000, CELIBATAIRE, 1500.0",
            "750000, CELIBATAIRE, 17500.0",
            "1500000, CELIBATAIRE, 47500.0",

            // Couple
            "400000, MARIE, 0.0",
            "750000, MARIE, 7500.0",
            "1500000, MARIE, 35000.0",

            // Divorcé/veuf (comme célibataire)
            "1000000, DIVORCE, 27500.0",
            "1000000, VEUF, 27500.0"
    })
    public void testCalculCEHR(double revenuFiscalReference, SituationFamiliale situation, double expectedCehr) {
        double result = calculateur.calculerCEHR(revenuFiscalReference, situation);
        assertEquals(expectedCehr, result,("Erreur"));
    }
}
