import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

/**
 * Report the average log likelihood of a test String occuring in a 
 * given Markov model and detail the calculated values behind this statistic.
 * 
 * @author Joshua Ng (20163079), Mohamed Yusuf (22273364) 
 * @version (20/5/17)
 */
public class ModelMatcher
{

    /** log likelihoods for a teststring under a given model */
    private HashMap<String,Double> logLikelihoodMap;
    /** summary statistic for this setting */
    private double averageLogLikelihood;  

    /**
     * Constructor to initialise the fields for the log likelihood map for 
     * a test string and a given Markov model and 
     * the average log likelihood summary statistic
     * @param MarkovModel model a given Markov model object
     * @param String teststring
     */
    public ModelMatcher(MarkovModel model, String testString)
    {
        logLikelihoodMap = new HashMap<>();
        NgramAnalyser testA = new NgramAnalyser(model.getK()+1,testString);
        for(String i : testA.getDistinctNgrams()){
            double estimate = Math.log10(model.laplaceEstimate(i));
            estimate *= testA.getNgramFrequency(i);
            logLikelihoodMap.put(i,estimate);
        }

        averageLogLikelihood = averageLogLikelihood(logLikelihoodMap,
            testA.getNgramCount());
    }

    /** Helper method that calculates the average log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @param ngramCount int number of ngrams in the original test string
     * @return average log likelihood: the total of loglikelihoods 
     *    divided by the ngramCount
     */
    private double averageLogLikelihood(HashMap<String,Double> logs, int ngramCount)
    {
        return ((totalLogLikelihood(logs))/ngramCount);
    }

    /** Helper method to calculate the total log likelihood statistic
     * given a HashMap of strings and their Laplace probabilities
     * and the total number of ngrams in the model.
     * 
     * @param logs map of ngram strings and their log likelihood
     * @return total log likelihood: the sum of loglikelihoods in logs 
     */
    private double totalLogLikelihood(HashMap<String,Double> logs)
    {
        double sum = 0;
        for(double i : logs.values()){
            sum += i;
        }
        return sum;
    }

    /**
     * @return the average log likelihood statistic
     */
    public double getAverageLogLikelihood() 
    {
        return averageLogLikelihood;
    }

    /**
     * Returns the log likelihood value for a given ngram string.
     * @param ngram A string for the ngram
     * @return double probability value for the given ngram.
     */
    public double getLogLikelihood(String ngram) 
    {
        return (logLikelihoodMap.get(ngram));
    }

    /**
     * Make a String summarising the log likelihood map and its statistics
     * @return String of ngrams and their loglikeihood differences between the models
     * The likelihood table should be ordered from highest to lowest likelihood
     */
    public String toString() 
    {
        String s = "averageLogLikelihood\n";
        s += "Context+Character  log10 of laplace estimate\n";
        for(String i : logLikelihoodMap.keySet()) {
            s += "      " + i + "              " + getLogLikelihood(i) + "\n";
        }
        s += "Average log likelihood " + getAverageLogLikelihood();
        System.out.println(s);
        return s;
    }

}
