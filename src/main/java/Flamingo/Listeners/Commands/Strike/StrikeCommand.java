package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import com.google.inject.Inject;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StrikeCommand extends AbstractCommand {
    public static final String COMMAND = COMMAND_PREFIX + "strike";
    private final AuthStrategy authStrategy;
    private final StrikeManager strikeManager;


    @Inject
    public StrikeCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        this.authStrategy = authStrategy;
        this.strikeManager = strikeManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event) && isCommand(event.getMessage())) {
            if (authStrategy.authorizedToStrike(event)) {
                String response = strikeManager.strike(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
                event.getChannel().sendMessage(response).queue();
            }
        }
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }
}
