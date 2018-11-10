package Flamingo.Listeners;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AbstractFlamingoListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (isRespondable(event)) {
            return;
        }
    }

    //Default behavior should be to ignore bots
    protected boolean isRespondable(MessageReceivedEvent event) {
        return !event.getAuthor().isBot();
    }
}
