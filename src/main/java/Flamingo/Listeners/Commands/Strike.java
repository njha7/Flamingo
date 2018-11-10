package Flamingo.Listeners.Commands;

import Flamingo.Listeners.AbstractFlamingoListener;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.google.inject.Inject;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Strike extends AbstractCommand {
    public static final String COMMAND = AbstractCommand.COMMAND_PREFIX + "strike";
    private final AmazonDynamoDB dynamoDB;

    @Inject
    public Strike(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
        if(!isCommand(event)) {
            return;
        }
    }

    @Override
    protected boolean isCommand(MessageReceivedEvent event) {
        return false;
    }
}
