package Flamingo.Listeners.Commands.Auth;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface AuthStrategy {

    public boolean isAuthorized(MessageReceivedEvent event);
}
