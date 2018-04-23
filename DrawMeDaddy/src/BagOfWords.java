import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

class BagOfWords{
    public static final String fileName = "BagOfWords.txt";
    private List<String> words = new ArrayList<>();
    private List<String> cache = new ArrayList<>();

    public BagOfWords(){
        this.readWords();
    }

    private void readWords(){
        try{
            BufferedReader breader = new BufferedReader(new FileReader(fileName));
            while(breader.ready()){
                String currentLine = breader.readLine().toUpperCase();
                this.words.add(currentLine);
            }
            breader.close();
        }catch(IOException ioe){
            System.out.println("BagOfWords.java.readWords():"+ioe.getMessage());
        }
    }

    public String getRandomWord(){
        Random randomizer = new Random();
        int numOfWords = words.size();
        int randomIndex = randomizer.nextInt(numOfWords);
        String word = words.get(randomIndex);
        while(cache.contains(word)){
            randomIndex = randomizer.nextInt(numOfWords);
            word = words.get(randomIndex);
        }
        cache.add(word);
        return word;
    }

}