package CalculateurTest;

import Calculateurs.CalculateurBaisseImpot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateurBaisseImpotTest {

    private final CalculateurBaisseImpot calculateur = new CalculateurBaisseImpot();

    public static Stream<Arguments> donneesPourBaisseImpot() {
        return Stream.of(
                // baisse < plafond → pas de changement
                Arguments.of(8000.0, 7500.0, 2.0, 2.5, 7500.0),
                // baisse == plafond → pas de changement
                Arguments.of(9000.0, 5482.0, 2.0, 3.0, 5482.0), // baisse = 3518 = 2 * PLAFOND_DEMI_PART
                // baisse > plafond → plafonnement
                Arguments.of(9000.0, 5000.0, 2.0, 3.0, 5482.0), // baisse = 4000 > plafond = 3518
                // aucune baisse → inchangé
                Arguments.of(6000.0, 6000.0, 2.0, 2.0, 6000.0)
        );
    }

    @DisplayName("Test du plafonnement de la baisse d'impôt liée au quotient familial")
    @ParameterizedTest(name = "Impot déclarants={0}, Impot avec parts={1}, parts déclarants={2}, parts foyer={3} => Impôt attendu={4}")
    @MethodSource("donneesPourBaisseImpot")
    public void testCalculerBaisseImpot(double impotDeclarants, double impotAvecParts,double partsDeclarants, double partsFoyer,double impotAttendu) {
        double resultat = calculateur.calculerBaisseImpot(
                impotDeclarants,
                impotAvecParts,
                partsDeclarants,
                partsFoyer
        );
        assertEquals(impotAttendu, resultat, 0.01, "L’impôt après plafonnement est incorrect");
    }
}
