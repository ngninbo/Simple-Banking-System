package banking.menu;

import banking.util.PropertiesLoader;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.IntStream;

public abstract class Menu {

    protected abstract boolean process(MenuItem menuItem) throws IOException;

    protected abstract MenuItem getMenuItem(int choice);

    /**
     * Display menu and read user input from standard input
     * @return {@link Integer} number corresponding to chosen menu item
     */
    protected abstract int display();

    protected Properties properties;
    protected MenuItem item = MenuItem.UNKNOWN;

    {
       properties = PropertiesLoader.getInstance().messages();
    }

    /**
     * Show menu and process user input.
     * @throws IOException e.g. when reading/loading data failed
     */
    public void show() throws IOException {
        while (process(item)) {
            int choice = display();
            item = getMenuItem(choice);
        }
    }

    protected void exit() {
        printByeMessage();
    }

    /**
     * Display menu options and read user input using {@link Scanner}
     * @param items List of {@link MenuItem}
     * @return {@link Integer} number of selected menu item
     */
    protected int display(List<MenuItem> items) {
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
    }

    /**
     * Get menu item by given index in the given list of items
     * @param index {@link Integer}
     * @param items List of {@link MenuItem}
     * @return {@link MenuItem}
     */
    protected MenuItem getMenuItem(int index, List<MenuItem> items) {

        final int size = items.size();
        if (index >= size || index < 0) {
            System.out.println(properties.getProperty("UNKNOWN_COMMAND_TXT") + "\n");
            return MenuItem.UNKNOWN;
        }

        if (index == 0) {
            return MenuItem.EXIT;
        }

        return items.get(index - 1);
    }

}
