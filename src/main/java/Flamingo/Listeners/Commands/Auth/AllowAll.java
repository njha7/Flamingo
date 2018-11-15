package Flamingo.Listeners.Commands.Auth;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AllowAll implements AuthStrategy {
    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        return true;
    }
}
