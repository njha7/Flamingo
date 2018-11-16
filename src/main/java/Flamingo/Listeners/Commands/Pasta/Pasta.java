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
//        super.onMessageReceived(event);
        if (isRespondable(event)) {
            if (isCommand(event.getMessage())) {
                String command = parseCommand(event.getMessage().getContentRaw());
                CommandAction commandAction = null;
                if (command != null) {
                    commandAction = commandActions.get(command);
                }
                if (commandAction != null) {
                    if (commandAction.isAuthorized(event)) {
                        event.getChannel().sendMessage(commandAction.command(event)).queue();
                    } else {
                        dmUser(event.getAuthor(), "You are unauthorized to issue this command.");
                    }
                } else {
                    dmUser(event.getAuthor(), "Malformed command. Please double check and try again.");
                }
            }
        }
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
