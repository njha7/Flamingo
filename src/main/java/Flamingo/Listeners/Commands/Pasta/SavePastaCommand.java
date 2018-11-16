package Flamingo.Listeners.Commands.Pasta;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SavePastaCommand extends CommandAction {

    public static final String COMMAND = "save";
    private final PastaManager pastaManager;

    public SavePastaCommand(AuthStrategy authStrategy, PastaManager pastaManager) {
        super(authStrategy);
        this.pastaManager = pastaManager;
    }

    @Override
    public String command(MessageReceivedEvent event) {
        String[] args = getArgs(event.getMessage().getContentRaw());
        return pastaManager.savePasta(event.getGuild().getId(), event.getAuthor().getId(), args[0], args[1]);
    }

    private String[] getArgs(String message) {
        String[] args = new String[2];
        String[] input = message.split(" ");
        args[0] = input[2];
        args[1] = message.substring(input[0].length() + input[1].length() + input[2].length() + 3);
        return args;
    }
}
