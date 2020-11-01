package com.cosmoloj.time;

/**
 * Classe utilitaire de constantes det d'algorithmes utilitaires relatives à
 * l'année julienne commençant en mars.
 *
 * @author Samuel Andrés
 */
public final class JulianMarchUtil {

    private JulianMarchUtil() {
    }

    public static final int MONTHS_PER_DECA_MENSUAL_CYCLE = 10;
    public static final int DAYS_PER_DECA_MENSUAL_CYCLE = 306;

    /**
     * Nombre de mois dans le cycle penta-mensuel.
     *
     * Le cycle penta-mensuel démarre en mars. La première période du cycle est
     * constituée par les mois de mars, avril, mai, juin et juillet qui
     * totalisent 153 jours répartis selon le schéma : 31|30|31|30|31. La
     * seconde est constituée par les mois d'aout, septembre, octobre, novembre
     * et décembre selon un shéma identique.
     */
    public static final int MONTHS_PER_PENTA_MENSUAL_CYCLE = 5;

    /**
     * Nombre de jours dans le cycle penta-mensuel.
     */
    public static final int DAYS_PER_PENTA_MENSUAL_CYCLE = 153;

    /**
     * Jour de l'année étant donné le nombre de jours depuis l'époque de départ,
     * pour une année donnée, dans le calendrier grégorien en considérant le
     * début de l'année au 1er mars.
     *
     * On retire :
     * - autant d'années de 365 jours,
     * - un jour supplémentaire chaque année divisible par quatre (années bissextiles ordinaires)
     *
     * @param zeroDay
     * @param year
     * @return
     */
    public static long dayOfYear(final long zeroDay, final long year) {
        return zeroDay - (365 * year + year / 4);
    }

    /**
     * Cette méthode permet de trouver le numéro du mois à partir du numéro du
     * jour dans l'année. Elle se base pour cela sur le cycle penta-mensuel :
     *
     *    31       30       31      30       31
     *   mars |  avril  |  mai  |  juin  |juillet
     *   aout |septembre|octobre|novembre|décembre
     * janvier| février
     *
     * Le fait que février ne compte que 28 ou 29 jours en rompant ainsi le cycle
     * n'a pas d'importance dans la mesure où une nouvelle année commence en mars
     * suivant et cela ne perturbe donc pas le calcul.
     *
     * Du fait de l'inégalité en nombre de jours des mois composant, le (+2)
     * sert à ajuster la transition entre avril et mai d'une part et entre juin
     * et juillet d'autre part.
     *
     * @param marchDoy0 Jour de l'année commençant en mars (le permier mars
     * correspond au jour 0).
     * @return Le numéro du moi dans l'année commençant en mars (mars correspond
     * au mois 0).
     */
    public static int getMarchMonth0(final int marchDoy0) {
        return (marchDoy0 * MONTHS_PER_PENTA_MENSUAL_CYCLE + 2) / DAYS_PER_PENTA_MENSUAL_CYCLE;
    }

    /**
     *
     * @param marchDoy0
     * @param marchMonth0
     * @return
     */
    public static int getMarchDayOfMonth0(final int marchDoy0, final int marchMonth0) {
        return marchDoy0 - (marchMonth0 * DAYS_PER_DECA_MENSUAL_CYCLE + 5) / MONTHS_PER_DECA_MENSUAL_CYCLE;
    }

    /**
     *
     * @param marchDoy0
     * @param marchMonth0
     * @return
     */
    public static int getMarchDayOfMonth(final int marchDoy0, final int marchMonth0) {
        return getMarchDayOfMonth0(marchDoy0, marchMonth0) + 1;
    }

    /**
     *
     * @param marchMonth0
     * @param offset
     * @return
     */
    public static int changeMonthOrder0(final int marchMonth0, final int offset) {
        return (marchMonth0 + offset) % JulianUtil.MONTHS_PER_YEAR;
    }

    /**
     *
     * @param marchMonth0
     * @param offset
     * @return
     */
    public static int changeMonthOrder(final int marchMonth0, final int offset) {
        return changeMonthOrder0(marchMonth0, offset) + 1;
    }

    /**
     * Construction d'une date à partir d'un jour dans l'année julienne
     * commençant en mars (le 1er mars est le jour 0) dans un calendrier dont
     * les mois sont identiques mais qui débute par un autre mois de l'année.
     *
     * @param marchDoy0 Numéro du jour dans l'année commençant le 1er mars.
     * @param marchYear Numéro de l'année de mars.
     * @param offset Décalage du début de l'année (de 0 à 11 mois plus tôt).
     * @return La date exprimé dans l'année commençant
     */
    public static YearMonthDayDate translate(final int marchDoy0, final long marchYear, final int offset) {
        if (offset < 0 || offset > 11) {
            throw new IllegalArgumentException();
        }
        final int marchMonth0 = JulianMarchUtil.getMarchMonth0(marchDoy0);
        final int month = JulianMarchUtil.changeMonthOrder(marchMonth0, offset);
        final int dom = JulianMarchUtil.getMarchDayOfMonth(marchDoy0, marchMonth0);
        final long year = marchYear + marchMonth0 / (JulianUtil.MONTHS_PER_YEAR - offset);
        return new YearMonthDayDate((int) year, month, dom);
    }

    /**
     *
     * @param marchDoy0
     * @param marchYear
     * @param toMonth
     * @return
     */
    public static YearMonthDayDate translate(final int marchDoy0, final long marchYear,
            final JulianMarchMonth toMonth) {
        return translate(marchDoy0, marchYear, JulianUtil.MONTHS_PER_YEAR - toMonth.compareTo(JulianMarchMonth.MARCH));
    }

    /**
     *
     * @param date Une date de l'année commençant par mars repérée par l'année
     * et le jour de l'année, dans laquelle le premier jour de l'année est
     * numéroté 0.
     *
     * @param toMonth
     * @return
     */
    public static YearMonthDayDate translate(final YearDayDate date, final JulianMarchMonth toMonth) {
        return translate(date.getDayOfYear(), date.getYear(),
                JulianUtil.MONTHS_PER_YEAR - toMonth.compareTo(JulianMarchMonth.MARCH));
    }

    /**
     *
     * @param zeroDay
     * @return
     */
    public static YearDayDate toDayOfYear(long zeroDay) {

        long adjust = 0;

        // Si on est avant l'ère chrétienne :
        if (zeroDay < 0) {
            // adjust negative years to positive for calculation
            long adjustCycles = (zeroDay + 1) / JulianUtil.DAYS_PER_CYCLE - 1;
            adjust = adjustCycles * JulianUtil.YEARS_PER_CYCLE;
            zeroDay += -adjustCycles * JulianUtil.DAYS_PER_CYCLE;
        }

        long yearEst = JulianUtil.YEARS_PER_CYCLE * zeroDay / JulianUtil.DAYS_PER_CYCLE;
        long doyEst = JulianMarchUtil.dayOfYear(zeroDay, yearEst);
        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = JulianMarchUtil.dayOfYear(zeroDay, yearEst);
        }
        yearEst += adjust;  // reset any negative year

        return new YearDayDate((int) doyEst, yearEst);
    }
}
