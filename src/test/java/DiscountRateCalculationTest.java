import org.junit.Test;

public class DiscountRateCalculationTest {

    @Test
    public void testMe() {
        DiscountRateCalculation.Params params = DiscountRateCalculation.params(100000, 0, 0, 0.0592, 0.10, 300, 12);

        double dr = DiscountRateCalculation.FUNCTION.apply(params);
        System.out.println(dr);
    }

}
