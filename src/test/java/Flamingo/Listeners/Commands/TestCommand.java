package Flamingo.Listeners.Commands;

import net.dv8tion.jda.core.entities.Message;

import java.util.Map;

public class TestCommand extends AbstractCommand {

    public static final String COMMAND = COMMAND_PREFIX + "tests";

    public TestCommand(Map<String, CommandAction> commandActions) {
        super(commandActions);
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }

    @Override
    protected String parseCommand(String message) {
        return COMMAND;
    }
}
