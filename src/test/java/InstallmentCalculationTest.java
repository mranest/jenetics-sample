import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstallmentCalculationTest {

    @Test
    public void testMe() {
        InstallmentCalculation.Params params1 = InstallmentCalculation.params(100000, 0, 0, 0.0592, 180, 0);
        assertEquals(839.54, InstallmentCalculation.FUNCTION.apply(params1), 0.01);

        InstallmentCalculation.Params params2 = InstallmentCalculation.params(100000, 0, 0, 0.0592, 180, 12);
        assertEquals(493.33, InstallmentCalculation.GRACE_PERIOD_FUNCTION.apply(params2), 0.01);
        assertEquals(876.98, InstallmentCalculation.FUNCTION.apply(params2), 0.01);

        InstallmentCalculation.Params params3 = InstallmentCalculation.params(100000, 0, 0, 0, 180, 12);
        assertEquals(555.56, InstallmentCalculation.FUNCTION.apply(params3), 0.01);
    }

}
