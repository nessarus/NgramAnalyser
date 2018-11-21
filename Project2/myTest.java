

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class myTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class myTest
{
    /**
     * Default constructor for test class myTest
     */
    public myTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testingL()
    {
        MarkovModel markovMo2 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.5000, markovMo2.laplaceEstimate("aac"), 0.0001);
        assertEquals(0.1667, markovMo2.laplaceEstimate("aaa"), 0.0001);
        assertEquals(0.3333, markovMo2.laplaceEstimate("aab"), 0.0001);
    }
}

