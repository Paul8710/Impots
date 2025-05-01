package CalculateurTest;

import Calculateurs.CalculateurAbattements;
import FoyerFiscal.FoyerFiscal;
import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateurAbattementsTest {

    private final CalculateurAbattements calculateur = new CalculateurAbattements();

    public static Stream<Arguments> fournirDonneesPourAbattement() {
        return Stream.of(
                Arguments.of(4000, 0, SituationFamiliale.CELIBATAIRE, 4000 - 495),         // < min
                Arguments.of(12000, 0, SituationFamiliale.CELIBATAIRE, 12000 - 1200),      // normal
                Arguments.of(200000, 0, SituationFamiliale.CELIBATAIRE, 200000 - 14171),   // > max
                Arguments.of(10000, 10000, SituationFamiliale.MARIE, 20000 - 2000),        // couple, normal
                Arguments.of(4000, 3000, SituationFamiliale.PACSE, 7000 - (495 + 495)),    // couple, < min
                Arguments.of(150000, 160000, SituationFamiliale.MARIE, 310000 - (14171 + 14171)) // couple, > max
        );
    }

    @DisplayName("Test du calcul de l'abattement pour différents foyers fiscaux")
    @ParameterizedTest(name = "R1={0}, R2={1}, Situation={2} => RFR attendu={3}")
    @MethodSource("fournirDonneesPourAbattement")
    public void testCalculerAbattement(double revenuNetDeclarant1, double revenuNetDeclarant2, SituationFamiliale situationFamiliale, double revenuFiscalAttendu) {
        FoyerFiscal foyer = new FoyerFiscal(50000, situationFamiliale, 0);

        double tauxAbattement = 0.10;
        int abattementMin = 495;
        int abattementMax = 14171;

        double revenuFiscalReference = calculateur.calculerAbattement(revenuNetDeclarant1, revenuNetDeclarant2, tauxAbattement, abattementMin, abattementMax, situationFamiliale);

        assertEquals(revenuFiscalAttendu, revenuFiscalReference, 1.0, "Le revenu fiscal de référence est incorrect");
    }
}
