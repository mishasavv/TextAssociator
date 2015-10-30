import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* CSE 373 Starter Code
 * @Author Kevin Quinn
 * 
 * TextAssociator represents a collection of associations between words.
 * See write-up for implementation details and hints
 * 
 */
public class TextAssociator {
	// PRIVATE
	public WordInfoSeparateChain[] table;
	public int size;
	public int[] primes;
	public int primeLevel;
	
	/* INNER CLASS
	 * Represents a separate chain in your implementation of your hashing
	 * A WordInfoSeparateChain is a list of WordInfo objects that have all
	 * been hashed to the same index of the TextAssociator
	 */
	private class WordInfoSeparateChain {
		private List<WordInfo> chain;
		
		/* Creates an empty WordInfoSeparateChain without any WordInfo
		 */
		public WordInfoSeparateChain() {
			this.chain = new ArrayList<WordInfo>();
		}
		
		/* Adds a WordInfo object to the SeparateCahin
		 * Returns true if the WordInfo was successfully added, false otherwise
		 */
		public boolean add(WordInfo wi) {
			if (!chain.contains(wi)) {
				chain.add(wi);
				return true;
			}
			return false;
		}
		
		/* Removes the given WordInfo object from the separate chain
		 * Returns true if the WordInfo was successfully removed, false otherwise
		 */
		public boolean remove(WordInfo wi) {
			if (chain.contains(wi)) {
				chain.remove(wi);
				return true;
			}
			return false;
		}
		
		// Returns the size of this separate chain
		public int size() {
			return chain.size();
		}
		
		// Returns the String representation of this separate chain
		public String toString() {
			return chain.toString();
		}
		
		// Returns the list of WordInfo objects in this chain
		public List<WordInfo> getElements() {
			return chain;
		}
	}
	
	
	/* Creates a new TextAssociator without any associations 
	 */
	public TextAssociator() {
		primes = new int[] {23, 53, 101, 211, 401, 809, 1601, 3203, 6007, 12007, 24001, 48017, 100003, 200003, 400009, 800011};
		primeLevel = 0;
		table = new WordInfoSeparateChain[primes[0]];
		size = 0;
	}
	
	
	/* Adds a word with no associations to the TextAssociator 
	 * Returns False if this word is already contained in your TextAssociator ,
	 * Returns True if this word is successfully added
	 */
	public boolean addNewWord(String word) {
		// First, check to see if table needs to be resized
		if( (double)(size / table.length) >= .75) {
			resize();
		}
		int arrayPosition = locateArrayPosition(word);
		WordInfo newWordInfo = new WordInfo(word);
		if (table[arrayPosition] == null) {
			WordInfoSeparateChain newWordInfoSeparateChain = new WordInfoSeparateChain();
			table[arrayPosition] = newWordInfoSeparateChain;
			size++;
			return table[arrayPosition].add(newWordInfo);
		} else if(!isContainedInChain(word,arrayPosition)) {
			size++;
			return table[arrayPosition].add(newWordInfo);
		} else {
			return false;
		}
	}
	
	
	/* Adds an association between the given words. Returns true if association correctly added, 
	 * returns false if first parameter does not already exist in the SpellChecker or if 
	 * the association between the two words already exists
	 */
	public boolean addAssociation(String word, String association) {
		int arrayPosition = locateArrayPosition(word);
		if (table[arrayPosition] != null && isContainedInChain(word, arrayPosition)) {
			WordInfo associationWordInfo = getWordInfo(word, arrayPosition);
			return associationWordInfo.addAssociation(association);
		}
		return false;
	}
	
	
	/* Remove the given word from the TextAssociator, returns false if word 
	 * was not contained, returns true if the word was successfully removed.
	 * Note that only a source word can be removed by this method, not an association.
	 */
	public boolean remove(String word) {
		int arrayPosition = locateArrayPosition(word);
		if (table[arrayPosition] != null && isContainedInChain(word, arrayPosition)) {
			WordInfo associationWordInfo = getWordInfo(word, arrayPosition);
			size--;
			return table[arrayPosition].remove(associationWordInfo);
		}
		return false;
	}
	
	
	/* Returns a set of all the words associated with the given String  
	 * Returns null if the given String does not exist in the TextAssociator
	 */
	public Set<String> getAssociations(String word) {
		int arrayPosition = locateArrayPosition(word);
		if (table[arrayPosition] != null && isContainedInChain(word, arrayPosition)) {
			WordInfo associationWordInfo = getWordInfo(word, arrayPosition);
			return associationWordInfo.getAssociations();
		}
		return null;
	}
	
	
	/* Prints the current associations between words being stored
	 * to System.out
	 */
	public void prettyPrint() {
		System.out.println("Current number of elements : " + size);
		System.out.println("Current table size: " + table.length);
		
		//Walk through every possible index in the table
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				WordInfoSeparateChain bucket = table[i];
				
				//For each separate chain, grab each individual WordInfo
				for (WordInfo curr : bucket.getElements()) {
					System.out.println("\tin table index, " + i + ": " + curr);
				}
			}
		}
		System.out.println();
	}
	
	private void resize() {
		primeLevel++;
		WordInfoSeparateChain[] resizedArray = new WordInfoSeparateChain[primes[primeLevel]];
	}
	
//	private int hashString(String s) {
//		return s.hashCode();
//	}
	
	// MAKE SURE TO MAKE PRIVATE
	public int locateArrayPosition(String s) {
		// Use Math.abs so we only get positive hashes (and will therefor work as array values)
		int hash = Math.abs(s.hashCode());
		return (hash % table.length);
	}
	
	private boolean isContainedInChain(String s, int index) {
		List<WordInfo> wordInfoObjects =  table[index].getElements();
		for(int i = 0; i < wordInfoObjects.size(); i++) {
			if(wordInfoObjects.get(i).getWord() == s) {
				return true;
			}
		}
		return false;
	}
	
	private WordInfo getWordInfo(String s, int index) {
		List<WordInfo> wordInfoObjects = table[index].getElements();
		for(int i = 0; i < wordInfoObjects.size(); i++) {
			if(wordInfoObjects.get(i).getWord() == s) {
				return wordInfoObjects.get(i);
			}
		}
		return null;
	}
	
}
