package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClearStrikesCommand extends CommandAction {

    public static final String COMMAND = "clear";
    private final StrikeManager strikeManager;

    public ClearStrikesCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        super(authStrategy);
        this.strikeManager = strikeManager;
    }

    @Override
    public String command(MessageReceivedEvent event) {
        return strikeManager.clearStrikes(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
    }
}
