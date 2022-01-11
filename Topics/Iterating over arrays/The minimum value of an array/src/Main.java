import java.util.Arrays;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int arraySize = scanner.nextInt();
        int minValue;
        int i = 0;
        int[] intValues = new int[arraySize];

        while (i < arraySize) {
            intValues[i] = Integer.parseInt(scanner.next());
            i++;
        }

        minValue = Arrays.stream(intValues)
                .sorted()
                .findFirst()
                .orElse(0);

        System.out.println(minValue);
    }
}