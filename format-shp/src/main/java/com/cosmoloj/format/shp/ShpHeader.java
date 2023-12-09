package com.cosmoloj.format.shp;

/**
 * <div class="fr">
 * En-tête de fichier de forme ESRI (.shp).
 * </div>
 *
 * @author Samuel Andrés
 */
public record ShpHeader(

    /**
     * <div class="fr">Taille du fichier en nombre de mots de 16 bits (en-tête et enregistrements confondus).</div>
     *
     * @return <span class="fr">taille du fichier en nombre de mots de 16 bits</span>
     */
    int length,

    /**
     * <div class="fr">Type de forme.</div>
     *
     * @return <span class="fr">code du type de forme</span>
     * @see ShapeType
     * @see ShapeCode
     */
    int shapeType,

    // BBox : emprise spatiale

    /**
     * <div class="fr">Valeur minimale de l'emprise spatiale selon la dimension 1.</div>
     *
     * @return <span class="fr">valeur minimale de l'emprise spatiale selon la dimension 1</span>
     */
    double xMin,

    /**
     * <div class="fr">Valeur minimale de l'emprise spatiale selon la dimension 2.</div>
     *
     * @return <span class="fr">valeur minimale de l'emprise spatiale selon la dimension 2</span>
     */
    double yMin,

    /**
     * <div class="fr">Valeur maximale de l'emprise spatiale selon la dimension 1.</div>
     *
     * @return <span class="fr">valeur maximale de l'emprise spatiale selon la dimension 1</span>
     */
    double xMax,

    /**
     * <div class="fr">Valeur maximale de l'emprise spatiale selon la dimension 2.</div>
     *
     * @return <span class="fr">valeur maximale de l'emprise spatiale selon la dimension 2</span>
     */
    double yMax,

    /**
     * <div class="fr">Valeur minimale de l'emprise spatiale selon la dimension 3. Vaut {@literal 0.} lorsque
     * le type de forme est de dimension 2.</div>
     *
     * @return <span class="fr">valeur minimale de l'emprise spatiale selon la dimension 3</span>
     */
    double zMin,

    /**
     * <div class="fr">Valeur maximale de l'emprise spatiale selon la dimension 3. Vaut {@literal 0.} lorsque
     * le type de forme est de dimension 2.</div>
     *
     * @return <span class="fr">valeur maximale de l'emprise spatiale selon la dimension 3</span>
     */
    double zMax,

    /**
     * <div class="fr">Valeur minimale de l'emprise spatiale selon la dimension 4 (mesure). Vaut {@literal 0.} lorsque
     * le type de forme ne porte pas de mesure.</div>
     *
     * @return <span class="fr">valeur minimale de l'emprise spatiale selon la dimension 4</span>
     */
    double mMin,

    /**
     * <div class="fr">Valeur maximale de l'emprise spatiale selon la dimension 4 (mesure). Vaut {@literal 0.} lorsque
     * le type de forme ne porte pas de mesure.</div>
     *
     * @return <span class="fr">valeur maximale de l'emprise spatiale selon la dimension 4</span>
     */
    double mMax) {

    /**
     * <div class="fr">Longueur d'une en-tête.</div>
     */
    public static final int HEADER_LENGTH = 100;

    public double[][] toBbox() {
        return new double[][] {
            {xMin, yMin, zMin, mMin},
            {xMax, yMax, zMax, mMax}};
    }
}
