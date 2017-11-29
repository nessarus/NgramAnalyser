import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

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

    @Test(timeout=1000 , expected = IllegalArgumentException.class)
    public void testSensibleToStringSize() {
        MarkovModel m = new MarkovModel(2,"c");
        MarkovModel k = new MarkovModel(2,null);
        MarkovModel l = new MarkovModel(2,"");
    }

    @Test(timeout=1000)
    public void testGetDistinctNgrams() {
        NgramAnalyser ngramAna1 = new NgramAnalyser(2, "123");
        Set<String> s1 = ngramAna1.getDistinctNgrams();
        
        //Set<String> someSet = new HashSet<String>();
        ArrayList<String> a1 = new ArrayList<String>(s1);
        Collections.sort(a1);
        
        ArrayList<String> a2 = new ArrayList<String>();
        a2.add("12");
        a2.add("23");
        a2.add("31");
        assertEquals(a2, a1);
    }

    @Test(timeout=1000)
    public void testLaplaceExample() {
        MarkovModel markovMo2 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.5000, markovMo2.laplaceEstimate("aac"), 0.0001);
        assertEquals(0.1667, markovMo2.laplaceEstimate("aaa"), 0.0001);
        assertEquals(0.3333, markovMo2.laplaceEstimate("aab"), 0.0001);
    }

    @Test(timeout=1000)
    public void testSimpleExample() {
      MarkovModel markovMo1 = new MarkovModel(2, "aabcabaacaac");
        assertEquals(0.33333, markovMo1.simpleEstimate("aab"), 0.0001);
    }

    @Test
    public void testTask3example() 
    {
        MarkovModel model = new MarkovModel(2, "aabcabaacaac");
        ModelMatcher modelMat1 = new ModelMatcher(model, "aabbcaac");
        assertEquals(-0.3848976, modelMat1.getAverageLogLikelihood(), 0.0001);
        assertEquals(-0.30102999, modelMat1.getLogLikelihood("bca"), 0.0001);
    }

    @Test
    public void matchControllerTest()
    {
        java.util.ArrayList<java.lang.String> arrayLis1 = new java.util.ArrayList<>();
        arrayLis1.add("aaaaaaaaaaaaaaaaaacaaaaaaaaaacccaaaaaaaacaaaaaaaaaaaaaaaaaaaaaa");
        arrayLis1.add("bbbbbbbbbbbbbbbbbbcbbbbcccbbbbbbbbbbbbbbbbbbbbbbbbbbcbbbbbbbbbb");
        arrayLis1.add("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
        arrayLis1.add("dddddddddddddddddddccddddddddddddddddcccdddddddddddcdddddddddd");
        MatcherController matcherC1 = new MatcherController(2, arrayLis1, "ccc");
        java.util.ArrayList<ModelMatcher> arrayLis2 = matcherC1.matcherList;
        ModelMatcher modelMat1 = matcherC1.getBestMatch(arrayLis2);
        assertEquals(arrayLis2.get(2), matcherC1.getBestMatch(arrayLis2));
    }

   
}


