package fragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BoysTest {

    private Boys boys;

    @Before
    public void setUp() throws Exception
    {
        boys = new Boys();
    }

    @Test
    public void testCalculation() throws Exception
    {
        assertNotNull(boys.calculation(50f, 50f, 60f, 25f, 23f, 33f));
        assertEquals(25f, boys.calculation(60f, 50f, 60f, 25f, 23f, 33f), 0);
    }

    @Test
    public void testcalculateDeltaT() throws Exception
    {
        assertNotNull(boys.calculateDeltaT(50f, 60f));
        assertEquals(10f, boys.calculateDeltaT(23f, 33f), 0);
    }

    @Test
    public void testcalculateFlowRate() throws Exception
    {
        assertNotNull(boys.calculateFlowRate(60f));
        assertEquals(2f, boys.calculateFlowRate(120f), 0);
    }

    @Test
    public void testcalculateGasFlowRate() throws Exception
    {
        assertNotNull(boys.calculateGasFlowRate(60f, 80f, 25f));
        assertEquals(2f, boys.calculateGasFlowRate(30f, 80f, 25f), 0);
    }

}