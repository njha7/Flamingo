package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClearStrikesCommand extends AbstractCommand {

    public static final String COMMAND = COMMAND_PREFIX + "strike clear";
    private final AuthStrategy authStrategy;
    private final StrikeManager strikeManager;

    public ClearStrikesCommand(AuthStrategy authStrategy, StrikeManager strikeManager) {
        this.authStrategy = authStrategy;
        this.strikeManager = strikeManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event) && isCommand(event.getMessage())) {
            if (authStrategy.authorizedToStrike(event)) {
                String response = strikeManager.clearStrikes(event.getGuild().getId(), event.getMessage().getMentionedMembers().get(0).getUser().getId());
                event.getChannel().sendMessage(response).queue();

            }
        }
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }
}
