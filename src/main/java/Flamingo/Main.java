package Flamingo;

import Flamingo.Bot.FlamingoBot;
import Flamingo.Listeners.Commands.Strike.Auth.GreaterThanOrEqualTo;
import Flamingo.Listeners.Commands.Strike.Strike;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main {

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
        List<ListenerAdapter> listerners = new ArrayList<>();
        listerners.add(new Strike(dynamoDB, new GreaterThanOrEqualTo()));
        FlamingoBot flamingo = new FlamingoBot(token, listerners);
    }
}
