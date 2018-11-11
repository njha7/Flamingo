package Flamingo.Listeners.Commands;

import Flamingo.Listeners.AbstractFlamingoListener;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class AbstractCommand extends AbstractFlamingoListener {

    public static final String COMMAND_PREFIX = "!";
    public static final String COMMAND = COMMAND_PREFIX + "abstractCommand";

    @Override
    public abstract void onMessageReceived(MessageReceivedEvent event);

    protected abstract boolean isCommand(Message message);
}
