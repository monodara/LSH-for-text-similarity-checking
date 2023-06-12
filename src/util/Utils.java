package util;

import impl.LSH;
import impl.Shingle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static void findSimilarPair(LSH lsh, int k) {
        double maxSimilarity = 0;
        String[] mostSimilarPair = new String[2];

        for (HashMap<String, List<String>> bucketBand : lsh.buckets) {
            for (List<String> sentences : bucketBand.values()) {
                if (sentences.size() > 1) {
                    for (int i = 0; i < sentences.size(); i++) {
                        for (int j = i + 1; j < sentences.size(); j++) {
                            double jac = calJaccardSimilarity(sentences.get(i), sentences.get(j), k);
                            if (jac > maxSimilarity) {
                                maxSimilarity = jac;
                                mostSimilarPair[0] = sentences.get(i);
                                mostSimilarPair[1] = sentences.get(j);
                            }
                        }
                    }
                }
            }
        }
        // print the results
        System.out.println(mostSimilarPair[0]);
        System.out.println(mostSimilarPair[1]);
        System.out.println("Jaccard Similarity score = " + maxSimilarity);
    }
    // Compare every pair to find the most similar two (Brute Force)
    public static void findSimilarPairBF(ArrayList<String> sentences, int k) {
    	double maxSimilarity = 0;
        String[] mostSimilarPair = new String[2];
    	for (int i = 0; i < sentences.size(); i++) {
            for (int j = i + 1; j < sentences.size(); j++) {
                double jac = calJaccardSimilarity(sentences.get(i), sentences.get(j), k);
                if (jac > maxSimilarity) {
                    maxSimilarity = jac;
                    mostSimilarPair[0] = sentences.get(i);
                    mostSimilarPair[1] = sentences.get(j);
                }
            }
        }
    	// print the results
        System.out.println(mostSimilarPair[0]);
        System.out.println(mostSimilarPair[1]);
        System.out.println("Jaccard Similarity score = " + maxSimilarity);
    }
    
    
    public static double calJaccardSimilarity(String s1, String s2, int k) {

        Shingle shingle1 = new Shingle(s1, k);
        Shingle shingle2 = new Shingle(s2, k);

        Set<String> unionSet = new HashSet<>(shingle1.getShingleSet());
        Set<String> intersectSet = new HashSet<>(shingle1.getShingleSet());

        unionSet.addAll(shingle2.getShingleSet());
        intersectSet.retainAll(shingle2.getShingleSet());

        return (double) intersectSet.size() / unionSet.size();
    }
}
