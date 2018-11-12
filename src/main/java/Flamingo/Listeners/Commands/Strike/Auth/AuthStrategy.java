package Flamingo.Listeners.Commands.Strike.Auth;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface AuthStrategy {

    public boolean authorizedToStrike(MessageReceivedEvent event);
}
