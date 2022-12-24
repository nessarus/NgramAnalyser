import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * The test class ProjectTest for student test cases.
 * Add all new test cases to this task.
 *
 * @author  Joshua Ng (20163079), Mohamed Yusuf (22273364)
 * @version (24/5/2017)
 */
public class ProjectTest
{
    /**
     * Default constructor for test class ProjectTest
     */
    public ProjectTest()
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

    //TODO add new test cases from here include brief documentation
    
    /**
     * Tests the MarkovModel on validation of input.
     */
    @Test(timeout=1000 , expected = IllegalArgumentException.class)
    public void testSensibleToStringSize() 
    {
        var m = new MarkovModel(2, "c");
        var k = new MarkovModel(2, null);
        var l = new MarkovModel(2, "");
        var t = new MarkovModel(0, "asdfsd");
        var s = new MarkovModel(-134, "asdfas");
    }

    /**
     * Tests the NgramAnalyser on its distinct ngrams.
     */
    @Test(timeout=1000)
    public void testGetDistinctNgrams() 
    {
        // One gram
        var analyser1 = new NgramAnalyser("aabcabaacaac");
        var ngrams1 = analyser1.getDistinctNgrams();
        assertEquals(ngrams1.contains("a"), true);
        assertEquals(ngrams1.contains("b"), true);
        assertEquals(ngrams1.contains("c"), true);
        
        // Bi gram
        var analyser2 = new NgramAnalyser(2,"aabcabaacaac");
        var ngrams2 = analyser2.getDistinctNgrams();
        assertEquals(ngrams2.contains("aa"), true);
        assertEquals(ngrams2.contains("ab"), true);
        assertEquals(ngrams2.contains("ac"), true);
        assertEquals(ngrams2.contains("ba"), true);
        assertEquals(ngrams2.contains("bc"), true);
        assertEquals(ngrams2.contains("ca"), true);
        
        // Tri gram
        var analyser3 = new NgramAnalyser(3, "aabcabaacaac");
        var ngrams3 = analyser3.getDistinctNgrams();
        assertEquals(ngrams3.contains("aab"), true);
        assertEquals(ngrams3.contains("aac"), true);
        assertEquals(ngrams3.contains("aba"), true);
        assertEquals(ngrams3.contains("abc"), true);
        assertEquals(ngrams3.contains("aca"), true);
        assertEquals(ngrams3.contains("baa"), true);
        assertEquals(ngrams3.contains("bca"), true);
        assertEquals(ngrams3.contains("caa"), true);
        assertEquals(ngrams3.contains("cab"), true);
    }

    /**
     * Tests the MarkovModel with a laplace estimate of last character 
     * occuring given a sequence.
     */
    @Test(timeout=1000)
    public void testLaplaceExample() 
    {
        var model = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.5000, model.laplaceEstimate("aac"), 0.0001);
        assertEquals(0.1667, model.laplaceEstimate("aaa"), 0.0001);
        assertEquals(0.3333, model.laplaceEstimate("aab"), 0.0001);
    }

    /**
     * Tests the MarkovModel using a simple estimate of last character 
     * occuring given a sequence.
     */
    @Test(timeout=1000)
    public void testSimpleExample() 
    {
        var markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.3333, markovMo1.simpleEstimate("aab"), 0.0001);
    }

    /**
     * Test the ModelMatcher on the calculating the average log likelihood
     * for the occurance of last character of a sequence.
     */
    @Test
    public void testTask3example() 
    {
        var markov = new MarkovModel(2, "aabcabaacaac");
        var matcher = new ModelMatcher(markov, "aabbcaac");
        assertEquals(-0.3849, matcher.getAverageLogLikelihood(), 0.0001);
        assertEquals(-0.3010, matcher.getLogLikelihood("bca"), 0.0001);
    }

    /**
     * Test the MatcherController get best match.
     */
    @Test
    public void matchControllerTest()
    {
        String[] dataSet = 
        {
        "aaaaaaaaaaaaaaaaaacaaaaaaaaaacccaaaaaaaacaaaaaaaaaaaaaaaaaaaaaa",
        "bbbbbbbbbbbbbbbbbbcbbbbcccbbbbbbbbbbbbbbbbbbbbbbbbbbcbbbbbbbbbb",
        "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
        "dddddddddddddddddddccddddddddddddddddcccdddddddddddcdddddddddd"
        };
        String sequence = "ccc";
        
        var dataArray = new ArrayList<String>(Arrays.asList(dataSet));
        var controller = new MatcherController(2, dataArray, sequence);
        var bestMatch = controller.matcherList.get(2);
        var controllerMatch = controller.getBestMatch();
        assertEquals(bestMatch, controllerMatch);
    }

   
}


