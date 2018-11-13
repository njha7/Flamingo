package Flamingo.Listeners.Commands;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandAction {

    public static final String COMMAND = "";
    private final AuthStrategy authStrategy;

    public CommandAction(AuthStrategy authStrategy) {
        this.authStrategy = authStrategy;
    }

    public boolean isAuthorized(MessageReceivedEvent event) {
        return authStrategy.isAuthorized(event);
    }

    public abstract String command(MessageReceivedEvent event);
}
