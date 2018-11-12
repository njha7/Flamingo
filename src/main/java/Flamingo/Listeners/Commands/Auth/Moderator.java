package Flamingo.Listeners.Commands.Auth;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Moderator implements AuthStrategy {
    @Override
    public boolean authorizedToStrike(MessageReceivedEvent event) {
        return event.getMember().hasPermission(Permission.VIEW_AUDIT_LOGS);
    }
}
