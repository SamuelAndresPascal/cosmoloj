package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.trials.DoubleVectorTrials;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.math.util.set.polynomial.PolynomialUtil;
import java.util.stream.DoubleStream;

/**
 *
 * @author Samuel Andrés
 */
abstract class AbstractPolynomialNonInversibleTransformation extends AbstractPolynomialTransformation {

    private final double[][] ar;
    private final double[][] br;

    protected AbstractPolynomialNonInversibleTransformation(final double sourceEval1, final double sourceEval2,
            final double targetEval1, final double targetEval2, final double sourceScaling, final double targetScaling,
            final double[][]a, final double[][] b) {
        super(sourceEval1, sourceEval2, targetEval1, targetEval2, sourceScaling, targetScaling, a, b);
        this.ar = DoubleTabulars.external(-1., a);
        this.br = DoubleTabulars.external(-1., b);
    }

    /*
    On définit une méthode inverse par défaut mais on n'implémente pas d'interface d'opération inversible. Ainsi
    on pourra éviter de détecter ces méthodes comme systématiquement inversibles, mais si une implémentation veut
    également implémentar une interface inversible elle pourra disposer de cette méthode inverse par défaut.
    */
    public double[] inverse(final double[] input) {
        return this.new InverseTrials(input).loop();
    }

    private class InverseTrials implements DoubleVectorTrials {

        private final double[] t0;

        InverseTrials(final double[] init) {
            this.t0 = init;
        }

        @Override
        public double[] init() {
            return this.t0;
        }

        @Override
        public double[] trial(final double[] in) {
            final double u = (in[0] - xs()) * ms();
            final double v = (in[1] - ys()) * ms();

            final double[] al = new double[ar.length];
            final double[] bl = new double[br.length];
            for (int i = 0; i < ar.length; i++) {
                al[i] = PolynomialUtil.hornerAsc(ar[i], v);
                bl[i] = PolynomialUtil.hornerAsc(br[i], v);
            }
            final double mtdx = PolynomialUtil.hornerAsc(al, u);
            final double mtdy = PolynomialUtil.hornerAsc(bl, u);

            return new double[]{
                in[0] - xs() + xt() + mtdx / mt(),
                in[1] - ys() + yt() + mtdy / mt()
            };
        }

        @Override
        public boolean test(final double[] first, final double[] second) {
            return DoubleTabulars.max(DoubleStream.of(DoubleTabulars.minus(first, second)).map(Math::abs).toArray())
                    < 1e-9;
        }

        @Override
        public double[] loop() {

            /*
            La documentation des formules de l'OGC indique que les formules polynomiales générales ne sont pas
            inversibles du point de vue mathématique mais peuvent malgré tout être inversées en procédant par
            itérations. Aucune documentation n'est néanmoins indiquée et le document Guidance Note number 7 - 2
            indique que "the iteration procedure is usually described by the information source of the polynomial
            transformation.

            On voit mal cependant comment faire converger un polynôme.

            "information_source" de la transformation 1041 (méthode 9648) indique "Use iteration for reverse
            transformation ETRS89 to TM75." Même constat pour la transformation 1042 qui utilise la même méthode.

            On ne trouve aucune documentation sur le web, et en particulier aucune documentation détaillée pour cette
            transformation par l'Ordnance Survey (ni UK ni Ireland).

            La seule documentation exploitable et crédible sur le sujet se trouve ici :

            https://w3.leica-geosystems.com/downloads123/gb/gps/gps1200/other/
            os%20transformations%20and%20osgm02%20user%20guide_en.pdf

            Mais la seule procédure d'itération qui s'y trouve ne concerne pas la même transformation.

            En attendant, on utilise une implémentation provisoire qui se base sur un décalage pour retrouver le point
            du système cible à l'origine de la transformation inverse.
            */
            double[] tn = t0;

            while (true) {
                final double[] sn = trial(tn);
                final double[] tntmp = tn;
                tn = compute(sn);

                if (test(tn, t0)) {
                    return sn;
                }

                tn = DoubleTabulars.add(tntmp, DoubleTabulars.minus(t0, tn));
            }
        }
    }
}
