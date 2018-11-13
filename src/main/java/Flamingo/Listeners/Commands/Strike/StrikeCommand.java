package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import com.google.inject.Inject;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StrikeCommand implements CommandAction {
    public static final String COMMAND = "";
    private final AuthStrategy authStrategy;
    private final StrikeManager strikeManager;


    @Inject
    public StrikeCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        this.authStrategy = authStrategy;
        this.strikeManager = strikeManager;
    }

    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        return authStrategy.isAuthorized(event);
    }

    @Override
    public String command(MessageReceivedEvent event) {
        return strikeManager.strike(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
    }
}
