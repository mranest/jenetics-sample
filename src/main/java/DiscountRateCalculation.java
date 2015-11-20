import java.util.function.Function;

public class DiscountRateCalculation {

    public static class Params {

        /** Balance */
        private final double b;

        /** Split-balance ratio */
        private final double sbr;

        /** Debt reduction percentage */
        private final double h;

        /** Interest rate */
        private final double ir;

        /** Interest rate of deferred principal balance */
        private final double irDpb;

        /** Tenor */
        private final int t;

        /** Grace period */
        private final int gp;

        public Params(double b, double sbr, double h, double ir, double irDpb, int t, int gp) {
            this.b = b;
            this.sbr = sbr;
            this.h = h;
            this.ir = ir;
            this.irDpb = irDpb;
            this.t = t;
            this.gp = gp;
        }

        public double getB() {
            return b;
        }

        public double getSbr() {
            return sbr;
        }

        public double getH() {
            return h;
        }

        public double getIr() {
            return ir;
        }

        public double getIrDpb() {
            return irDpb;
        }

        public double getT() {
            return t;
        }

        public double getGp() {
            return gp;
        }

    }

    public static Params params(double b, double sbr, double h, double ir, double irDpb, int t, int gp) {
        return new Params(b, sbr, h, ir, irDpb, t, gp);
    }

    public static final Function<Params, Double> FUNCTION = p -> {
        double ii = InstallmentCalculation.FUNCTION.apply(
                InstallmentCalculation.params(p.b, p.sbr, p.h, p.ir, p.t, p.gp)
        );
        double iiGp = p.gp == 0 ? 0 :
                InstallmentCalculation.FUNCTION_GRACE_PERIOD.apply(
                        InstallmentCalculation.params(p.b, p.sbr, p.h, p.ir, p.t, p.gp));

        double tmp = Math.pow(1 + p.irDpb / 12, p.t);
        double nominatorSum = 0, denominatorSum = 0;
        for (int i = 0; i < p.t; i++) {
            double nominator = tmp;
            nominator *= 1 - p.h;
            nominator *= p.sbr;
            nominator *= p.b;
            nominator *= p.t;
            nominator += i * (i < p.gp ? iiGp : ii);
            nominatorSum += nominator;

            double denominator = tmp;
            denominator *= 1 - p.h;
            denominator *= p.sbr;
            denominator *= p.b;
            denominator += (i < p.gp ? iiGp : ii);

            denominatorSum += denominator;
        }

        return nominatorSum / denominatorSum;
    };

}
