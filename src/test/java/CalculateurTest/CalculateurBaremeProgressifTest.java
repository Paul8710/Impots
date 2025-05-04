package CalculateurTest;

import Calculateurs.CalculateurBaremeProgressif;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculateurBaremeProgressifTest {

    private final CalculateurBaremeProgressif calculateur = new CalculateurBaremeProgressif();

    @ParameterizedTest(name = "revenu={0}, parts={1}, impot attendu={2}")
    @CsvSource({
            // Aucun impôt
            "5000, 1, 0",
            // Dans tranche 1
            "15000, 1, 408",
            // Dans tranche 2
            "32000, 1, 2886",
            // Dans tranche 3
            "45000, 1, 6786",
            // plafond
            "200000, 1, 67144"
    })
    void testCalculBarèmeProgressif(double revenu, double parts, double impotAttendu) {
        double result = calculateur.calculerImpot(revenu, parts);
        System.out.println("Résultat attendu: " + impotAttendu + " Résultat calculé: " + result);
        assertEquals(impotAttendu, result, 1.0);  // Tolérance de 1.0 pour les petites erreurs de calcul
    }

    @ParameterizedTest
    @CsvSource({
            "-1000, 1",
            "30000, 0",
            "50000, -2"
    })
    void testParametresInvalides(double revenu, double parts) {
        assertThrows(IllegalArgumentException.class, () -> calculateur.calculerImpot(revenu, parts));
    }
}
