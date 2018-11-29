package Flamingo.Listeners.Commands.Pasta;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;

public class Pasta extends AbstractCommand {

    public static final String COMMAND = COMMAND_PREFIX + "pasta";

    public Pasta(Map<String, CommandAction> commandActions) {
        super(commandActions);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
    }

    @Override
    protected String parseCommand(String message) {
        return message.split(" ")[1];
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentRaw().startsWith(COMMAND);
    }
}
