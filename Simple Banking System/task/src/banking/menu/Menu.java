package banking.menu;

import banking.services.CardService;
import banking.util.PropertiesLoader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.IntStream;

public abstract class Menu {

    protected abstract boolean process(MenuItem menuItem) throws IOException;

    protected abstract MenuItem getMenuItem(int choice);
    protected abstract void process() throws IOException;
    protected abstract int displayMenu();

    protected Properties properties;
    protected final CardService cardService;

    {
        try {
            properties = PropertiesLoader.loadProperties("logs.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Menu(CardService cardService) {
        this.cardService = cardService;
    }

    protected void exit() {
        printByeMessage();
    }

    /**
     * @param items List of {@link MenuItem}
     * @return selected option
     */
    public int displayOptions(List<MenuItem> items) {
        // Print menu
        int selectedOption;
        IntStream.range(0, items.size())
                .forEach(i -> System.out.printf("%d. %s\n",
                        MenuItem.EXIT.equals(items.get(i)) ? 0 : i + 1, items.get(i).capitalize()));

        // Prompt user to select an option
        Scanner scanner = new Scanner(System.in);
        selectedOption = scanner.nextInt();

        return selectedOption;
    }

    /**
     * Say 'Bye' and end the program
     */
    protected void printByeMessage() {
        System.out.println(properties.getProperty("BYE_MSG"));
        System.exit(0);
    }

}
