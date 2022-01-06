import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CountGreaterNumber {

    public static void main(String[] args) {

        String pathToFile = "/Users/bdongmo-ngnintedem/IdeaProjects/Simple Banking System/Topics/Branching statements/";
        String fileName = "dataset_91022.txt";

        File file = new File(pathToFile + fileName);
        int limitValue = 9999;
        int counter = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                int value = Integer.parseInt(scanner.next());
                if (value >= limitValue) {
                    counter += 1;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not such file found: " + pathToFile + fileName);
        }

        System.out.println("Number of value greater than or equal to 9999: " + counter);
    }
}
