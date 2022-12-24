import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/** 
 * Trains and and finds best Markov model for a given text. 
 * 
 * @author Mohamed Yusuf (22273364), Joshua Ng (20163079)
 * @version 13/12/2022
 */
public class MatcherController 
{
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

    /** 
     * Contructor.
     * 
     * @param   k         The order of the markov models to be used.
     * @param   dataset   The training data set.
     * @param   input     The given text input.
     */
    public MatcherController
    (
        int                 k, 
        ArrayList<String>   dataset, 
        String              input
    )
    {
        validateInput(k, dataset, input);

        trainingDataList = dataset;
        this.k = k;
        testData = input;
        modelList = new ArrayList<>();
        matcherList = new ArrayList<>();

        for(var data : trainingDataList)
        {
            modelList.add(new MarkovModel(k, data));
        }
        
        for(var model : modelList)
        {
            matcherList.add(new ModelMatcher(model, input));
        }
    }
    
    /**
     * Checks and validates given model and input string.
     * 
     * @param   k         The order of the markov models to be used.
     * @param   dataset   The training data set.
     * @param   input     The given text input.
     * @throws  IllegalArgumentException if the input fields are unsuitable.
     */
    private void validateInput(int k, ArrayList<String> dataset, String input)
    {
        String error = null;
        
        if (input == null)
        {
            error = "Error : input string cannot be null";
        }
        else if (input.isEmpty())
        { 
            error = "Error : input string cannot be empty";
        }
        else if (dataset == null)
        { 
            error = "Error : dataset cannot be null";
        }
        else if (dataset.isEmpty())
        {
            error = "Error : arraylist cannot be empty";
        }
        else if (k <= 0)
        {
            error = "Error : error your integer cannot be zero or less than zero";
        }
        
        if (error != null)
        {
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Gets the best model with the highest average log likelihood.
     * 
     * @return  The model with the hightest average log likelidhood.
     */
    public ModelMatcher getBestMatch() 
    {
        return getBestMatch(matcherList);
    }
    
    /**
     * Gets the best model with the highest average log likelihood.
     * 
     * @param   candidates  The candidate models to compare.
     * @return  The model with the hightest average log likelidhood.
     */
    public ModelMatcher getBestMatch(ArrayList<ModelMatcher> candidates) 
    {
        if (!validateCandidates(candidates))
        {
            return null;
        }
        
        var iterator = candidates.iterator();
        var bestModel = iterator.next();
        var bestScore = bestModel.getAverageLogLikelihood();
        
        while (iterator.hasNext())
        {
            var candidate = iterator.next();
            var score = candidate.getAverageLogLikelihood();
            
            if (score > bestScore)
            {
                bestModel = candidate;
                bestScore = score;
            }
        }
        
        return bestModel;
    }
    
    /**
     * Checks and validates given model and input string.
     * 
     * @param   candidates  The candidate models to compare.
     * @return  Returns true if candidates are valid.
     */
    private boolean validateCandidates(ArrayList<ModelMatcher> candidates)
    {
        String error = null;
        
        if(candidates == null)
        { 
            error = "Error : input ArrayList cannot be null";
        } else if(candidates.isEmpty())
        {
            error = "Error : arraylist cannot be empty";
        }
        
        if (error != null)
        {
            displayError(error);
        }
        
        return error == null;
    }

    /** 
     * Gives a explaination on why the given model is the 
     * best match for the input text string.
     */
    public String explainBestMatch(ModelMatcher best) 
    {
        var text = """
        This model has the highest log likelihood making it
        the best match for the input text.
        The log likelihood has logarithmic probability range between 
        (-inf, 0] with zero being the highest.
        A high score means a better on average predictive capability 
        of the last character of a given sequence. 
        This models average loglikelihood is: %f
        """;

        return String.format(text, best.getAverageLogLikelihood());
    }

    /** 
     * Displays an error to the user.
     * 
     * @param message   The message string to send the user.
     */
    public void displayError(String message) 
    {
        // LEAVE THIS METHOD EMPTY
    }

}
