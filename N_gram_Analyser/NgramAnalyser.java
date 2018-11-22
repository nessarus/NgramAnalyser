import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import java.util.HashSet;
import java.util.Arrays;

/**
 * Perform n-gram analysis of a string.
 * 
 * Analyses the frequency with which distinct n-grams, of length n,
 * appear in an input string. For the purposes of all analyses of the input
 * string, the final n-1 n-grams appearing in the string should be
 * "filled out" to a length of n characters, by adding
 * a sequence of contiguous characters from the start of the string.
 * e.g. "abbc" includes "bca" and "cab" in its 3-grams
 * 
 * @author Joshua Ng (20163079), Mohamed Yusuf (22273364)
 * @version 16/5/2017
 */
public class NgramAnalyser
{
    /** dictionary of all distinct n-grams and their frequencies */
    private HashMap<String,Integer> ngram;

    /** number of distinct characters in the input */
    private int alphabetSize;

    /** n-gram size for this object (new field) */
    private int ngramSize;
    
    /** number of ngram counts (not requiring them to be distinct) */
    private int ngramCount;

    /** 
     * Analyse the frequency with which distinct n-grams, of length n,
     * appear in an input string. 
     * n-grams at the end of the string wrap to the front
     * e.g. "abbbbc" includes "bca" and "cab" in its 3-grams
     * @param int n size of n-grams to create
     * @param String inp input string to be modelled
     * @throws IllegalArgumentException if the input fields are unsuitable.
     */
    public NgramAnalyser(int n, String inp) 
    { 
        if(inp == null){throw new IllegalArgumentException(
            "Error : input string cannot be null");}
        if(inp.isEmpty()){ throw new IllegalArgumentException(
            "Error : input string cannot be empty");}        
        if(n <= 0){ throw new IllegalArgumentException(
            "Error : ngram size cannot be zero or less than zero");}
        if(n > inp.length()){throw new IllegalArgumentException(
            "Error: your ngram cannot be larger than your input string");}
       
        ngram = new HashMap<>();
        ngramSize = n;
        
        char[] inp_array = inp.toCharArray();
        ngramCount = inp_array.length;
        
        for(int i=0; i < ngramCount; i++) {
            String gram = createGram(inp_array, i, n);
            int freq = getNgramFrequency(gram) + 1;
            ngram.put(gram, freq);
        }
        
        HashSet<Character> alphabet = new HashSet<>();
        for(char c : inp_array) {
            alphabet.add(c);
        }
        alphabetSize = alphabet.size();

    }
    
    /** 
     * Analyses the input text for n-grams of size 1.
     */
    public NgramAnalyser(String inp) 
    {
        this(1,inp);
    }
    
    /**
     * Creates n-gram string from a char array at p index. 
     * n-grams at the end of the string wrap to the front.
     * e.g. createGram({'a', 'b', 'c'}, 0, 2) returns 'ab'
     * @param char[] inp input string array to be modelled
     * @param int p index position of n-gram
     * @param int n size of gram.
     */
    private String createGram(char[] inp, int p, int n)
    {
        char[] gram = new char[n];
        for(int i=0; i<n; i++) {
            gram[i] = inp[(p+i)%inp.length];
        }
        return new String(gram);
    }

    /**
     * @return int the size of the alphabet of a given input
     */
    public int getAlphabetSize() {
        return alphabetSize;
    }

    /**
     * @return the total number of distinct n-grams appearing
     *         in the input text.
     */
    public int getDistinctNgramCount() {
        return ngram.size();
    }

    /** 
     * @return Return a set containing all the distinct n-grams
     *         in the input string.
     */
    public Set<String> getDistinctNgrams() {
        return ngram.keySet();
    }

    /**
     * @return the total number of n-grams appearing
     *         in the input text (not requiring them to be distinct)
     */
    public int getNgramCount() {
        return ngramCount;
    }

    /** Return the frequency with which a particular n-gram appears
     * in the text. If it does not appear at all, return 0.
     * 
     * @param ngram The n-gram to get the frequency of
     * @return The frequency with which the n-gram appears.
     */
    public int getNgramFrequency(String ngram) {
        return this.ngram.getOrDefault(ngram, 0);
    }

    /**
     * Sorts an unorder set to and return a ordered String array.
     * 
     * @param un The Set of unordered strings.
     * @return An ordered string array.
     */    
    private String[] sortSet(Set<String> un){
        Set<String> unOrder = un;
        String[] order = unOrder.toArray(new String[unOrder.size()]);
        Arrays.sort(order);
        return order;
    }

    /**
     * Generate a summary of the ngrams for this object.
     * @return a string representation of the n-grams in the input text 
     * comprising the ngram size and then each ngram and its frequency
     * where ngrams are presented in alphabetical order.     
     */
    public String toString()
    {
        String summary = "" + ngramSize;
        String[] order = sortSet(getDistinctNgrams());
        for(String m : order){
            summary += "\n" + m + " " + ngram.get(m);
        }
        return summary;
    }

}
