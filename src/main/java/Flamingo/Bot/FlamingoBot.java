package Flamingo.Bot;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Collection;

public class FlamingoBot {

    public FlamingoBot(String token, Collection<ListenerAdapter> listenerAdapters) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        listenerAdapters.forEach(listener -> builder.addEventListener(listener));
        try {
            builder.build();
        } catch (LoginException e) {
            System.exit(0);
        }
    }
}
