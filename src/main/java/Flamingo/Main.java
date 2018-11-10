package Flamingo;

import Flamingo.Bot.FlamingoBot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        FlamingoBot flamingo = new FlamingoBot(token, Collections.emptyList());
    }
}
