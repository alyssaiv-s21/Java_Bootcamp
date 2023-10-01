package ex01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Similarity {
    private TreeSet<String> dictionary = new TreeSet<String>();
    private HashMap<String, Integer> inputA = new HashMap<String, Integer>();
    private HashMap<String, Integer> inputB = new HashMap<String, Integer>();
    private ArrayList<Integer> vector1 = new ArrayList<Integer>();
    private ArrayList<Integer> vector2 = new ArrayList<Integer>();
    private double result;

    public double calculateSimilarity(String file1, String file2) {
        try {
            OpenInputFile(file1, inputA);
            OpenInputFile(file2, inputB);
        } catch (IOException ex) {
            System.out.println("Your input files can 't be read");
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        try {
            CreateFileDictionary();
        } catch (IOException ex) {
            System.out.println("File 'dictionay.txt' can 't be created");
        }
        if (inputB.size() == 0 && inputA.size() == 0) {
            return 1.0;
        } else if (inputB.size() == 0 || inputA.size() == 0) {
            return 0.0;
        } else {
            VectorCreate(inputA, vector1);
            VectorCreate(inputB, vector2);
            CalculateSimilarity(vector1, vector2);
            return result;
        }
    }

    private void OpenInputFile(String inputFile, HashMap<String, Integer> inputList) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line = reader.readLine();
        while (line != null) {
            String modifiedString = NormalizeLine.NormalizeLne(line);
            String[] words = modifiedString.split(" ");
            for (String word : words) {
                int qty = inputList.getOrDefault(word, -1);
                if (qty == -1) {
                    inputList.put(word, 1);
                } else {
                    inputList.put(word, qty + 1);
                }
                dictionary.add(word);
            }
            line = reader.readLine();
        }
        reader.close();
    }

    private void CreateFileDictionary() throws IOException {
        File file = new File("dictionary.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        StringBuilder infoToFile = new StringBuilder();
        for (String word : dictionary) {
            infoToFile.append(word);
            infoToFile.append(", ");
        }
        if (infoToFile.length() > 1) {
            infoToFile.deleteCharAt(infoToFile.length() - 1);
            infoToFile.deleteCharAt(infoToFile.length() - 1);
        }
        writer.write(infoToFile.toString());
        writer.close();
    }

    private void VectorCreate(HashMap<String, Integer> input, ArrayList<Integer> vector) {
        for (String word : dictionary) {
            if (input.getOrDefault(word, -1) != -1) {
                vector.add(input.get(word));
            } else {
                vector.add(0);
            }
        }
    }

    private void CalculateSimilarity(ArrayList<Integer> vectorA, ArrayList<Integer> vectorB) {
        int numerator = 0;
        int sumSqrtA = 0;
        int sumSqrtB = 0;
        for (int i = 0; i < vectorA.size(); ++i) {
            numerator += vectorA.get(i) * vectorB.get(i);
            sumSqrtA += vectorA.get(i) * vectorA.get(i);
            sumSqrtB += vectorB.get(i) * vectorB.get(i);
        }
        result = numerator / (Math.sqrt(sumSqrtA) * Math.sqrt(sumSqrtB));
    }
}