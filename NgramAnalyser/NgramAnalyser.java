import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Analyses the frequency with which distinct n-grams, of length n,
 * appear in an input string. 
 * 
 * A gram is a substring of string but can wrap around to the 
 * beginning of word. An n-gram is an n lengthed gram. 
 * 
 * e.g. For "abbc", its 3-gram are {"abc", "bbc", "bca", "cab"}.
 * 
 * @author Joshua Ng (20163079), Mohamed Yusuf (22273364)
 * @version 13/12/22
 */
public class NgramAnalyser
{
    /** dictionary of all distinct n-grams and their frequencies */
    private HashMap<String,Integer> ngram;
    
    /** The number of distinct characters in the input */
    private int alphabetSize;

    /** n-gram size for this object (new field) */
    private int ngramSize;

    /** number of ngram counts (not requiring them to be distinct) */
    private int ngramCount;
    
    /** A summary of the ngrams */
    private String summary;

    /** 
     * Constructor.
     * 
     * @param n         The n size of the n-grams.
     * @param input     The input string to be modelled
     * @throws IllegalArgumentException if the input fields are unsuitable.
     */
    public NgramAnalyser(int n, String input)
    { 
        validateInput(n, input);
        ngram = new HashMap<>();
        ngramSize = n;
        ngramCount = input.length();

        generateNgrams(input);
        
        var alphabet = new HashSet<>();
        for(var c : input.toCharArray()) 
        {
            alphabet.add(c);
        }
        alphabetSize = alphabet.size();

        summary = generateSummary();
    }

    /** 
     * Constructor for n-grams of size 1.
     */
    public NgramAnalyser(String input) 
    {
        this(1, input);
    }

    /**
     * Checks and validates given input string.
     *
     * @param n         Size of n-grams to create
     * @param input     The given input.
     * @throws IllegalArgumentException if the input fields are unsuitable.
     */
    private void validateInput(int n, String input)
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
        else if(n <= 0)
        { 
            error = "Error : ngram size cannot be zero or less than zero";
        }
        else if(n > input.length())
        {
            error = "Error: your ngram cannot be larger than your input string";
        }
        
        if (error != null)
        {
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Generates n-gram strings from the input string. 
     * n-grams at the end of the string wrap to the front.
     * 
     * @param input     input string array
     */
    private void generateNgrams(String input)
    {
        var inputArray = input.toCharArray();
        var gramArray = new char[ngramSize];
        
        for (var i = 0; i < input.length(); i++)
        {
            for (var j = 0; j < ngramSize; j++)
            {
                gramArray[j] = inputArray[(i + j) % input.length()];
            }
            
            // Add ngram and increment its frequency.
            var gram = new String(gramArray);
            var freq = ngram.getOrDefault(gram, 0);
            ngram.put(gram, freq + 1);
        }
    }

    /**
     * Returns the number of the distinct characters in the input text.
     * 
     * @return int the size of the alphabet of a given input text.
     */
    public int getAlphabetSize() 
    {
        return alphabetSize;
    }

    /**
     * Returns the total number of distinct n-gram in the input.
     * 
     * @return The number of distinct n-grams in the input.
     */
    public int getDistinctNgramCount() 
    {
        return ngram.size();
    }

    /** 
     * Returns the set distinct n-grams from the given input.
     * 
     * @return A set of distinct n-gram strings.
     */
    public Set<String> getDistinctNgrams() 
    {
        return ngram.keySet();
    }
    
    /** 
     * Returns the set distinct n-grams with their frequencies from
     * the given input.
     * 
     * @return A set of distinct n-gram and their frequencies.
     */
    public Set<HashMap.Entry<String,Integer>> getFrequencySet()
    {
        return ngram.entrySet();
    }
    
    /**
     * Returns the total number of n-grams appearing in the input.
     * (not requiring them to be distinct)
     * 
     * @return  The number of n-grams.
     */
    public int getNgramCount() 
    {
        return ngramCount;
    }

    /**
     * Return the frequency with which a particular n-gram appears
     * in the text. If it does not appear at all, return 0.
     * 
     * @param gram  The gram to get the frequency of.
     * @return The frequency with which the gram appears.
     */
    public int getNgramFrequency(String gram) 
    {
        return ngram.getOrDefault(gram, 0);
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
        // Sort n grams alphabetically. (Don't use TreeMap)
        var sortedNgrams = new String[ngram.size()];
        ngram.keySet().toArray(sortedNgrams);
        Arrays.sort(sortedNgrams);
        
        // Create summary.
        var summary = new ArrayList<String>();
        summary.add(Integer.toString(ngramSize));
        
        for(var gram : sortedNgrams)
        {
            var freq = ngram.get(gram);
            summary.add(String.format("%s %d", gram, freq));
        }
        
        return String.join("\n", summary);
    }

    /**
     * Returns a summary of the ngrams for this object.
     * 
     * @return A string summary of the ngrams.
     */
    public String toString()
    {
        return summary;
    }
}
