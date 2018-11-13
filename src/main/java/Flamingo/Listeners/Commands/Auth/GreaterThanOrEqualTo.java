package Flamingo.Listeners.Commands.Auth;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Comparator;

public class GreaterThanOrEqualTo implements AuthStrategy {

    @Override
    public boolean isAuthorized(MessageReceivedEvent event) {
        Member striker = event.getMember();
        Member target = null;
        if (event.getMessage().getMentionedMembers() != null || !event.getMessage().getMentionedMembers().isEmpty()) {
            target = event.getMessage().getMentionedMembers().get(0);
        }
        if (target != null && target.hasPermission((Channel) event.getChannel(), Permission.MESSAGE_READ, Permission.MESSAGE_WRITE)) {
            return isGreaterThanOrEqualTo(striker, target);
        }
        return false;
    }

    private boolean isGreaterThanOrEqualTo(Member striker, Member target) {
        int strikerMax = striker.getRoles().isEmpty() ? 0 : striker.getRoles().stream().max(Comparator.comparing(Role::getPosition)).get().getPosition();
        int targetMax = target.getRoles().isEmpty() ? 0 : target.getRoles().stream().max(Comparator.comparing(Role::getPosition)).get().getPosition();
        return strikerMax >= targetMax;
    }
}
