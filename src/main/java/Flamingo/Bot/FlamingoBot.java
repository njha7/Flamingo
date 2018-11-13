package Flamingo.Bot;

import Flamingo.Listeners.Commands.AbstractCommandFactory;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class FlamingoBot {

    public FlamingoBot(String token, AbstractCommandFactory... commandFactories) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        for (AbstractCommandFactory factory : commandFactories) {
            builder.addEventListener(factory.build());
        }
        try {
            builder.build();
        } catch (LoginException e) {
            System.exit(0);
        }
    }
}
