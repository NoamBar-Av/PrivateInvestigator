package com.investigator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

public class PrivateInvestigator {

    final static String inputFilePath = "src/main/resources/input.txt";
    final static String outputFilePath = "src/main/resources/output.txt";

    // need to add propertied file
    public static void main(String[] args) {
        List<String> lines = null;
        File inputFile = new File(inputFilePath);
        boolean exists = inputFile.exists();
        if(exists){
            try {
                lines = Files.lines(Paths.get(inputFilePath)).collect(Collectors.toList());
                int runStatus = createSentencesAndChangeWordMap(lines);
                if(runStatus==1){
                    System.out.println("Function finished OK");
                }else{
                    System.out.println("an error occur while running the script ");
                }
            } catch (IOException e) {
                System.out.println("An Erroe occured while reading lined from " + inputFilePath + "error message: " + e);
            }
        }else{
            System.out.println("File " + inputFilePath + "dose not exist");
        }
    }

    public static int createSentencesAndChangeWordMap(List<String> lines) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(outputFilePath);
            Map<String, RelatedSentencesAndChangedWords> patternSentences = new HashMap<>();
            for (String str : lines) {
                generatePatterns(patternSentences, str);
            }
            Map<String, RelatedSentencesAndChangedWords> finalMap=getFilterdMap(patternSentences);
            writeToFile(myWriter, finalMap);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
    private static Map<String, RelatedSentencesAndChangedWords> getFilterdMap(Map<String, RelatedSentencesAndChangedWords> patternSentences){
        Map<java.lang.String, com.investigator.RelatedSentencesAndChangedWords> finalMap = new HashMap<>();
        for (Map.Entry<java.lang.String, com.investigator.RelatedSentencesAndChangedWords> entry : patternSentences.entrySet()) {
            if (entry.getValue().getLines().size() > 1) {
                finalMap.put(entry.getKey(), entry.getValue());
            }
        }
        return finalMap;
    }
    private static void generatePatterns(Map<String, RelatedSentencesAndChangedWords> patternSentences, String line) {
        String[] words = line.split(" ");
        words =  Arrays.copyOfRange(words, 2, words.length);
        for (int i = 0; i < words.length; i++) {
            String[] preffix = Arrays.copyOfRange(words, 0, i);
            String[] Suffix = Arrays.copyOfRange(words, i + 1, words.length);
            String[] both = ArrayUtils.addAll(preffix, Suffix);
            if(patternSentences.containsKey(String.join(" ", both))){
                RelatedSentencesAndChangedWords matchingObject = patternSentences.get(String.join(" ", both));
                matchingObject.addToDiffWords(words[i]);
                matchingObject.addToLines(line);
            }else{
                patternSentences.put(String.join(" ", both),new RelatedSentencesAndChangedWords(line, words[i]));
            }
        }
    }
    public static void writeToFile(FileWriter myWriter, Map<String, RelatedSentencesAndChangedWords> map) {
        for (Map.Entry<String, RelatedSentencesAndChangedWords> resObj : map.entrySet()) {
            try {
                for (String str : resObj.getValue().getLines()) {
                    myWriter.write(str);
                    myWriter.write("\n");
                }
                String diffWords = String.join(",", resObj.getValue().getDiffWords());
                myWriter.write("The changing word was: " + diffWords);
                myWriter.write("\n");
            } catch (Exception ee) {
                System.out.println("there was an error while writing to output.txt " + ee);
            }
        }
    }
}
