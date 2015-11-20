import java.util.function.Function;

public class InstallmentCalculation {

    public static class Params {

        /** Balance */
        private final double b;

        /** Split-balance ratio */
        private final double sbr;

        /** Debt reduction percentage */
        private final double h;

        /** Annual interest rate */
        private final double ir;

        /** Tenor */
        private final int t;

        /** Grace period */
        private final int gp;

        public Params(double b, double sbr, double h, double ir, int t, int gp) {
            this.b = b;
            this.sbr = sbr;
            this.h = h;
            this.ir = ir;
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

        public double getT() {
            return t;
        }

        public double getGp() {
            return gp;
        }

    }

    public static Params params(double b, double sbr, double h, double ir, int t, int gp) {
        return new Params(b, sbr, h, ir, t, gp);
    }

    public static final Function<Params, Double> FUNCTION = p -> {
        if (p.ir == 0) {
            return p.b / p.t;
        }

        double result = p.b;
        result *= (1 - p.sbr);
        result += (1 - p.h);
        double tmp = Math.pow(1 + p.ir / 12, p.t - p.gp);
        result *= tmp;
        result *= p.ir / 12;
        result /= (tmp - 1);

        return result;
    };

    public static final Function<Params, Double> FUNCTION_GRACE_PERIOD = p ->
            p.b * p.ir / p.gp;

}
