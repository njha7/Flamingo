package Flamingo.Listeners.Commands;

import Flamingo.Listeners.AbstractFlamingoListener;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;

public abstract class AbstractCommand extends AbstractFlamingoListener {

    public static final String COMMAND_PREFIX = "~";
    public static final String COMMAND = COMMAND_PREFIX + "abstractCommand";

    private final Map<String, CommandAction> commandActions;

    public AbstractCommand(Map<String, CommandAction> commandActions) {
        this.commandActions = commandActions;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
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

    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }

    protected String parseCommand(String message) {
        return null;
    }

    private void dmUser(User user, CharSequence message) {
        user.openPrivateChannel().queue((channel) -> {
            channel.sendMessage(message).queue();
        });
    }
}
