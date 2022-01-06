import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindMax {

    public static void main(String[] args) {

        String pathToFile = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/Topics/Branching statements/";
        String filename = "dataset_91007.txt";
        String fullFilePath = pathToFile + filename;

        File file = new File(fullFilePath);
        int maxValue = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                if (value > maxValue) {
                    maxValue = value;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No such file: " + fullFilePath);
        }

        System.out.println("Max value: " + maxValue);
    }
}
