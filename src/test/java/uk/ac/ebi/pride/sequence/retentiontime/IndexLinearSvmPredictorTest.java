package uk.ac.ebi.pride.sequence.retentiontime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yperez
 * Date: 8/26/13
 * Time: 10:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexLinearSvmPredictorTest {

    IndexLinearSvmPredictor calculator;
    @Before
    public void setUp() throws Exception {
        calculator = IndexLinearSvmPredictor.getInstance(null, true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetCoefficients() throws Exception {
        double[] coefficentValues = calculator.getCoefficients();
        System.out.println(coefficentValues.length);
        assertTrue(20 == coefficentValues.length);
    }
}
