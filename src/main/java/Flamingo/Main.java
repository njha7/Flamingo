package Flamingo;

import Flamingo.Bot.FlamingoBot;
import Flamingo.Listeners.Commands.Strike.StrikeFactory;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        FlamingoBot flamingo = new FlamingoBot(token, new StrikeFactory());
    }
}
