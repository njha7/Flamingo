package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StrikeCommand extends CommandAction {
    public static final String COMMAND = "";
    private final StrikeManager strikeManager;

    public StrikeCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        super(authStrategy);
        this.strikeManager = strikeManager;
    }

    @Override
    public String command(MessageReceivedEvent event) {
        return strikeManager.strike(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
    }
}
