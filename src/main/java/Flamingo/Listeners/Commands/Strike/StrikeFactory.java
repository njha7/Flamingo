package Flamingo.Listeners.Commands.Strike;

import Flamingo.Listeners.Commands.AbstractCommandFactory;
import Flamingo.Listeners.Commands.Auth.GreaterThanOrEqualTo;
import Flamingo.Listeners.Commands.Auth.Moderator;
import Flamingo.Listeners.Commands.CommandAction;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;

public class StrikeFactory extends AbstractCommandFactory {

    public Strike build() {
        StrikeManager strikeManager = buildStrikeManager();
        Map<String, CommandAction> commandActionMap = new HashMap<>();
        commandActionMap.put(StrikeCommand.COMMAND, new StrikeCommand(new GreaterThanOrEqualTo(), strikeManager));
        commandActionMap.put(ClearStrikesCommand.COMMAND, new ClearStrikesCommand(new Moderator(), strikeManager));
        commandActionMap.put(QueryStrikeCommand.COMMAND, new QueryStrikeCommand(strikeManager));
        return new Strike(commandActionMap);
    }

    private StrikeManager buildStrikeManager() {
        Dotenv dotenv = Dotenv.load();
        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion("us-east-1")
                .build();
        return new StrikeManager(dynamoDB);
    }
}
