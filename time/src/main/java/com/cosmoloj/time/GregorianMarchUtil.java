package com.cosmoloj.time;

/**
 *
 * @author Samuel Andrés
 */
public final class GregorianMarchUtil {

    private GregorianMarchUtil() {
    }

    /**
     * Jour de l'année étant donné le nombre de jours depuis l'époque de départ,
     * pour une année donnée, dans le calendrier grégorien en considérant le
     * début de l'année au 1er mars.
     *
     * On commence par appliquer la transformation julienne dans l'année
     * débutant en mars.
     *
     * Puis :
     * - on ajoute un jour tous les siècles à cause des premières années de siècles qui ne sont pas bissextiles,
     * - et on retire un jour tous 400 ans parce qu'une année de siècle sur 4 est malgré tout bissextile.
     *
     * @param zeroDay
     * @param year
     * @return
     */
    public static long dayOfYear(final long zeroDay, final long year) {
        return JulianMarchUtil.dayOfYear(zeroDay, year) + year / 100 - year / 400;
    }

    /**
     *
     * @param zeroDay
     * @return
     */
    public static YearDayDate toDayOfYear(long zeroDay) {

        /*
        Nombre nul pour les nombre de jours positifs et négatif pour les nombres
        de jours négatifs. Ces derniers étant transformés en nombre de jours
        positifs pendant le premier cycle le temps du calcul, "adjust" indique
        la correction à apporter pour retrouver l'année négative véritable.
        */
        long adjust = 0;
        if (zeroDay < 0) {
            /*
             zeroDay = 0 : c'est le 01/03/0000


             Si on est avant le 01/03/0000, on fait en sorte de retomber sur un
             calcul identique que pour les valeurs de jours positives et on
             repassera en négatif après le calcul.

             Cette correction commence donc à partir de zeroDay = -1 et pour
             toutes les valeurs négatives, c'est-à-dire avant le 01/03/0000.

             ____________________________________
             cycles n° :    4     3     2     1  |

                           01/03/-300        01/03/0000              01/03/400
                               v                 v                       v
             |_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|_____|
             adjustCycles: -4    -3    -2    -1     /     /     /     /     /
             adjust:      -1600 -1200 -800  -400    0     0     0     0     0

             adjust negative years to positive for calculation

             Commentaire à propos de adjustCycles.
             =====================================
             Il reste nul pour les années positives.
             Pour les années négatives, il commence à -1 et est décrémenté de 1
             chaque premier jour de nouveau cycle dans la direction du passé.
             Le premier jour du premier cycle des années négatives vaut -1.
             Pour effectuer le calcul de adjustCycles, il faut le ramener à 0,
             c'est-à-dire y ajouter 1 et ainsi de suite pour tous les jours
             antérieurs pour lesquels adjustCycles sera calculé.

             Commentaire à propos de adjust.
             ===============================
             adjust reste nul pour les années positives.
             Pour les années négatives, il n'intervient pas directement mais
             sera ajouté au nombre d'années final pour revenir en négatif.
            */

            long adjustCycles = (zeroDay + 1) / GregorianUtil.DAYS_PER_CYCLE - 1;
            adjust = adjustCycles * GregorianUtil.YEARS_PER_CYCLE; // Nombre d'années à retirer

            /*
             Commentaire sur le nombre de jours.
             ===================================
             De sa valeur négative, il est transformé en sa valeur positive au
             cours du premier cycle :

                                                 01/03/0000
                                                     V
                                     d               | d'
                      <----------------------------->|<->
             _|______x___|__________|_ _ _ _ _ _ _ _ |___x______|__________|____
                      <-> <-------------------------> <->
                            "-adjustCycles" cycles
            */
            zeroDay += -adjustCycles * GregorianUtil.DAYS_PER_CYCLE;
        }

        // écart approché en entre le nombre de jour en moyenne par année grégorienne réel (365,2425)
        // et sa valeur approchée par division entière (365)


        // DAYS_PER_CYCLE : nombre de jours par cycle grégorien de 400 ans
        // zeroDay : nombre de jours écoulés depuis le 01/03/0000
        /*
        => 591 : moyen de compenser l'écart induit par l'inégalité en nombre de
        jours des années composant la période de 400 ans. Ceci garantit qu'aucun
        jour n'indique en première approximation une année inférieure à l'année
        grégorienne à laquelle il appartient. Toutefois, ceci peut avoir comme
        effet de bord que l'année peut être légèrement surestimée.

        Pour corriger cette erreur, il suffit de vérifier que le jour ne tombe
        pas avant le début de l'année.
        */
        long yearEst = (GregorianUtil.YEARS_PER_CYCLE * zeroDay + 591) / GregorianUtil.DAYS_PER_CYCLE;
        long doyEst = GregorianMarchUtil.dayOfYear(zeroDay, yearEst);
        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = GregorianMarchUtil.dayOfYear(zeroDay, yearEst);
        }
        yearEst += adjust;  // reset any negative year

        return new YearDayDate((int) doyEst, yearEst);
    }
}
