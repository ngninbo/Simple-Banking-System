package banking.menu;

public enum LoginState {

    LOGGED_IN,
    LOGGED_OUT;

    public String getName() {
        return name().replace("_", " ").toLowerCase();
    }

    public String getLabel() {
        return name().concat("_MSG");
    }
}
