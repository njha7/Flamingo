package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.Strike.Auth.AuthStrategy;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.google.inject.Inject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Strike extends AbstractCommand {
    public static final String COMMAND = COMMAND_PREFIX + "strike";
    private final AmazonDynamoDB dynamoDB;
    private final AuthStrategy authStrategy;


    @Inject
    public Strike(AmazonDynamoDB dynamoDB, AuthStrategy authStrategy) {
        this.dynamoDB = dynamoDB;
        this.authStrategy = authStrategy;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event) && isCommand(event.getMessage())) {
            if (authStrategy.authorizedToStrike(event)) {
                UpdateItemRequest strike = buildStrike(event.getMessage());
                if (strike != null) {
                    UpdateItemResult strikeResult = dynamoDB.updateItem(strike);
                    String strikes = strikeResult.getAttributes().get(StrikeItem.STRIKES).getN();
                    StringBuilder messageBuilder = new StringBuilder();
                    messageBuilder.append("<@");
                    messageBuilder.append(event.getMessage().getMentionedUsers().get(0).getId() + ">");
                    messageBuilder.append(" has " + strikes + " strikes.");
                    event.getChannel().sendMessage(messageBuilder.toString()).queue();
                }
            }
        }
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }

    private UpdateItemRequest buildStrike(Message message) {
        //Only 1 user per strike, ignore all other mentions
        String targetUserId = message.getMentionedUsers().get(0).getId();
        return StrikeItem.buildStrike(message.getGuild().getId(), targetUserId);
    }
}
