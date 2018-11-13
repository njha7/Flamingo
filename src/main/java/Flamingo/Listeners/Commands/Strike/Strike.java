package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommand;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.entities.Message;

import java.util.Map;

public class Strike extends AbstractCommand {
    public static final String COMMAND = COMMAND_PREFIX + "strike";

    public Strike(Map<String, CommandAction> commandActions) {
        super(commandActions);
    }

    @Override
    protected boolean isCommand(Message message) {
        return message.getContentDisplay().startsWith(COMMAND);
    }

    @Override
    protected String parseCommand(String message) {
        String[] args = message.split(" ");
        if (args.length < 2) {
            return null;
        } else if (args.length == 2) {
            return "";
        } else {
            return args[1];
        }
    }
}
