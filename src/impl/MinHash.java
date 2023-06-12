package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Class implements the MinHash algorithm for
 * finding the similarity between sets of texts.
 */
public class MinHash {

	private ArrayList<Set<String>> shingleSets; //Store all shingles generated from every text to be compared
	private ArrayList<ArrayList<Integer>> matricesReps; //Store 0/1 matrices representing if a single shingle occurs in a text
	private Set<String> allShingles; //Store all shingles generated from all texts
	private ArrayList<ArrayList<Integer>> hashFunctions; //Store all hashFunctions
	private ArrayList<ArrayList<Integer>> signatures; //Store all signatures
	//Getter for access

	/**
	 * Getter method for getting the signatures
	 * @return signature list
	 */
	public ArrayList<ArrayList<Integer>> getSignatures() {return signatures;}

	/**
	 * Getter method for getting the hash functions
	 * @return hash function list
	 */
	public ArrayList<ArrayList<Integer>> getHashFunctions() {return hashFunctions;}

	//Constructor
	public MinHash(ArrayList<Set<String>> shingleSets, int numOfHashFunc) {
		this.shingleSets = shingleSets;
		this.allShingles = new HashSet<>();
		for(Set<String> shingleSet: shingleSets) {
			this.allShingles.addAll(shingleSet); //Merge all shingles together
		}
		getMatrixRep();
		this.hashFunctions = new ArrayList<>();
		generateHashFunctions(numOfHashFunc);
		this.signatures = new ArrayList<>();
		geneAllSig();
	}

	/**
	 * Generates a 0/1 matrix for each set, where each row represents a shingle and
	 * each column represents a text, and the entry at row i and column j is 1 if
	 * the i-th shingle occurs in the j-th text, and 0 otherwise
	 */
	public void getMatrixRep(){
		this.matricesReps = new ArrayList<>();
		for(int i = 0; i < this.shingleSets.size(); i++) {
			ArrayList<Integer> shingleOccuList = new ArrayList<>();
			Set shingleSet = shingleSets.get(i);
			for(String shingle: this.allShingles) {
				if(shingleSet.contains(shingle)) { //if the shingle occurs in current text
					shingleOccuList.add(1);
				}else shingleOccuList.add(0);
			}
			this.matricesReps.add(shingleOccuList);
		}
	}

	/**Create a single Hash function ( and randomly shuffle it)
	  -Get a 0-index ArrayList where the index represent the position in the matrices
	   -Randomly shuffle the list
	 */
	public ArrayList<Integer> createHashFunc(){
		ArrayList<Integer> list = new ArrayList<>();
		for(int j = 0; j < this.allShingles.size(); j++) {
			list.add(j);
		}
		Collections.shuffle(list);
		return list;
	}

	/**
	 * Shuffle and get n Hash functions
	 */
	public void generateHashFunctions(int n){
		for(int i = 0; i < n; i++) {
			ArrayList<Integer> list = createHashFunc();
			this.hashFunctions.add(list);
		}
	}

	/**
	 * Generate signature for every list that represents the occurrence of shingles in texts
	 * @param singleList single list
	 * @return signatures
	 */
	public ArrayList<Integer> generateSig(ArrayList<Integer> singleList) {
		ArrayList<Integer> signature = new ArrayList<>();
		//For each Hash function,
		//Traverse the list,
		//The first index of 1 which indicates the shingles occurs in the text is result of the function
		//All results from all Hash functions make up a signature
		for(ArrayList<Integer> func: this.hashFunctions) {
			for(int i = 0; i < singleList.size(); i++) {
				int index = func.get(i);
				int val = singleList.get(index);
				if(val == 1) {
					signature.add(index); break;
				}
			}
		}
		return signature;
	}

	/**
	 * Generate all signatures for all lists in the matrix
	 */
	public void geneAllSig() {
		for(ArrayList<Integer> list: this.matricesReps) {
			ArrayList<Integer> signature = generateSig(list);
			this.signatures.add(signature);
		}
	}
	
}
