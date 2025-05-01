package CalculateurTest;

import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.DisplayName;
import Calculateurs.CalculateurDecote;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateurDecoteTest {

    private final CalculateurDecote calculateur = new CalculateurDecote();

    public static Stream<Arguments> donneesPourDecote() {
        return Stream.of(
                Arguments.of(1500.0, SituationFamiliale.CELIBATAIRE, 194.0),  // Décote partielle pour célibataire
                Arguments.of(1929.0, SituationFamiliale.CELIBATAIRE, 0.0),    // Pas de décote car = seuil
                Arguments.of(2000.0, SituationFamiliale.CELIBATAIRE, 0.0),    // Pas de décote car > seuil
                Arguments.of(2500.0, SituationFamiliale.MARIE, 313.0),        // Décote partielle pour couple
                Arguments.of(3191.0, SituationFamiliale.MARIE, 0.0),          // Pas de décote car = seuil
                Arguments.of(3200.0, SituationFamiliale.MARIE, 0.0),          // Pas de décote car > seuil
                Arguments.of(3191.0, SituationFamiliale.PACSE, 0.0)           // Pas de décote car = seuil
        );
    }

    @DisplayName("Test du calcul de la décote pour différents foyers fiscaux")
    @ParameterizedTest(name = "Impôt brut={0}, Situation={1} => Décote attendue={2}")
    @MethodSource("donneesPourDecote")
    public void testCalculerDecote(double impotBrut, SituationFamiliale situationFamiliale, double decoteAttendue) {
        double decote = calculateur.calculerDecote(impotBrut, situationFamiliale);
        assertEquals(decoteAttendue, decote, 0.01, "La décote calculée est incorrecte");
    }
}
