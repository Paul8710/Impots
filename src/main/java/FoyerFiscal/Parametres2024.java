package FoyerFiscal;

public class Parametres2024 {

    // Seuils de la décote
    public static final double SEUIL_DECOTE_CELIBATAIRE = 1929.0;
    public static final double SEUIL_DECOTE_COUPLE = 3191.0;

    // Maximum de la décote
    public static final double DECOTE_MAX_CELIBATAIRE = 873.0;
    public static final double DECOTE_MAX_COUPLE = 1444.0;

    // Taux de la décote
    public static final double TAUX_DECOTE = 0.4525;

    //
    public static int[] limitesCEHR = { 0, 250000, 500000, 1000000, Integer.MAX_VALUE };
    // Taux impots sur les revenues exceptionelles
    public static final double[] tauxCEHRCelibataire = { 0.0, 0.03, 0.04, 0.04 };
    public static final double[] tauxCEHRCouple =      { 0.0, 0.0,  0.03, 0.04 };
}