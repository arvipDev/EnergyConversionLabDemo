package fragment;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Aru on 06-05-2016.
 */
public class SayboltTest {

    private Saybolt saybolt;

    @Before
    public void setUp() throws Exception
    {
        saybolt = new Saybolt();
    }
    @Test
    public void testCalculateDynamicViscosity() throws Exception
    {
        assertNotNull(saybolt.calculateDynamicViscosity(4f, 10f));
        assertEquals(40f, saybolt.calculateDynamicViscosity(4f, 10f), 0);
    }

    @Test
    public void testCalculatekinematicViscosity() throws Exception
    {
        assertNotNull(saybolt.calculatekinematicViscosity(4f, 10f, 20f));
        assertEquals(35f, saybolt.calculatekinematicViscosity(4f, 10f, 20f), 0);
    }

    @Test
    public void testCalculateDensity() throws Exception
    {
        assertNotNull(saybolt.calculateDensity(50f, 99f));
        // can not perform assertEquals because the application allows user to change constants involved in the formulae
    }
}