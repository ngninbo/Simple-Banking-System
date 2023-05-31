package banking.domain;

public class MessageFactory extends Formatter {

    public MessageFactory() {
        super(ResourceProperties.from("messages"));
    }
}
