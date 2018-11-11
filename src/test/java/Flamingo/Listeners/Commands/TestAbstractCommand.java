package Flamingo.Listeners.Commands;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestAbstractCommand extends AbstractCommand {

    public static final String COMMAND = "!test";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event) && isCommand(event.getMessage())) {
            event.getMessage().editMessage("Dummy string");
        }
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }
}
