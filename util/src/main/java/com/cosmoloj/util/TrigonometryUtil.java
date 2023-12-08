package com.cosmoloj.util;

/**
 * <div class="fr">
 * Classe utilitaire de trigonométrie.
 * </div>
 *
 * <div class="en">
 * Utilitary class of trigonometry.
 * </div>
 *
 * @author Samuel Andrés
 */
public final class TrigonometryUtil {

    private TrigonometryUtil() {
    }

    /**
     * <div class="fr">
     * Méthode utilitaire de conversion d'une mesure d'angle en radian depuis sa valeur en degrés, minutes d'arc et
     * secondes d'arc.
     * </div>
     *
     * <div class="en">
     * Utilitary conversion method from an angle measurement in degree, arcminutes and arcseconds to radian.
     * </div>
     *
     * @param degree <span class="fr">Nombre de degrés dans la mesure de l'angle.</span>
     * @param arcminutes <span class="fr">Nombre de minutes d'arc dans la mesure de l'angle.</span>
     * @param arcseconds <span class="fr">Nombre de secondes d'arc dans la msesure de l'angle.</span>
     * @return <span class="fr">Mesure de l'angle exprimée en radians.</span>
     */
    public static double dmsToRadians(final double degree, final double arcminutes, final double arcseconds) {
        return (degree * 3600. + arcminutes * 60. + arcseconds) / 3600. * 2 * Math.PI / 360.;
    }

    /**
     * <div class="fr">
     * Méthode utilitaire de conversion d'une mesure d'angle en radian depuis sa valeur en heures, minutes et secondes.
     * </div>
     *
     * <div class="en">
     * Utilitary conversion method from an angle measurement in hours, minutes and seconds to radian.
     * </div>
     *
     * @param hours <span class="fr">Nombre d'heures dans la mesure de l'angle.</span>
     * @param minutes <span class="fr">Nombre de minutes dans la mesure de l'angle.</span>
     * @param seconds <span class="fr">Nombre de secondes dans la mesure de l'angle.</span>
     * @return <span class="fr">Mesure de l'angle exprimée en radians.</span>
     */
    public static double hmsToRadians(final double hours, final double minutes, final double seconds) {
        return (hours * 24. * 60. + minutes * 60. + seconds) / 24. / 60. * 2 * Math.PI / 24.;
    }
}
