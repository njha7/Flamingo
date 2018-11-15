package Flamingo;

import Flamingo.Bot.FlamingoBot;
import Flamingo.Listeners.Commands.Auth.AllowAll;
import Flamingo.Listeners.Commands.Auth.AuthStrategy;
import Flamingo.Listeners.Commands.Auth.GreaterThanOrEqualTo;
import Flamingo.Listeners.Commands.Auth.Moderator;
import Flamingo.Listeners.Commands.Pasta.PastaFactory;
import Flamingo.Listeners.Commands.Strike.StrikeFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static Map<String, AuthStrategy> authStrategyMap = new HashMap<>();

    static {
        authStrategyMap.put(AllowAll.class.getName(), new AllowAll());
        authStrategyMap.put(GreaterThanOrEqualTo.class.getName(), new GreaterThanOrEqualTo());
        authStrategyMap.put(Moderator.class.getName(), new Moderator());
    }

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");
        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("us-east-1")
                .build();

        StrikeFactory strikeFactory = new StrikeFactory(authStrategyMap);
        strikeFactory.buildStrikeManager(dynamoDB);

        PastaFactory pastaFactory = new PastaFactory(authStrategyMap);
        pastaFactory.buildPastaManager(dynamoDB);

        FlamingoBot flamingo = new FlamingoBot(token, strikeFactory, pastaFactory);
    }
}
