import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/** Create and manipulate Markov models and model matchers for lists of training data 
 * a test data String and generate output from it for convenient display.
 * 
 * @author Mohamed Yusuf (22273364), Joshua Ng (20163079)
 * @version (22/5/17)
 *
 */
public class MatcherController {
    /** list of training data string used to generate markov models */
    ArrayList<String> trainingDataList;
    /** test data to be matched with the models */
    String testData;
    /** order of the markov models*/
    int k;
    /** generated list of markov models for the given training data*/
    ArrayList<MarkovModel> modelList;
    /** generated list of matchers for the given markov models and test data*/
    ArrayList<ModelMatcher> matcherList;

    /** Generate models for analysis
     * @param k order of the markov models to be used
     * @param testData String to check against different models
     * @throw unchecked exceptions if the input order or data inputs are invalid
     */
    public MatcherController(int k, ArrayList<String> trainingDataList, String testData) 
    {
        if(testData == null){throw new IllegalArgumentException(
            "Error : input string cannot be null");}
        if(testData.isEmpty()){ throw new IllegalArgumentException(
            "Error : input string cannot be empty");}
        if(trainingDataList == null){throw new IllegalArgumentException(
            "Error : ArrayList cannot be null");}
        if(trainingDataList.size() == 0){ throw new IllegalArgumentException(
            "Error : arraylist cannot be empty");}
        if(k <= 0){ throw new IllegalArgumentException(
            "Error : error your integer cannot be zero or less than zero");}
            
        this.trainingDataList = trainingDataList;
        this.k = k;
        this.testData = testData;
        modelList = new ArrayList<>();
        matcherList = new ArrayList<>();

        for(String i : trainingDataList){
            MarkovModel m = new MarkovModel(k,i);
            modelList.add(m);
        }
        for(MarkovModel i : modelList){
            ModelMatcher m = new ModelMatcher(i,testData);
            matcherList.add(m);
        }
    }

    /** @return a string containing all lines from a file 
     * if file contents can be got, otherwise null
     * This method should process any exceptions that arise.
     */
    private static String getFileContents(String filename) {
        try{  
            ArrayList<String> reads = FileIO.readFile(filename);
            String contents = "";
            for(String i : reads){
                contents += i; 
            }
            return contents;

        }catch(java.io.FileNotFoundException e){
            return null;
        }
        catch(java.io.IOException e){
            return null;
        }
    }

    /**
     * @return the ModelMatcher object that has the highest average loglikelihood
     * (where all candidates are trained for the same test string)
     * @throw unchecked exceptions if the input order or data inputs are invalid
     */
    public ModelMatcher getBestMatch(ArrayList<ModelMatcher> candidates) 
    {
        if(candidates == null){ displayError(
            "Error : input ArrayList cannot be null");}
        if(candidates.size() == 0){ displayError(
         "Error : arraylist cannot be empty");}
        
        ModelMatcher max = candidates.get(0);
        double maxi = candidates.get(0).getAverageLogLikelihood();
        for(ModelMatcher dummy : candidates ){
            if(dummy.getAverageLogLikelihood()  > maxi ){
                maxi = dummy.getAverageLogLikelihood();
                max = dummy;
            }

        }
        return max;
    }

    /** @return String an *explanation* of
     * why the test string is the match from the candidate models
     */
    public String explainBestMatch(ModelMatcher best) {
        String s = "This is the best match because it \n" +
        "has the greatest log likelihood compared to the \n" +
        "others" + " its loglikelihood is : " + best.getAverageLogLikelihood();

        return s;
    }

    /** Display an error to the user in a manner appropriate
     * for the interface being used.
     * 
     * @param message
     */
    public void displayError(String message) {
        // LEAVE THIS METHOD EMPTY
    }

}
