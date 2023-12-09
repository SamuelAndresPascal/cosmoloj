package com.cosmoloj.math.tabular.n;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleVariantTabularN extends DefaultDoubleIndicedTabularN implements DoubleVariantTabularN {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final IndexFactory IF = IndexFactory.getInstance();

    private final Variant[] variants;

    public DefaultDoubleVariantTabularN(final Object tabular, final VariantIndex... vindices) {
        super(tabular, vindices);
        variants = new Variant[vindices.length];
        for (int i = 0; i < vindices.length; i++) {
            variants[i] = vindices[i].getVariant();
        }
    }

    @Override
    public Variant getDimensionVariant(final int index) {
        return variants[index];
    }

    public static DoubleVariantTabularN generalMult(final DoubleVariantTabularN t1, final DoubleVariantTabularN t2) {

        final Map.Entry<VariantIndex[], NamedIndex[]> prepareStructure = prepareStructure(t1, t2);
        final var builder = new DefaultDoubleVariantTabularNBuilder(double.class, prepareStructure.getKey());
        final NamedIndex[] sumIndex = prepareStructure.getValue();

        final char[] indexes = builder.getIndexes();
        final int[] sumDimensions = NamedIndex.getDimensions(sumIndex);
        final char[] sumNames = NamedIndex.getNames(sumIndex);

        final int[] t1SumPos = NamedIndex.getPositions(sumNames, t1.getIndexes());
        final int[] t2SumPos = NamedIndex.getPositions(sumNames, t2.getIndexes());

        final int[] t1IndexIterator = new int[t1.getOrder()];
        final int[] t2IndexIterator = new int[t2.getOrder()];

        final Iterator<int[]> it = builder.iterator();
        int[] resultIndexIterator;
        while (it.hasNext()) {
            resultIndexIterator = it.next();

            // Préparation du tableau d'index pour t1 : détermination des indices non sommés.
            for (final char c : indexes) {
                final int i = builder.getPosition(c);

                int t1p = t1.getPosition(c);
                if (t1p >= 0) {
                    t1IndexIterator[t1p] = resultIndexIterator[i];
                }

                int t2p = t2.getPosition(c);
                if (t2p >= 0) {
                    t2IndexIterator[t2p] = resultIndexIterator[i];
                }
            }

            // Calcul de la valeur.
            double value;
            if (sumIndex.length == 0) {

                // Dans le cas où il n'y a pas d'indices sommés, la détermination des indices non sommés suffit.
                value = t1.get(t1IndexIterator) * t2.get(t2IndexIterator);
            } else {

                value = 0;
                // Dans le cas où il y a des indices sommés, il faut parcourir leurs combinaisons
                final Iterator<int[]> ti = new TabularIterator(sumDimensions);
                while (ti.hasNext()) {
                    final int[] id = ti.next();

                    // On parcourt chacun des indices de la combinaison
                    for (int k = 0; k < id.length; k++) {

                        final int t1Pos = t1SumPos[k];
                        final int t2Pos = t2SumPos[k];
                        t1IndexIterator[t1Pos] = id[k];
                        t2IndexIterator[t2Pos] = id[k];
                    }
                    value += t1.get(t1IndexIterator) * t2.get(t2IndexIterator);
                }
            }

            builder.setValue(value, resultIndexIterator);
        }

        return new DefaultDoubleVariantTabularN(builder.getTabular(), prepareStructure.getKey());
    }

    /**
     * <div class="fr">Calcul des index résultant d'un produit de tableaux indicés.</div>
     *
     * @param t1
     * @param t2
     * @return
     */
    static Entry<VariantIndex[], NamedIndex[]> prepareStructure(final DoubleVariantTabularN t1,
            final DoubleVariantTabularN t2) {

        final int toRepeatedWithoutSum = 0;
        final int fromT1 = 1;
        final int fromT2 = 2;

        final int order1 = t1.getOrder();
        final int order2 = t2.getOrder();

        // Calcul du nombrefinal d'indices
        int finalDim = 0;

        // Tableau provisoire des indices non sommés
        final VariantIndex[] provIndex = new VariantIndex[order1 + order2];

        // Tableau provisoire des indices sommés
        final NamedIndex[] provSumIndex = new NamedIndex[order1 + order2];
        // Curseur des indices sommés (nombre d'indices sommés)
        int sumNb = 0;

        // Examen des indices de t1.
        for (int i = 0; i < order1; i++) {
            final char i1 = t1.getIndex(i);
            final Variant v1 = t1.getDimensionVariant(i);

            boolean repeatedWithSum = false; // Same indices, one covariant and one contravariant
            boolean repeatedWithoutSum = false; // Same indices, both covariant or both contravariant

            // Examen des indices de t2
            for (int j = 0; j < order2; j++) {
                final char i2 = t2.getIndex(j);

                if (i1 == i2) {
                    if (!v1.equals(t2.getDimensionVariant(j))) {
                        // On range l'index dans le tableau des indices sommés.
                        provSumIndex[sumNb] = new NamedIndex(i1, t1.getDimension(i));
                        sumNb++; // On incrémente le curseur des indices sommés.
                        repeatedWithSum = true; // Sommation : l'index ne sera pas retenu dans le tenseur de sortie
                    } else {
                        // Sinon, les deux indices sont contra- ou co- variants.
                        // on ne fait pas la sommation et on retient donc l'indice dans le tenseur de sortie.
                        // Mais on mémorise que les deux indices sont identiques.
                        repeatedWithoutSum = true;
                    }
                    // Qu'il soit sommé ou pas, on sort à partir du moment où l'on a détecté que l'indice est répété.
                    break;
                }
            }

            // Indices du tenseur de sortie : on ne retient pas les indices sommés.
            if (!repeatedWithSum) {
                provIndex[finalDim] = IF.get(i1, repeatedWithoutSum ? toRepeatedWithoutSum : fromT1, v1);
                finalDim++;
            }
        }

        // Création du tableau définitif des indices répétés sommés.
        final NamedIndex[] sumIndex = Arrays.copyOf(provSumIndex, sumNb);

        // Examen des indices de t2 qui n'étaient pas dans t1
        for (int j = 0; j < order2; j++) {
            final char i2 = t2.getIndex(j);
            final Variant v2 = t2.getDimensionVariant(j);

            boolean present = false;

            for (int i = 0; i < order1; i++) {
                final char i1 = t1.getIndex(i);

                if (i1 == i2) {
                    present = true;
                    break;
                }
            }

            if (!present) {
                provIndex[finalDim] = IF.get(i2, fromT2, v2);
                finalDim++;
            }
        }

        final VariantIndex[] defIndex = Arrays.copyOf(provIndex, finalDim);

        // Calcul des dimensions
        for (final VariantIndex v : defIndex) {
            // Cas d'indices identiques répétés non sommés
            switch (v.getDimension()) {
                case toRepeatedWithoutSum:
                    v.setDimension(t1.getDimension(v.getName()));
                    break;
                case fromT1:
                    v.setDimension(t1.getDimension(v.getName())); // *t2ns;
                    break;
                case fromT2:
                    v.setDimension(t2.getDimension(v.getName())); // *t1ns;
                    break;
                default:
                    break;
            }
        }

        return Map.entry(defIndex, sumIndex);
    }
}
