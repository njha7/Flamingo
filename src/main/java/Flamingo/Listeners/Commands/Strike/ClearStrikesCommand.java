package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClearStrikesCommand implements CommandAction {

    public static final String COMMAND = "clear";
    private final AuthStrategy authStrategy;
    private final StrikeManager strikeManager;

    public ClearStrikesCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        this.authStrategy = authStrategy;
        this.strikeManager = strikeManager;
    }

    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        return authStrategy.isAuthorized(event);
    }

    @Override
    public String command(MessageReceivedEvent event) {
        return strikeManager.clearStrikes(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
    }
}
