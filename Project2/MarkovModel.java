import java.util.Set;
/**
 * Construct a Markov model of order /k/ based on an input string.
 * 
 * @author Joshua Ng (20163079) Mohamed Yusuf(22273364)
 * @version 20/5/2017
 */
public class MarkovModel
{

    /** Markov model order parameter */
    int k; 
    /** ngram model of order k */
    NgramAnalyser ngram; 
    /** ngram model of order k+1 */
    NgramAnalyser n1gram; 

    /**
     * Construct an order-k Markov model from string s
     * @param k int order of the Markov model
     * @param s String input to be modelled
     * @throws IllegalArgumentException if the input fields are unsuitable.
     */
    public MarkovModel(int k, String s) 
    {
        if(s == null){throw new IllegalArgumentException("Error : input string cannot be null");}
        if(s.isEmpty()){ throw new IllegalArgumentException("Error : input string cannot be empty");}        
        if(k <= 0){ throw new IllegalArgumentException("Error : ngram size cannot be zero or less than zero");}
        if(k+1 > s.length()){throw new IllegalArgumentException("Error: your ngram cannot be larger than your input string");}  
        
        this.k = k;
        ngram = new NgramAnalyser(k, s);
        n1gram = new NgramAnalyser(k+1, s);
    }

    /**
     * @return order of this Markov model
     */
    public int getK()
    {
        return k;
    }

    /** Estimate the probability of a sequence appearing in the text 
     * using simple estimate of freq seq / frequency front(seq).
     * @param sequence String of length k+1
     * @return double probability of the last letter occuring in the 
     * context of the first ones or 0 if front(seq) does not occur.
     */
    public double simpleEstimate(String sequence) {
        if(sequence == null){return (double) 0;}
        if(sequence.isEmpty()){return (double) 0;}
        
        int aa = ngram.getNgramFrequency(sequence.substring(0,sequence.length()-1));
        if(aa == 0) {return (double) 0;}
        int aab = n1gram.getNgramFrequency(sequence);
        return (double) aab/aa;
    }
    /**
     * Calculate the Laplacian probability of string obs given this Markov model
     * @para sequence String of length k+1
     * @return double laplace probability of sequence string
     */
    public double laplaceEstimate(String sequence) 
    { 
        if(sequence == null){return (double) 0;}
        if(sequence.isEmpty()){return (double) 0;}
        
        int aa = ngram.getNgramFrequency(sequence.substring(0,sequence.length()-1));
        if((aa+ngram.getAlphabetSize()) == 0) {return (double) 0;}
        int aab = n1gram.getNgramFrequency(sequence);
        return (double) (aab+1)/(aa+ngram.getAlphabetSize());
    }

    /**
     * @return String representing this Markov model
     */
    public String toString()
    {
        String s = "" + k;
        s += "\n" + ngram.getAlphabetSize();
        s += "\n" + ngram.toString() + "\n" + n1gram.toString();
        
        return s;
    }

}
