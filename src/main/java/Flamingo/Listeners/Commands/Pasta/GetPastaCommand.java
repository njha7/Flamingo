package Flamingo.Listeners.Commands.Pasta;

import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.CommandAction;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GetPastaCommand extends CommandAction {

    public static final String COMMAND = "get";
    private final PastaManager pastaManager;

    public GetPastaCommand(AuthStrategy authStrategy, PastaManager pastaManager) {
        super(authStrategy);
        this.pastaManager = pastaManager;
    }

    @Override
    public String command(MessageReceivedEvent event) {
        String alias = getAlias(event.getMessage().getContentRaw());
        return pastaManager.getPasta(event.getGuild().getId(), alias);
    }

    private String getAlias(String message) {
        String[] input = message.split(" ");
        return input[2];
    }
}
