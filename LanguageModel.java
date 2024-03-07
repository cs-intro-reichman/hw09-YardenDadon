import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {// Your code goes here
        String window = "";
        char chr = ' ' ;
        In in = new In(fileName);
        for (int i = 0; i < windowLength; i++){
            chr = in.readChar();
            window += chr;
        }
        while (!in.isEmpty()){
            chr = in.readChar();
            if (CharDataMap.containsKey(window)){
                CharDataMap.get(window).update(chr);
            }
            else{
                List probs = new List();
                probs.addFirst(chr);
                CharDataMap.put(window, probs);
            }
            window = window.substring(1) + chr;
        }
        for (List probs : CharDataMap.values())
        calculateProbabilities(probs);
	}

	public void calculateProbabilities(List probs) {// Your code goes here
        int counter = 0;
        Node current = probs.getFirstNode();
        while (current != null)
        {
            counter += current.cp.count;
            current = current.next;
        }

        double ccp = 0;
        current = probs.getFirstNode();
        while (current != null) {
            current.cp.p = (double) current.cp.count / counter;
            ccp += (double) current.cp.count / counter;;
            current.cp.cp = ccp;
            current = current.next;
        }}

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {// Your code goes here
        Node current = probs.getFirstNode();
        double r = randomGenerator.nextDouble();
        while (r > current.cp.cp)
        {
            current = current.next;
        }
        return current.cp.chr;
	}

    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {// Your code goes here
        String window = "";
        String text = initialText;
        char chr;
        if (windowLength > initialText.length() || initialText.length() >= textLength){
            return initialText;
        }
        else{
            window = initialText.substring(initialText.length() - windowLength);
            while (text.length() - windowLength < textLength) {
                if (CharDataMap.containsKey(window)){
                    chr = getRandomChar(CharDataMap.get(window));
                    text += chr;
                    window = window.substring(1) + chr;
                }
                else {return text;}
            }
            return text;
        }
    }

    /** Returns a string representing the map of this language model. */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}

    public static void main(String[] args) {// Your code goes here
        int windowLength = Integer.parseInt(args[0]);
        String initialText = args[1];
        int generatedTextLength = Integer.parseInt(args[2]);
        Boolean randomGeneration = args[3].equals("random");
        String fileName = args[4];
        LanguageModel lm;
        if (randomGeneration) lm = new LanguageModel(windowLength);
       else lm = new LanguageModel(windowLength, 20);
       lm.train(fileName);
       System.out.println(lm.generate(initialText, generatedTextLength));
    }
}
