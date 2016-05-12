package fragment;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class JunkersTest {

    private Junkers junkers;

    @Before
    public void setUp() throws Exception
    {
        junkers = new Junkers();
    }

    @Test
    public void testCalculation()throws Exception
    {
        assertNotNull(junkers.calculation(50f, 23f, 33f));
        // can not perform assertEquals because the application allows user to change constants involved in the formulae
    }

    @Test
    public void testCalculateDeltaT()throws Exception
    {
        assertNotNull(junkers.calculateDeltaT(23f, 33f));
        assertEquals(10f, junkers.calculateDeltaT(33f, 23f));
    }

    @Test
    public void testCalculateGasMass() throws Exception
    {
        assertNotNull(junkers.calculateGasMass(10f, 2f));
        assertEquals(10f, junkers.calculateGasMass(5f, 2f));
    }
}