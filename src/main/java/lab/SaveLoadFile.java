package lab;

import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveLoadFile {
    public SaveLoadFile() {
    }

    public static void saveScores(String name, int score) {
        try (FileWriter fw = new FileWriter("data.csv", true)) { // automaticky zavre soubor
            fw.write(name + "," + score + "\n");
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static Pair<String, Integer> loadHighestScore() {
        Pair<String, Integer> maxScore = new Pair<>("noen", 0);
        try (Scanner scanner = new Scanner(new File("data.csv"))) {
            scanner.useDelimiter("[,\\n]");
            while (scanner.hasNext()) {
                String name = scanner.next();
                int score = scanner.nextInt();
                if(score > maxScore.getValue()) {
                    maxScore = new Pair<>(name,score);
                }
                System.out.println(name + " , " + score);

            }
        } catch (IOException err) {
            err.printStackTrace();
        }

        return maxScore;
    }
}
