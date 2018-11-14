package Flamingo.Listeners.Commands;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TestCommandAction extends CommandAction {

    public static final String COMMAND_MESSAGE = "TEST COMMAND SUCCESS";

    public TestCommandAction(AuthStrategy authStrategy) {
        super(authStrategy);
    }

    @Override
    public String command(MessageReceivedEvent event) {
        return COMMAND_MESSAGE;
    }
}
