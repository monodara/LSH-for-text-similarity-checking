import impl.LSH;
import impl.Shingle;
import util.Utils;
import impl.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

import static util.Utils.findSimilarPair;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> sentencesList = new ArrayList<>();
		ArrayList<Set<String>> shingleSets = new ArrayList<>();

		int k = 5; // single size
		int numOfHashFunc = 50; // num of hash functions to be used
		int band = 10; // band size

		String path = "testdata2\\";
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	String fileName = file.getName();
		    	BufferedReader br = null;
		    	try {
		    		br = new BufferedReader(new FileReader(path + fileName));
		    	} catch (FileNotFoundException e) {
		    		e.printStackTrace();
		    	}	 
		    	String st;
		    	try {
		    		while ((st = br.readLine()) != null) {
		    			sentencesList.add(st);
		    			Shingle shingle = new Shingle(st, k);
		    			shingleSets.add(shingle.getShingleSet());
		    		}
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	}
		               
		    }
		}

		MinHash minHash = new MinHash(shingleSets, numOfHashFunc);
		ArrayList<ArrayList<Integer>> signatures = minHash.getSignatures();

		LSH lsh = new LSH(band);
		for(int i = 0; i < sentencesList.size(); i++) {
			lsh.bucketing(band, signatures.get(i), sentencesList.get(i));			
		}
		long startTime = System.currentTimeMillis();
		findSimilarPair(lsh, k);
		long endTime = System.currentTimeMillis();
		System.out.println("Time of using LSH: " + (endTime - startTime) + " ms");
		long startTimeBF = System.currentTimeMillis();
		Utils.findSimilarPairBF(sentencesList, k);
		long endTimeBF = System.currentTimeMillis();
		System.out.println("Time of brute force: " + (endTimeBF - startTimeBF) + " ms");
	}
}
