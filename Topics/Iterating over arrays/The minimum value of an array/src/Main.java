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

        minValue = intValues[0];

        for (int j = 1; j < arraySize; j++) {
            if (intValues[j] < minValue) {
                minValue = intValues[j];
            }
        }

        System.out.println(minValue);
    }
}