import java.util.Set;
import java.util.ArrayList;

/**
 * Determines how necessary the last character of the substring (gram)
 * is when searching for the gram in some text.
 * 
 * Uses a Markov model of order k to analyse an input string.
 * 
 * @author Joshua Ng (20163079), Mohamed Yusuf (22273364)
 * @version 13/12/2022
 */
public class MarkovModel
{
    /** Markov model order parameter */
    int k;
    
    /** Input String */
    String s;
    
    /** ngram model of order k */
    NgramAnalyser ngram;
    
    /** ngram model of order k+1 */
    NgramAnalyser n1gram;
    
    /** A summary of the ngrams */
    private String summary;

    /**
     * Construct an order-k Markov model from string s.
     * 
     * @param   k       Order of the Markov model.
     * @param   s       Input text to be modelled.
     */
    public MarkovModel(int k, String s) 
    {
        validateInput(k, s);
        this.k = k;        
        this.s = s;
        ngram = new NgramAnalyser(k, s);
        n1gram = new NgramAnalyser(k + 1, s);
        summary = generateSummary();
    }
    
    /**
     * Checks and validates given input string.
     *
     * @param n         Size of n-grams to create.
     * @param input     The given input.
     */
    private void validateInput(int n, String input)
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
        else if (n <= 0)
        { 
            error = "Error : ngram size cannot be zero or less than zero";
        }
        else if ((n + 1) > input.length())
        {
            error = """
            Error: your ngram cannot be larger than your input string
            """;
        }
        
        if (error != null)
        {
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Return the order of this Markov model.
     * 
     * @return  The order of this Markov model.
     */
    public int getK()
    {
        return k;
    }

    /** 
     * A simple estimate of how likely the last character occurs
     * given a particular sequence.
     * 
     * @param search    The given substring (search) to search for.
     * @return          The probability of the last character.
     */
    public double simpleEstimate(String search) 
    {   
        if ((search == null) || search.isEmpty())
        {
            return 0;
        }
        
        double probability = 0;
        var subSearch = search.substring(0, search.length() - 1);
        var subSearchCount = ngram.getNgramFrequency(subSearch);
        
        if (subSearchCount != 0)
        {
            double searchCount = n1gram.getNgramFrequency(search);
            probability = searchCount / subSearchCount;
        }
        
        return probability;
    }
    
    /**
     * Calculate the Laplacian probability of the last character 
     * occuring given a sequence.
     * 
     * @param search    The given substring (search) to search for.
     * @return          The Laplace probability of the last character.
     */
    public double laplaceEstimate(String search) 
    { 
        if ((search == null) || search.isEmpty())
        {
            return 0;
        }
        
        var subSearch = search.substring(0, search.length() - 1);
        var subSearchCount = ngram.getNgramFrequency(subSearch);
        var distinctCharCount = ngram.getAlphabetSize();
        double searchCount = n1gram.getNgramFrequency(search);
        
        // The Laplace smoothed estimate of the probability.
        // Using alphabet count heuristic so sub count cannot be zero. 
        var prob = (searchCount + 1)/(subSearchCount + distinctCharCount);
        
        return prob;
    }

    /**
     * Generate a summary of the ngrams for this object.
     * Lists the ngram size, string and frequency.
     * Ngrams are presented in alphabetical order.
     * 
     * @return A string summary of the ngrams.
     */
    private String generateSummary()
    {
        var summary = new ArrayList<String>();
        summary.add(Integer.toString(k));
        summary.add(Integer.toString(ngram.getAlphabetSize()));
        summary.add(ngram.toString());
        summary.add(ngram.toString());
        
        return String.join("\n", summary);
    }
    
    /**
     * Returns a summary of the configuration for this Markov model.
     * 
     * @return A string summary for this Markov model.
     */
    public String toString()
    {
        return summary;
    }

}
