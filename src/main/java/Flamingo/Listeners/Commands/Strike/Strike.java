package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.google.inject.Inject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Strike extends AbstractCommand {
    public static final String COMMAND = COMMAND_PREFIX + "strike";
    private final AmazonDynamoDB dynamoDB;

    @Inject
    public Strike(AmazonDynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event) && isCommand(event.getMessage())) {
            UpdateItemRequest strike = buildStrike(event.getMessage());
            if (strike != null) {
                UpdateItemResult strikeResult = dynamoDB.updateItem(strike);

            }
        }
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }

    private UpdateItemRequest buildStrike(Message message) {
        if (message.getMentionedUsers() == null || message.getMentionedUsers().isEmpty()) {
            return null;
        }
        //Only 1 user per strike, ignore all other mentions
        String targetUserId = message.getMentionedUsers().get(0).getId();
        return StrikeItem.buildStrike(message.getGuild().getId(), targetUserId);
    }
}
