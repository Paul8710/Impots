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

    public static final double PLAFOND_DEMI_PART = 1759.0;

    // Tranches de revenu imposable pour l'année 2024
    public static final int[] TRANCHES = { 0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE };

    // Taux d'imposition correspondant à chaque tranche
    public static final double[] TAUX = { 0.0, 0.11, 0.30, 0.41, 0.45 };

    // Les limites des tranches de revenus imposables
    public static final int[] LIMITES_TRANCHES_IMPOT = { 0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE };

    // Les taux d'imposition par tranche
    public static final double[] TRANCHES_TAUX_IMPOSITION = { 0.0, 0.11, 0.3, 0.41, 0.45 };
}