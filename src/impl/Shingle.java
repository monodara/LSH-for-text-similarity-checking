package impl;

import java.util.HashSet;
import java.util.Set;

/**
 * Responsible for generating k-shingles from a given text and storing them in a set.
 */
public class Shingle {
	private Set<String> shingles; //Store all shingles
	private String originalString; 
	int k; // length of the shingle

	/**
	 * This method generates K shingles and stores in the list
	 * @param originalString original string
	 * @param k length of the shingle
	 */
	public Shingle(String originalString, int k) {
		this.originalString = originalString;
		this.shingles = new HashSet<>();
		this.k = k;
		geneKShingles(originalString, k);
	}

	/**
	 * This method splits the text based on the shingle length and adds it to the list
	 * @param text text to be parsed
	 * @param k shingle length
	 */
	public void geneKShingles(String text, int k) {
		for (int i = 0; i < text.length() - (k - 1); i++) {
			  String shingle = text.toLowerCase().substring(i, i + k);//Creating k-shingles			  
			  shingles.add(shingle); //Add shingles to the Set		  
		}
	}

	/**
	 * Getter method which returns all the generated shingles
	 * @return list of shingles
	 */
	public Set<String> getShingleSet(){
		return this.shingles;
	}
}
