package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class QueryStrikeCommand extends CommandAction {

    public static final String COMMAND = "get";
    private final StrikeManager strikeManager;

    public QueryStrikeCommand(StrikeManager strikeManager) {
        super(null);
        this.strikeManager = strikeManager;
    }

    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        return true;
    }

    @Override
    public String command(MessageReceivedEvent event) {
        if (event.getMessage().getMentionedMembers() != null || !event.getMessage().getMentionedMembers().isEmpty()) {
            return "Malformed command. Please double check and try again.";
        }
        return strikeManager.getStrikes(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
    }
}
