package Flamingo.Listeners.Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface CommandAction {

    boolean isAuthorized(MessageReceivedEvent event);

    String command(MessageReceivedEvent event);
}
