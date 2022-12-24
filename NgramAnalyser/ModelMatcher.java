import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

/**
 * Report the average log likelihood of a test string occuring in a 
 * given Markov model and detail the calculated values behind this 
 * statistic.
 * 
 * @author Joshua Ng (20163079), Mohamed Yusuf (22273364) 
 * @version 13/12/2022
 */
public class ModelMatcher
{
    /** Log likelihoods for a teststring under a given model */
    private HashMap<String, Double> logLikelihoodMap;

    /** Summary statistic for this setting */
    private double averageLogLikelihood;

    /** Given markov model */
    private MarkovModel model;
    
    /** A summary of the ngrams */
    private String summary;

    /**
     * Constructor.
     * 
     * @param   model   A given Markov model object.
     * @param   input   The file text.
     */
    public ModelMatcher(MarkovModel model, String input)
    {
        validateInput(model, input);
        logLikelihoodMap = new HashMap<>();
        this.model = model;
        
        // Calculate average log likelihood.
        var k1Analyser = new NgramAnalyser(model.getK() + 1, input);
        double totalScore = 0;
        
        for(var entry : k1Analyser.getFrequencySet())
        {
            var k1gram = entry.getKey();
            var k1frequency = entry.getValue();
            
            var markovScore = calculateMarkovScore(k1gram, k1frequency);
            logLikelihoodMap.put(k1gram, markovScore);
            totalScore += markovScore;
        }
        
        averageLogLikelihood = totalScore / k1Analyser.getNgramCount();
        
        summary = generateSummary();
    }

    /**
     * Checks and validates given model and input string.
     *
     * @param model     The markov model.
     * @param input     The given text input.
     */
    private void validateInput(MarkovModel model, String input)
    {
        String error = null;

        if(input == null)
        {
            error = "Error : input string cannot be null";
        }
        else if(input.isEmpty())
        { 
            error = "Error : input string cannot be empty";
        }
        else if(model == null)
        { 
            error = "Error : model cannot be null";
        }
        
        if (error != null)
        {
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Calculate markov laplace log score.
     * 
     * @param ngram         A string for the ngram.
     * @param frequency     The frequency of the ngram. 
     */
    private double calculateMarkovScore(String ngram, int frequency)
    {
        // Markov calculation   ~ (n1frequency + 1)/(nfrequency + distinct);
        var markovEstimate      = model.laplaceEstimate(ngram);

        // Logarithmic probability changes range from [0, 1] to (-inf, 0].
        var markovLog           = Math.log10(markovEstimate);

        // High markov score means better predictions (zero being the highest). 
        // High frequency sequences (grams) carry more weight.
        var markovScore         = markovLog * frequency;

        return markovScore;
    }

    /**
     * Gets average log markov score of the give markov model.
     * 
     * @return the average log likelihood statistic.
     */
    public double getAverageLogLikelihood() 
    {
        return averageLogLikelihood;
    }

    /**
     * Returns the log likelihood value for a given ngram string.
     * 
     * @param ngram     A string for the ngram
     * @return          Probability value for the given ngram.
     */
    public double getLogLikelihood(String ngram) 
    {
        return logLikelihoodMap.getOrDefault(ngram, 0.0);
    }

    /**
     * Generate a summary of the ngrams for this object.
     * Lists the ngram and log likelihoods.
     * Ordered from highest to lowest likelihood
     * 
     * @return A string summary of the ngrams.
     */
    private String generateSummary()
    {
        var summary = new ArrayList<String>();
        summary.add("Summary of the log likelihoods of this model.");
        summary.add("ngram\t\tlog likelihood");
        
        for(var entry : logLikelihoodMap.entrySet()) 
        {
            var ngram = entry.getKey();
            var frequency = entry.getValue();
            var line = String.format("%s\t\t%f", ngram, frequency);
        }
        
        var line = "Average log likelihood: %f";
        summary.add(String.format(line, averageLogLikelihood));
        
        return String.join("\n", summary);
    }
    
    /**
     * Returns a summary of the ngram log likelihood statistics. 
     * 
     * @return A string summary of log likelihood statistics.
     */
    public String toString() 
    {
        return summary;
    }

}
