package Flamingo.Listeners.Commands.Auth;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestAuthStrategy implements AuthStrategy {

    public static final String AUTH_STRING = "AUTH";

    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        return AUTH_STRING.equals(event.getMessage().getContentRaw());
    }
}
