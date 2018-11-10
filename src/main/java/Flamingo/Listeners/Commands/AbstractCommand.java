package Flamingo.Listeners.Commands;

import Flamingo.Listeners.AbstractFlamingoListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class AbstractCommand extends AbstractFlamingoListener {

    public static final String COMMAND_PREFIX = "!";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        super.onMessageReceived(event);
    }

    protected abstract boolean isCommand(MessageReceivedEvent event);
}
